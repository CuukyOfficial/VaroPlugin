package de.cuuky.varo.gui.admin.debug;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class DebugGUI extends SuperInventory {

	public DebugGUI(Player opener) {
		super("§6DEBUG", opener, 18, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§cTrigger Event").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[] { "§7Fuehrt ein Event aus, um den DiscordBot,", "TelegramBot, Config etc. zu testen" }).build(), new Runnable() {

			@Override
			public void run() {
				close(false);

				new ChatHook(opener, "§7Enter Event Message:", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, event.getMessage());
						opener.sendMessage(Main.getPrefix() + "§aErfolgreich!");
						return true;
					}
				});
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§cDo daily timer").itemstack(new ItemStack(Material.DAYLIGHT_DETECTOR)).lore(new String[] { "§7Fuehrt die Dinge aus, die sonst immer", "§7Nachts ausgefuehrt werden, wie Sessionreset" }).build(), new Runnable() {

			@Override
			public void run() {
				Main.getDataManager().getDailyTimer().doDailyChecks();
				opener.sendMessage(Main.getPrefix() + "§aErfolgreich!");
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§cTrigger Coordpost").itemstack(new ItemStack(Material.ANVIL)).amount(1).build(), new Runnable() {

			@Override
			public void run() {
				String post = "";
				for (VaroPlayer vp : VaroPlayer.getAlivePlayer())
					post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? new LocationFormat(vp.getStats().getLastLocation()).format("X:x Y:y Z:z in world") : "/");

				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post);
			}
		});

		return true;
	}
}
