package de.cuuky.varo.gui.admin.game;

import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.utils.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameOptionsGUI extends VaroInventory {

    public GameOptionsGUI(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§2Game";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        addItem(10, new BuildItem().displayName("§aChange GameState")
                        .itemstack(new ItemStack(Material.EMERALD))
                        .lore(new String[]{"§7Current: §c" + Main.getVaroGame().getGameState().getName()}).build(),
                (event) -> Main.getVaroGame().setGamestate(ArrayUtils.getNext(Main.getVaroGame().getGameState(), GameState.values())));

        addItem(16, new BuildItem().displayName("§bSet Lobby Location")
                        .itemstack(new ItemStack(Material.DIAMOND_BLOCK))
                        .lore(new String[]{"§7Current: " + (Main.getVaroGame().getLobby() != null ? new LocationFormat(Main.getVaroGame().getLobby())
                                .format("x, y, z in world") : "§c-")}).build(),
                (event) -> Main.getVaroGame().setLobby(getPlayer().getLocation()));

        addItem(13, new BuildItem().displayName("§2Set World Spawn")
                        .itemstack(new ItemStack(Material.BEACON))
                        .lore(new String[]{"§7Current: " + (getPlayer().getWorld().getSpawnLocation() != null ? new LocationFormat(getPlayer().getWorld().getSpawnLocation()).format("x, y, z in world") : "§c-")})
                        .build(),
                (event) -> getPlayer().getWorld().setSpawnLocation(getPlayer().getLocation().getBlockX(), getPlayer().getLocation().getBlockY(), getPlayer().getLocation().getBlockZ()));
    }
}