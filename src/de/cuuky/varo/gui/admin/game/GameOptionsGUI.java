package de.cuuky.varo.gui.admin.game;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.utils.ArrayUtils;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.location.LocationFormat;
import de.varoplugin.cfw.location.SimpleLocationFormat;

public class GameOptionsGUI extends VaroInventory {
    
    private static final LocationFormat LOCATION_FORMAT = new SimpleLocationFormat("x, y, z in world");

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
        addItem(10, ItemBuilder.material(XMaterial.EMERALD).displayName("§aChange GameState")
                        .lore(new String[]{"§7Current: §c" + Main.getVaroGame().getGameState().getName()}).build(),
                (event) -> Main.getVaroGame().setGamestate(ArrayUtils.getNext(Main.getVaroGame().getGameState(), GameState.values())));

        addItem(16, ItemBuilder.material(XMaterial.DIAMOND_BLOCK).displayName("§bSet Lobby Location")
                        .lore(new String[]{"§7Current: " + (Main.getVaroGame().getLobby() != null ? LOCATION_FORMAT.format(Main.getVaroGame().getLobby()) : "§c-")}).build(),
                (event) -> Main.getVaroGame().setLobby(getPlayer().getLocation()));

        addItem(13, ItemBuilder.material(XMaterial.BEACON).displayName("§2Set World Spawn")
                        .lore(new String[]{"§7Current: " + (getPlayer().getWorld().getSpawnLocation() != null ? LOCATION_FORMAT.format(getPlayer().getWorld().getSpawnLocation()) : "§c-")})
                        .build(),
                (event) -> getPlayer().getWorld().setSpawnLocation(getPlayer().getLocation().getBlockX(), getPlayer().getLocation().getBlockY(), getPlayer().getLocation().getBlockZ()));
    }
}