package de.cuuky.varo.gui.admin.game;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
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
        addItem(11, ItemBuilder.material(XMaterial.EMERALD).displayName("§cRestart Game").build(),
                (event) -> this.openNext(new ConfirmInventory(this, "§4Restart Game?", (accept) -> {
                    if (!accept) {
                        this.open();
                        return;
                    }
                    VaroPlayer vp = VaroPlayer.getPlayer(this.getPlayer());
                    if (!Main.getVaroGame().hasStarted()) {
                        this.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_IN_LOBBY.getValue(vp));
                        this.open();
                        return;
                    }
                    Main.getVaroGame().restart();
                    this.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_RESTARTED.getValue(vp));
                    this.open();
                })));

        addItem(15, ItemBuilder.material(XMaterial.DIAMOND_BLOCK).displayName("§bSet Lobby Location")
                .lore(new String[]{"§7Current: " + (Main.getVaroGame().getLobby() != null ? LOCATION_FORMAT.format(Main.getVaroGame().getLobby()) : "§c-")}).build(),
                (event) -> Main.getVaroGame().setLobby(getPlayer().getLocation()));

        addItem(13, ItemBuilder.material(XMaterial.BEACON).displayName("§2Set World Spawn")
                .lore(new String[]{"§7Current: " + (getPlayer().getWorld().getSpawnLocation() != null ? LOCATION_FORMAT.format(getPlayer().getWorld().getSpawnLocation()) : "§c-")})
                .build(),
                (event) -> getPlayer().getWorld().setSpawnLocation(getPlayer().getLocation().getBlockX(), getPlayer().getLocation().getBlockY(), getPlayer().getLocation().getBlockZ()));
    }
}