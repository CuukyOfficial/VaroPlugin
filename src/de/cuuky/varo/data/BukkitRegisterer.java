package de.cuuky.varo.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit.HitListener;
import de.cuuky.varo.command.VaroCommandListener;
import de.cuuky.varo.command.essentials.BorderCommand;
import de.cuuky.varo.command.essentials.BroadcastCommand;
import de.cuuky.varo.command.essentials.ChatClearCommand;
import de.cuuky.varo.command.essentials.CountdownCommand;
import de.cuuky.varo.command.essentials.DayCommand;
import de.cuuky.varo.command.essentials.FlyCommand;
import de.cuuky.varo.command.essentials.FreezeCommand;
import de.cuuky.varo.command.essentials.GamemodeCommand;
import de.cuuky.varo.command.essentials.HealCommand;
import de.cuuky.varo.command.essentials.InfoCommand;
import de.cuuky.varo.command.essentials.InvSeeCommand;
import de.cuuky.varo.command.essentials.LanguageCommand;
import de.cuuky.varo.command.essentials.MessageCommand;
import de.cuuky.varo.command.essentials.MuteCommand;
import de.cuuky.varo.command.essentials.NightCommand;
import de.cuuky.varo.command.essentials.PerformanceCommand;
import de.cuuky.varo.command.essentials.PingCommand;
import de.cuuky.varo.command.essentials.ProtectCommand;
import de.cuuky.varo.command.essentials.RainCommand;
import de.cuuky.varo.command.essentials.ReplyCommand;
import de.cuuky.varo.command.essentials.ReportCommand;
import de.cuuky.varo.command.essentials.SetWorldspawnCommand;
import de.cuuky.varo.command.essentials.SpawnCommand;
import de.cuuky.varo.command.essentials.SpeedCommand;
import de.cuuky.varo.command.essentials.SunCommand;
import de.cuuky.varo.command.essentials.TeamRequestCommand;
import de.cuuky.varo.command.essentials.ThunderCommand;
import de.cuuky.varo.command.essentials.UnflyCommand;
import de.cuuky.varo.command.essentials.UnfreezeCommand;
import de.cuuky.varo.command.essentials.UnmuteCommand;
import de.cuuky.varo.command.essentials.UnprotectCommand;
import de.cuuky.varo.command.essentials.UsageCommand;
import de.cuuky.varo.command.essentials.VanishCommand;
import de.cuuky.varo.command.essentials.VaroTimeCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.event.VaroEventListener;
import de.cuuky.varo.game.world.listener.VaroWorldListener;
import de.cuuky.varo.listener.EntityDamageByEntityListener;
import de.cuuky.varo.listener.EntityDamageListener;
import de.cuuky.varo.listener.FancyEffectListener;
import de.cuuky.varo.listener.InventoryMoveListener;
import de.cuuky.varo.listener.ItemDropListener;
import de.cuuky.varo.listener.ItemPickupListener;
import de.cuuky.varo.listener.NoPortalListener;
import de.cuuky.varo.listener.PlayerChatListener;
import de.cuuky.varo.listener.PlayerCommandPreprocessListener;
import de.cuuky.varo.listener.PlayerDeathListener;
import de.cuuky.varo.listener.PlayerHungerListener;
import de.cuuky.varo.listener.PlayerJoinListener;
import de.cuuky.varo.listener.PlayerLoginListener;
import de.cuuky.varo.listener.PlayerMoveListener;
import de.cuuky.varo.listener.PlayerQuitListener;
import de.cuuky.varo.listener.PlayerRegenerateListener;
import de.cuuky.varo.listener.PlayerRespawnListener;
import de.cuuky.varo.listener.PlayerTeleportListener;
import de.cuuky.varo.listener.ServerListPingListener;
import de.cuuky.varo.listener.WeatherChangeListener;
import de.cuuky.varo.listener.lists.BlockedEnchantmentsListener;
import de.cuuky.varo.listener.lists.BlockedItemsListener;
import de.cuuky.varo.listener.logging.DestroyedBlocksListener;
import de.cuuky.varo.listener.saveable.BlockBreakListener;
import de.cuuky.varo.listener.saveable.BlockPlaceListener;
import de.cuuky.varo.listener.saveable.EntityExplodeListener;
import de.cuuky.varo.listener.saveable.InventoryMoveSavableListener;
import de.cuuky.varo.listener.saveable.PlayerInteractListener;
import de.cuuky.varo.listener.saveable.SignChangeListener;
import de.cuuky.varo.listener.spectator.SpectatorListener;

public final class BukkitRegisterer {
	
	private static final SimpleCommandMap commandMap;
	private static final Constructor<PluginCommand> pluginCommandConstructor;
	
	static {
		try {
			Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
			
			pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			pluginCommandConstructor.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
			throw new Error(e);
		}
	}

	private static void registerDynamicCommand(String name, String desc, CommandExecutor executor, ConfigSetting configSetting, String... aliases) {
		if (!configSetting.getValueAsBoolean())
			return;
		
		PluginCommand command;
		try {
			command = pluginCommandConstructor.newInstance(name, Main.getInstance());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new Error("Unable to register command " + name, e);
		}
		command.setDescription(desc);
		command.setAliases(Arrays.asList(aliases));
		command.setExecutor(executor);
		
		commandMap.register(name, command);
	}

	private static void registerEvent(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
	}

	public static void registerCommands() {
		registerDynamicCommand("border", "Zeigt Infos zur Border", new BorderCommand(), ConfigSetting.COMMAND_BORDER_ENABLED, "setborder");
		registerDynamicCommand("broadcast", "Sendet eine Nachricht an den Server", new BroadcastCommand(), ConfigSetting.COMMAND_BROADCAST_ENABLED, "bc");
		registerDynamicCommand("chatclear", "Leert den Chat", new ChatClearCommand(), ConfigSetting.COMMAND_CHATCLEAR_ENABLED, "cc");
		registerDynamicCommand("countdown", "Startet einen Countdown", new CountdownCommand(), ConfigSetting.COMMAND_COUNTDOWN_ENABLED);
		registerDynamicCommand("day", "Setzt die Tageszeit auf Tag", new DayCommand(), ConfigSetting.COMMAND_TIME_ENABLED);
		registerDynamicCommand("fly", "Lässt dich oder einen Spieler fliegen", new FlyCommand(), ConfigSetting.COMMAND_FLY_ENABLED);
		registerDynamicCommand("freeze", "Lässt einen Spieler einfrieren", new FreezeCommand(), ConfigSetting.COMMAND_FREEZE_ENABLED, "nomove");
		registerDynamicCommand("gamemode", "Setzt den Spielmodus von dir oder einem Spieler", new GamemodeCommand(), ConfigSetting.COMMAND_GAMEMODE_ENABLED, "gm");
		registerDynamicCommand("heal", "Heilt einen Spieler", new HealCommand(), ConfigSetting.COMMAND_HEAL_ENABLED, "feed");
		registerDynamicCommand("info", "Zeigt Infos über einen Spieler", new InfoCommand(), ConfigSetting.COMMAND_INFO_ENABLED, "life");
		registerDynamicCommand("invsee", "Zeigt das Inventar eines anderen Spielers", new InvSeeCommand(), ConfigSetting.COMMAND_INVSEE_ENABLED, "inventorysee");
		registerDynamicCommand("language", "Changes language of player", new LanguageCommand(), ConfigSetting.COMMAND_LANGUAGE_ENABLED, "lang");
		registerDynamicCommand("message", "Schreibt einem Spieler eine Nachricht", new MessageCommand(), ConfigSetting.COMMAND_MESSAGE_ENABLED, "msg");
		registerDynamicCommand("mute", "Mutet einen Spieler", new MuteCommand(), ConfigSetting.COMMAND_MUTE_ENABLED);
		registerDynamicCommand("night", "Setzt die Tageszeit auf Nacht", new NightCommand(), ConfigSetting.COMMAND_TIME_ENABLED);
		registerDynamicCommand("performance", "Verbessert die Performance", new PerformanceCommand(), ConfigSetting.COMMAND_PERFORMANCE_ENABLED);
		registerDynamicCommand("ping", "Zeigt den Ping von dir oder einem Spieler", new PingCommand(), ConfigSetting.COMMAND_PING_ENABLED);
		registerDynamicCommand("protect", "Beschützt Spieler vor Schaden", new ProtectCommand(), ConfigSetting.COMMAND_PROTECT_ENABLED);
		registerDynamicCommand("rain", "Wechselt zu Regen", new RainCommand(), ConfigSetting.COMMAND_WEATHER_ENABLED);
		registerDynamicCommand("reply", "Antwortet einem Spieler", new ReplyCommand(), ConfigSetting.COMMAND_MESSAGE_ENABLED, "r");
		registerDynamicCommand("report", "Reporte einen Spieler", new ReportCommand(), ConfigSetting.COMMAND_REPORT_ENABLED);
		registerDynamicCommand("setworldspawn", "Setzt den Spawn", new SetWorldspawnCommand(), ConfigSetting.COMMAND_SETSPAWN_ENABLED);
		registerDynamicCommand("spawn", "Zeigt Distanz und Information zum Spawn", new SpawnCommand(), ConfigSetting.COMMAND_SPAWN_ENABLED);
		registerDynamicCommand("speed", "Setzt die Geschwindigkeit von dir oder einem Spieler", new SpeedCommand(), ConfigSetting.COMMAND_SPEED_ENABLED);
		registerDynamicCommand("sun", "Wechselt zu schönem Wetter", new SunCommand(), ConfigSetting.COMMAND_WEATHER_ENABLED);
		registerDynamicCommand("thunder", "Wechselt zu Gewitter", new ThunderCommand(), ConfigSetting.COMMAND_WEATHER_ENABLED);
		registerDynamicCommand(ConfigSetting.COMMAND_TR_NAME.getValueAsString(), "Sendet einem anderen Spieler eine Teamanfrage", new TeamRequestCommand(), ConfigSetting.COMMAND_TR_ENABLED);
		registerDynamicCommand("unfly", "Lässt dich oder einen Spieler nicht mehr fliegen", new UnflyCommand(), ConfigSetting.COMMAND_FLY_ENABLED);
		registerDynamicCommand("unfreeze", "Entfriert einen Spieler", new UnfreezeCommand(), ConfigSetting.COMMAND_FREEZE_ENABLED, "move");
		registerDynamicCommand("unmute", "Entmutet einen Spieler", new UnmuteCommand(), ConfigSetting.COMMAND_MUTE_ENABLED);
		registerDynamicCommand("unprotect", "Beendet die Beschützung vor Schaden von Spielern", new UnprotectCommand(), ConfigSetting.COMMAND_PROTECT_ENABLED);
		registerDynamicCommand("usage", "Zeigt die Nutzung des Servers", new UsageCommand(), ConfigSetting.COMMAND_USAGE_ENABLED);
		registerDynamicCommand("vanish", "Versteckt dich oder einen Spieler vor allen anderen", new VanishCommand(), ConfigSetting.COMMAND_VANISH_ENABLED, "v");
		registerDynamicCommand(ConfigSetting.COMMAND_VARO_NAME.getValueAsString(), "Hauptbefehl des Plugins", new VaroCommandListener(), ConfigSetting.COMMAND_VARO_ENABLED, "varoplugin");
		registerDynamicCommand("varotime", "Zeigt die verbleibende Session-Zeit an", new VaroTimeCommand(), ConfigSetting.COMMAND_VAROTIME_ENABLED, "vt");
		
	}

	public static void registerEvents() {
		registerEvent(new PlayerJoinListener());
		registerEvent(new PlayerQuitListener());
		registerEvent(new PlayerMoveListener());
		registerEvent(new DestroyedBlocksListener());
		registerEvent(new PlayerLoginListener());
		registerEvent(new BlockBreakListener());
		registerEvent(new EntityExplodeListener());
		registerEvent(new PlayerInteractListener());
		registerEvent(new SignChangeListener());
		registerEvent(new PlayerChatListener());
		registerEvent(new BlockPlaceListener());
		registerEvent(new InventoryMoveSavableListener());
		registerEvent(new ServerListPingListener());
		registerEvent(new PlayerDeathListener());
		registerEvent(new BlockedEnchantmentsListener());
		registerEvent(new BlockedItemsListener());
		registerEvent(new EntityDamageListener());
		registerEvent(new EntityDamageByEntityListener());
		registerEvent(new PlayerTeleportListener());
		registerEvent(new PlayerRegenerateListener());
		registerEvent(new PlayerHungerListener());
		registerEvent(new NoPortalListener());
		registerEvent(new PlayerCommandPreprocessListener());
		registerEvent(new SpectatorListener());
		registerEvent(new PlayerRespawnListener());
		registerEvent(new VaroEventListener());
		registerEvent(new VaroWorldListener());
		registerEvent(new FancyEffectListener());
		registerEvent(new HitListener());
		registerEvent(new InventoryMoveListener());
		registerEvent(new ItemDropListener());
		registerEvent(new ItemPickupListener());
		registerEvent(new WeatherChangeListener());
	}
}