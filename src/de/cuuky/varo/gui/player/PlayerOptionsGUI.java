package de.cuuky.varo.gui.player;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.InventoryNotifiable;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.gui.VaroPageInventory;
import de.cuuky.varo.gui.player.option.OptionsPage1;
import de.cuuky.varo.gui.player.option.OptionsPage2;
import de.cuuky.varo.utils.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.net.URL;

public class PlayerOptionsGUI extends VaroPageInventory implements InventoryNotifiable {

    private final Stats stats;
    private final VaroPlayer target;

    public PlayerOptionsGUI(Player opener, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener);

        this.target = target;
        this.stats = target.getStats();
        this.registerPage(1, () -> new OptionsPage1(this, target));
        this.registerPage(2, () -> new OptionsPage2(this, target));
    }

    private boolean isURl(String link) {
        try {
            new URL(link).openConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getDefaultTitle() {
        return "§2" + target.getName() + " §7(" + target.getId() + ")";
    }

    @Override
    public int getDefaultSize() {
        return 54;
    }

    public VaroPlayer getTarget() {
        return target;
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
    }

    // what is this i wont recode this
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        String itemname = event.getCurrentItem().getItemMeta().getDisplayName();

        if (itemname.contains("EpisodesPlayed")) {
            if (itemname.contains("Add"))
                stats.addSessionPlayed();
            else if (itemname.contains("Remove"))
                stats.setSessionsPlayed(stats.getSessionsPlayed() - 1);
        }

        if (itemname.contains("Countdown")) {
            if (itemname.contains("Reset"))
                stats.removeCountdown();
            else if (itemname.contains("Remove"))
                stats.setCountdown(1);
        }

        if (itemname.contains("Will InventoryClear"))
            stats.setWillClear(!stats.isWillClear());

        if (itemname.contains("Kill")) {
            if (itemname.contains("Add"))
                stats.addKill();
            else if (itemname.contains("Remove"))
                stats.setKills(stats.getKills() - 1);
        }

        if (itemname.contains("Session")) {
            if (itemname.contains("Add"))
                stats.setSessions(stats.getSessions() + 1);
            else if (itemname.contains("Remove"))
                stats.setSessions(stats.getSessions() - 1);
        }

        if (itemname.contains("Link")) {
            if (itemname.contains("Set")) {
                close();

                Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Enter Youtube-Link:", new ChatHookHandler() {

                    @Override
                    public boolean onChat(AsyncPlayerChatEvent event) {
                        if (!isURl(event.getMessage())) {
                            getPlayer().sendMessage(Main.getPrefix() + "Das ist kein Link!");
                            return false;
                        }

                        stats.setYoutubeLink(event.getMessage());
                        getPlayer().sendMessage(Main.getPrefix() + "Youtubelink gesetzt!");
                        open();
                        return true;
                    }
                }));
            } else if (itemname.contains("Remove"))
                stats.setYoutubeLink(null);
        }

        if (itemname.contains("Rank")) {
            if (itemname.contains("Set")) {
                close();

                Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Enter Rank", new ChatHookHandler() {

                    @Override
                    public boolean onChat(AsyncPlayerChatEvent event) {
                        target.setRank(new Rank(event.getMessage()));
                        getPlayer().sendMessage(Main.getPrefix() + "Rang gesetzt!");
                        open();
                        return true;
                    }
                }));
            } else if (itemname.contains("Remove"))
                target.setRank(null);
        }

        if (itemname.contains("State"))
            stats.setState(ArrayUtils.getNext(target.getStats().getState(), PlayerState.values()));

        if (itemname.contains("TimeUntilAddSession")) {
            stats.setTimeUntilAddSession(null);
            stats.setSessions(stats.getSessions() + 1);
        }

        this.update();
    }
}