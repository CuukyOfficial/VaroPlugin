package de.cuuky.varo.data.plugin;

public class OrebfuscatorPluginLibrary extends PluginLibrary {

	public OrebfuscatorPluginLibrary() {
		super("Orebfuscator4", null, "http://api.spiget.org/v2/resources/22818/download");
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}
}
