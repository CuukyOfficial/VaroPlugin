package de.cuuky.varo.gui.team;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.VaroTeam;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;

public class TeamGUI extends VaroInventory {

    private final VaroTeam team;

    public TeamGUI(Player player, VaroTeam team) {
        super(Main.getInventoryManager(), player);

        this.team = team;
    }

    @Override
    public String getTitle() {
        return "§8Team-ID: " + Main.getColorCode() + this.team.getId() + " §8(" + Main.getColorCode() + this.team.getKills() + " §8Kills)";
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, ItemBuilder.material(XMaterial.DIAMOND).displayName("§cSet team-lives").lore("§7Current§8: §4" + team.getLifes()).build(), (event) -> {
            new PlayerChatHookBuilder().message("§7Enter team-lives:")
            .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                double lives;
                try {
                    lives = Double.parseDouble(hookEvent.getMessage());
                } catch (NumberFormatException e) {
                    getPlayer().sendMessage(Main.getPrefix() + "Pleas enter a valid value for team lives");
                    return;
                }

                team.setLifes(lives);
                getPlayer().sendMessage(Main.getPrefix() + "Team lives of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getLifes() + "§7'");
                open();
                hookEvent.getHook().unregister();
            }).complete(getPlayer(), Main.getInstance());
            this.close();
        });

        addItem(3, ItemBuilder.material(XMaterial.DIAMOND_HELMET).displayName("§7Set §3name").lore("§7Current§8: " + Main.getColorCode() + team.getDisplayName()).build(), (event) -> {
            team.createNameChangeChatHook(VaroPlayer.getPlayer(this.getPlayer()), this::open);
            this.close();
        });

        addItem(5, ItemBuilder.material(XMaterial.BOOK).displayName("§7Set §acolorcode").lore("§7Current§8: §5" + (team.getColorCode() != null ? team.getColorCode() + "Like this!" : "-")).build(), (event) -> {
            new PlayerChatHookBuilder().message("§7Enter team colorcode:")
            .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                team.setColorCode(hookEvent.getMessage());
                getPlayer().sendMessage(Main.getPrefix() + "Team colorocode of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getDisplayName() + "§7'");
                open();
                hookEvent.getHook().unregister();
            }).complete(getPlayer(), Main.getInstance());
            this.close();
        });

        addItem(7, ItemBuilder.material(XMaterial.BUCKET).displayName("§4Remove").build(), (event) -> {
            team.delete();
            back();
        });
    }
}