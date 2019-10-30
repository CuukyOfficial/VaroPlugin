package de.cuuky.varo.spigot.downloader;

public class PluginDownloader extends FileDownloader {

	private static String PLUGIN_DIR = "plugins/";

	public PluginDownloader() {
		super(SPIGET_RESOURCES_URL + "/" + RESOURCE_ID + "/download", PLUGIN_DIR + "Varo.jar");
	}

	public PluginDownloader(int id, String path) {
		super(SPIGET_RESOURCES_URL + "/" + id + "/download", PLUGIN_DIR + path);
	}
}