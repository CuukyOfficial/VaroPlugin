package de.cuuky.varo.command.essentials;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.data.plugin.OrebfuscatorPluginLibrary;
import de.cuuky.varo.entity.player.VaroPlayer;

public class AntiXrayCommand implements CommandExecutor {

	private boolean antiXrayActivated;
	private byte xrayAvailable;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.xray")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}
		this.antiXrayActivated = false;

		if (!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_8)) {
			this.xrayAvailable = 0;
		} else if (!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_13)) {
			this.xrayAvailable = 1;
		} else {
			this.xrayAvailable = 2;
		}

		if (VersionUtils.getSpigot() == null)
			if (this.xrayAvailable != 1)
				this.xrayAvailable = 2;

		YamlConfiguration spigotConfig = null;
		if (this.xrayAvailable == 0)
			try {
				Method m = VersionUtils.getSpigot().getClass().getMethod("getConfig");
				m.setAccessible(true);
				spigotConfig = (YamlConfiguration) m.invoke(VersionUtils.getSpigot());
				m.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (this.xrayAvailable == 0) {
			String enabled = spigotConfig.getString("world-settings.default.anti-xray.enabled");
			String engineMode = spigotConfig.getString("world-settings.default.anti-xray.engine-mode");
			if (enabled == null || engineMode == null) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ERROR_NOT_AVAIALABLE.getValue(vp)
						.replace("%version%", Bukkit.getName()));
				return false;
			}
			if (!enabled.contentEquals("true") || !engineMode.contentEquals("2")) {
				this.antiXrayActivated = false;
			} else {
				this.antiXrayActivated = true;
			}
		}

		if (this.xrayAvailable == 1) {
			File file = new File("plugins/Orebfuscator4", "config.yml");
			if (file.exists()) {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				String enabled = config.getString("Booleans.Enabled");
				String engineMode = config.getString("Integers.EngineMode");
				if (!enabled.contentEquals("true") || !engineMode.contentEquals("2")) {
					this.antiXrayActivated = false;
				} else {
					this.antiXrayActivated = true;
				}
			} else {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_INSTALLING_PLUGIN.getValue(vp));

				Main.getDataManager().getLibraryLoader().loadLibraryIfNecessary(new OrebfuscatorPluginLibrary());

				/*
				 * if (!xrayDownload) { sender.sendMessage(Main.getPrefix() +
				 * ConfigMessages.COMMANDS_XRAY_INSTALLING_ERROR.getValue(vp)); return false; }
				 */

				Bukkit.getServer().shutdown();
				return false;
			}
		}

		if (this.xrayAvailable == 2) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_VERSION_NOT_AVAIALABLE.getValue(vp)
					.replace("%version%", Bukkit.getName() + " 1." + VersionUtils.getVersion().getIdentifier()));
			return false;
		}

		if (args.length != 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
			sender.sendMessage(Main.getPrefix()
					+ ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue(vp).replace("%category%", "Anti-Xray"));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_STATUS.getValue(vp).replace("%status%",
					this.antiXrayActivated ? ConfigMessages.COMMANDS_XRAY_STATUS_ACTIVATED.getValue(vp)
							: ConfigMessages.COMMANDS_XRAY_STATUS_DEACTIVATED.getValue(vp)));
			sender.sendMessage(Main.getPrefix() + "/antixray <on/off>");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue(vp));
			return false;
		}

		if (args[0].equalsIgnoreCase("on")) {
			if (this.antiXrayActivated) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ALREADY_ACTIVATED.getValue(vp));
				return false;
			} else {
				if (this.xrayAvailable == 0) {
					spigotConfig.set("world-settings.default.anti-xray.enabled", true);
					spigotConfig.set("world-settings.default.anti-xray.engine-mode", 2);

					try {
						spigotConfig.save("spigot.yml");
					} catch (IOException e) {
						sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ERROR_NOT_AVAIALABLE
								.getValue(vp).replace("%version%", Bukkit.getName()));
						return false;
					}
				} else if (this.xrayAvailable == 1) {
					Bukkit.dispatchCommand(sender, "ofc enable");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ofc engine 2");
				}

				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ACTIVATED.getValue(vp));
			}
		} else if (args[0].equalsIgnoreCase("off")) {
			if (!this.antiXrayActivated) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ALREADY_DEACTIVATED.getValue(vp));
				return false;
			} else {
				if (this.xrayAvailable == 0) {
					spigotConfig.set("world-settings.default.anti-xray.enabled", true);
					spigotConfig.set("world-settings.default.anti-xray.engine-mode", 1);

					try {
						spigotConfig.save("spigot.yml");
					} catch (IOException e) {
						sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_ERROR_NOT_AVAIALABLE
								.getValue(vp).replace("%version%", Bukkit.getName()));
						return false;
					}
				} else if (this.xrayAvailable == 1) {
					Bukkit.dispatchCommand(sender, "ofc disable");
				}

				sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_XRAY_DEACTIVATED.getValue(vp));
			}
		}

		Bukkit.getServer().shutdown();
		return false;
	}
}