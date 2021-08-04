package de.cuuky.varo.data.plugin;

import java.io.File;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.listener.PermissionSendListener;

public class LabymodPluginLibrary extends PluginLibrary {

	public LabymodPluginLibrary() {
		super("Labymod", "net.labymod.serverapi.LabyModAPI", "http://api.spiget.org/v2/resources/52423/download",
				ConfigSetting.DISABLE_LABYMOD_FUNCTIONS, ConfigSetting.KICK_LABYMOD_PLAYER,
				ConfigSetting.ONLY_LABYMOD_PLAYER);
	}

	@Override
	protected void init(File jar) {
		super.init(jar);
		Bukkit.getPluginManager().registerEvents(new PermissionSendListener(), Main.getInstance());
	}
}
