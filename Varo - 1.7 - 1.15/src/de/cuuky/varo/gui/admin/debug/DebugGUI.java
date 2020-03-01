package de.cuuky.varo.gui.admin.debug;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.gui.utils.chat.ChatHook;
import de.cuuky.varo.gui.utils.chat.ChatHookListener;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.utils.varo.VaroUtils;
import de.cuuky.varo.version.types.Materials;

public class DebugGUI extends SuperInventory {

	public DebugGUI(Player opener) {
		super("ß6DEBUG", opener, 18, false);

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
		linkItemTo(1, new ItemBuilder().displayname("ßcTrigger Event").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[] { "ß7F√ºhrt ein Event aus, um den DiscordBot,", "TelegramBot, Config etc. zu testen" }).build(), new Runnable() {

			@Override
			public void run() {
				close(false);

				new ChatHook(opener, "ß7Enter Event Message:", new ChatHookListener() {

					@Override
					public void onChat(String message) {
						Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, message);
						opener.sendMessage(Main.getPrefix() + "ßaErfolgreich!");

					}
				});
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("ßcDo daily timer").itemstack(new ItemStack(Material.DAYLIGHT_DETECTOR)).lore(new String[] { "ß7F√ºhrt die Dinge aus, die sonst immer", "ß7Nachts ausgef√ºhrt werden, wie Sessionreset" }).build(), new Runnable() {

			@Override
			public void run() {
				Main.getDataManager().getDailyTimer().doDailyChecks();
				opener.sendMessage(Main.getPrefix() + "ßaErfolgreich!");
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("ßcTrigger Coordpost").itemstack(new ItemStack(Material.ANVIL)).amount(1).build(), new Runnable() {

			@Override
			public void run() {
				String post = "";
				for(VaroPlayer vp : VaroPlayer.getAlivePlayer())
					post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? VaroUtils.formatLocation(vp.getStats().getLastLocation(), "X:x Y:y Z:z in world") : "/");

				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post);
			}
		});

		return true;
	}
}
