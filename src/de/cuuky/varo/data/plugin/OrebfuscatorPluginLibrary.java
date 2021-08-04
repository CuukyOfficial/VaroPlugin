package de.cuuky.varo.data.plugin;

public class OrebfuscatorPluginLibrary extends PluginLibrary {

	public OrebfuscatorPluginLibrary() {
		super("Orebfuscator4", null,
				"https://github.com/lishid/Orebfuscator/releases/download/4.4.3/orebfuscator-4.4.3.jar");
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}
}
