package de.cuuky.varo.serialize;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.serialize.field.FieldLoader;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.serialize.serializer.VaroDeserializer;
import de.cuuky.varo.serialize.serializer.VaroSerializer;

public class VaroSerializeObject extends VaroSerializeHandler {

	/*
	 * Pls dont look too close at this class. I made this withing like 4 hours
	 * and it works so I don't wanna change anything. I know, this is pretty
	 * ugly and too much code in one class but I'm really happy it works like
	 * this
	 */

	private Class<? extends VaroSerializeable> clazz;
	private YamlConfiguration configuration;

	private FieldLoader fieldLoader;
	private File file;

	public VaroSerializeObject(Class<? extends VaroSerializeable> clazz) {
		this.clazz = clazz;
		this.fieldLoader = new FieldLoader(clazz);

		handler.add(this);
	}

	public VaroSerializeObject(Class<? extends VaroSerializeable> clazz, String fileName) {
		this(clazz);

		if(files.get(fileName) != null) {
			this.file = files.get(fileName);
			this.configuration = configs.get(fileName);
		} else {
			this.file = new File("plugins/Varo", fileName);
			files.put(fileName, file);
			this.configuration = YamlConfiguration.loadConfiguration(file);
			configs.put(fileName, configuration);
		}
	}

	public Class<? extends VaroSerializeable> getClazz() {
		return clazz;
	}

	public YamlConfiguration getConfiguration() {
		return configuration;
	}

	public FieldLoader getFieldLoader() {
		return fieldLoader;
	}

	public void onSave() {}

	protected void clearOld() {
		Map<String, Object> configValues = configuration.getValues(false);
		for(Map.Entry<String, Object> entry : configValues.entrySet())
			configuration.set(entry.getKey(), null);
	}

	protected void load() {
		for(String string : configuration.getKeys(true)) {
			Object obj = configuration.get(string);
			if(obj instanceof MemorySection) {
				if(string.contains("."))
					continue;

				new VaroDeserializer((MemorySection) obj, this).deserialize();
			}
		}
	}

	protected void save(String saveUnder, VaroSerializeable instance, YamlConfiguration saveTo) {
		try {
			new VaroSerializer(saveUnder, instance, saveTo);
		} catch(NoClassDefFoundError e) {
			System.out.println(Main.getConsolePrefix() + "Failed to save files - did you change the version while the server was running?");
		}
	}

	protected void saveFile() {
		try {
			configuration.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}