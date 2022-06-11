package de.varoplugin.varo.dependencies;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.function.Function;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import de.cuuky.varo.spigot.FileDownloader;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.config.VaroConfig;

public abstract class Dependency {

	protected static final File LIB_FOLDER = new File("plugins/Varo/libs");

	private final String name;
	private final File file;
	private final String link;
	private final String sha512sum;
	private final LoadPolicy loadPolicy;
	private boolean loaded;

	public Dependency(String name, String link, String sha512sum, LoadPolicy loadPolicy) {
		this.name = name;
		this.file = new File(LIB_FOLDER, this.getName() + ".jar");
		this.link = link;
		this.sha512sum = sha512sum;
		this.loadPolicy = loadPolicy;
	}

	public Dependency(String name, String link, String sha512sum) {
		this(name, link, sha512sum, config -> true);
	}

	public boolean shouldLoad(VaroConfig config) {
		return this.loadPolicy.shouldLoad(config);
	}

	public void load(VaroPlugin varo) throws IOException, InvalidSignatureException {
		// config may be null
		if (this.isLoaded() || !this.shouldLoad(varo.getVaroConfig()))
			return;

		this.loaded = true;

		if (!LIB_FOLDER.exists())
			LIB_FOLDER.mkdirs();

		// download jar if necessary
		if (!this.file.exists() || this.file.length() == 0) {
			varo.getLogger().info("Downloading " + this.link);
			this.file.createNewFile();

			FileDownloader fileDownloader = new FileDownloader(this.link, this.file.getAbsolutePath());
			fileDownloader.startDownload();
		}

		// check signature
		this.checkSignature();

		varo.getLogger().info("Loading " + this.name);
		this.init(varo);
	}

	public void checkSignature() throws IOException, InvalidSignatureException {
		String fileHash = Files.hash(this.getFile(), Hashing.sha512()).toString();
		if (!this.sha512sum.equals(fileHash))
			throw new InvalidSignatureException(this.getFile(), this.sha512sum, fileHash);
	}

	protected abstract void init(VaroPlugin varo);

	public String getName() {
		return this.name;
	}

	public File getFile() {
		return this.file;
	}

	public URL getUrl() throws MalformedURLException {
		return this.getFile().toURI().toURL();
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	public static interface LoadPolicy {
		boolean shouldLoad(VaroConfig config);
	}

	public static class ClassPolicy implements LoadPolicy {

		private final String className;

		public ClassPolicy(String className) {
			this.className = className;
		}

		@Override
		public boolean shouldLoad(VaroConfig config) {
			try {
				Class.forName(this.className);
				// already loaded
				return false;
			} catch (ClassNotFoundException e) {
				return true;
			}
		}
	}

	public static class ConfigEntriesPolicy extends ClassPolicy {

		private Function<VaroConfig, ConfigEntry<Boolean>[]> configEntryGetter;

		public ConfigEntriesPolicy(String className, Function<VaroConfig, ConfigEntry<Boolean>[]> configEntriesGetter) {
			super(className);
			this.configEntryGetter = configEntriesGetter;
		}

		@Override
		public boolean shouldLoad(VaroConfig config) {
			return super.shouldLoad(config) && Arrays.stream(this.configEntryGetter.apply(config)).allMatch(ConfigEntry::getValue);
		}
	}

	public static class ConfigEntryPolicy extends ConfigEntriesPolicy {

		@SuppressWarnings("unchecked")
		public ConfigEntryPolicy(String className, Function<VaroConfig, ConfigEntry<Boolean>> configEntryGetter) {
			super(className, config -> new ConfigEntry[] {configEntryGetter.apply(config)});
		}
	}

	@FunctionalInterface
	public interface ConfigEntryGetter {
		ConfigEntry<Boolean>[] getConfigEntries(VaroConfig config);
	}
}
