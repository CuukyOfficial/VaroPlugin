package de.cuuky.varo.data;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommandListener;
import de.cuuky.varo.command.essentials.AntiXrayCommand;
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
import de.cuuky.varo.command.essentials.ThunderCommand;
import de.cuuky.varo.command.essentials.UnflyCommand;
import de.cuuky.varo.command.essentials.UnfreezeCommand;
import de.cuuky.varo.command.essentials.UnmuteCommand;
import de.cuuky.varo.command.essentials.UnprotectCommand;
import de.cuuky.varo.command.essentials.UsageCommand;
import de.cuuky.varo.command.essentials.VanishCommand;
import de.cuuky.varo.event.VaroEventListener;
import de.cuuky.varo.game.world.listener.VaroWorldListener;
import de.cuuky.varo.gui.utils.InventoryListener;
import de.cuuky.varo.item.hook.HookListener;
import de.cuuky.varo.listener.EntityDamageByEntityListener;
import de.cuuky.varo.listener.EntityDamageListener;
import de.cuuky.varo.listener.FancyEffectListener;
import de.cuuky.varo.listener.HealtLoseListener;
import de.cuuky.varo.listener.NoPortalListener;
import de.cuuky.varo.listener.PlayerAchievementListener;
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
import de.cuuky.varo.listener.lists.BlockedEnchantmentsListener;
import de.cuuky.varo.listener.lists.BlockedItemsListener;
import de.cuuky.varo.listener.logging.DestroyedBlocksListener;
import de.cuuky.varo.listener.saveable.BlockBreakListener;
import de.cuuky.varo.listener.saveable.BlockPlaceListener;
import de.cuuky.varo.listener.saveable.EntityExplodeListener;
import de.cuuky.varo.listener.saveable.InventoryMoveListener;
import de.cuuky.varo.listener.saveable.PlayerInteractListener;
import de.cuuky.varo.listener.saveable.SignChangeListener;
import de.cuuky.varo.listener.spectator.SpectatorListener;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public final class BukkitRegisterer {

	private static void registerCommand(String name, CommandExecutor cm) {
		Main.getInstance().getCommand(name).setExecutor(cm);
	}

	private static void registerEvent(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
	}

	public static void registerCommands() {
		registerCommand("varo", new VaroCommandListener());
		registerCommand("antixray", new AntiXrayCommand());
		registerCommand("broadcast", new BroadcastCommand());
		registerCommand("chatclear", new ChatClearCommand());
		registerCommand("day", new DayCommand());
		registerCommand("fly", new FlyCommand());
		registerCommand("freeze", new FreezeCommand());
		registerCommand("gamemode", new GamemodeCommand());
		registerCommand("heal", new HealCommand());
		registerCommand("info", new InfoCommand());
		registerCommand("invsee", new InvSeeCommand());
		registerCommand("message", new MessageCommand());
		registerCommand("mute", new MuteCommand());
		registerCommand("night", new NightCommand());
		registerCommand("ping", new PingCommand());
		registerCommand("reply", new ReplyCommand());
		registerCommand("speed", new SpeedCommand());
		registerCommand("vanish", new VanishCommand());
		registerCommand("report", new ReportCommand());
		registerCommand("unfly", new UnflyCommand());
		registerCommand("unfreeze", new UnfreezeCommand());
		registerCommand("unmute", new UnmuteCommand());
		registerCommand("unprotect", new UnprotectCommand());
		registerCommand("usage", new UsageCommand());
		registerCommand("border", new BorderCommand());
		registerCommand("setworldspawn", new SetWorldspawnCommand());
		registerCommand("spawn", new SpawnCommand());
		registerCommand("sun", new SunCommand());
		registerCommand("rain", new RainCommand());
		registerCommand("thunder", new ThunderCommand());
		registerCommand("protect", new ProtectCommand());
		registerCommand("countdown", new CountdownCommand());
		registerCommand("performance", new PerformanceCommand());
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
		registerEvent(new InventoryMoveListener());
		registerEvent(new InventoryListener());
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
		registerEvent(new HealtLoseListener());
		registerEvent(new SpectatorListener());
		registerEvent(new PlayerRespawnListener());
		registerEvent(new VaroEventListener());
		registerEvent(new HookListener());
		registerEvent(new VaroWorldListener());
		registerEvent(new FancyEffectListener());

		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_11))
			registerEvent(new PlayerAchievementListener());
	}
}