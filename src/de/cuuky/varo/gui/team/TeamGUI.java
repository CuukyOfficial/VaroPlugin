package de.cuuky.varo.gui.team;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class TeamGUI extends VaroInventory {

    private final VaroTeam team;

    public TeamGUI(Player player, VaroTeam team) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.team = team;
    }

    @Override
    public String getTitle() {
        return "§8Team-ID: " + Main.getColorCode() + this.team.getId();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, new BuildItem().displayName("§cSet team-lives").lore("§7Current§8: §4" + team.getLifes()).itemstack(new ItemStack(Material.DIAMOND)).build(), (event) -> {
            Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Enter team-lives:", new ChatHookHandler() {

                @Override
                public boolean onChat(AsyncPlayerChatEvent event) {
                    double lives;
                    try {
                        lives = Double.parseDouble(event.getMessage());
                    } catch (NumberFormatException e) {
                        getPlayer().sendMessage(Main.getPrefix() + "Pleas enter a valid value for team lives");
                        return false;
                    }

                    team.setLifes(lives);
                    getPlayer().sendMessage(Main.getPrefix() + "Team lives of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getLifes() + "§7'");
                    open();
                    return true;
                }
            }));
            this.close();
        });

        addItem(3, new BuildItem().displayName("§7Set §3name").lore("§7Current§8: " + Main.getColorCode() + team.getDisplay())
                .itemstack(new ItemStack(Material.DIAMOND_HELMET)).build(), (event) -> {
            team.createNameChangeChatHook(VaroPlayer.getPlayer(this.getPlayer()), this::open);
            this.close();
        });

        addItem(5, new BuildItem().displayName("§7Set §acolorcode").lore("§7Current§8: §5" + (team.getColorCode() != null ? team.getColorCode() + "Like this!" : "-"))
                .itemstack(new ItemStack(Material.BOOK)).build(), (event) -> {
            Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Enter team colorcode:", new ChatHookHandler() {

                @Override
                public boolean onChat(AsyncPlayerChatEvent event) {
                    team.setColorCode(event.getMessage());
                    getPlayer().sendMessage(Main.getPrefix() + "Team colorocode of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getDisplay() + "§7'");
                    open();
                    return true;
                }
            }));
            this.close();
        });

        addItem(7, new BuildItem().displayName("§4Remove").itemstack(new ItemStack(Material.BUCKET)).build(), (event) -> {
            team.delete();
            back();
        });
    }
}