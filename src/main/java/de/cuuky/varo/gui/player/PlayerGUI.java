package de.cuuky.varo.gui.player;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.inventory.InventoryBackupListGUI;
import de.cuuky.varo.gui.savable.PlayerSavableChooseGUI;
import de.cuuky.varo.gui.strike.StrikeListGUI;
import de.cuuky.varo.gui.youtube.YouTubeVideoListGUI;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.location.LocationFormat;
import de.varoplugin.cfw.location.SimpleLocationFormat;
import de.varoplugin.cfw.player.SafeTeleport;

public class PlayerGUI extends VaroInventory {
    
    private static final LocationFormat LOCATION_FORMAT = new SimpleLocationFormat("x, y, z in world");

    private final VaroPlayer target;

    public PlayerGUI(Player player, VaroPlayer target) {
        super(Main.getInventoryManager(), player);

        this.target = target;
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + target.getName() + " §7(" + target.getId() + ")";
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void refreshContent() {
        addItem(4, ItemBuilder.material(XMaterial.MAP).displayName("§2Last Location")
                        .lore(new String[]{"§cClick to teleport", "§7" + (target.getStats().getLastLocation() != null ? LOCATION_FORMAT.format(target.getStats().getLastLocation()) : "/")}).build(),
                (event) -> {
                    if (target.getStats().getLastLocation() == null)
                        return;

                    SafeTeleport.tp(getPlayer(), target.getStats().getLastLocation());
                });
        
        addItem(11, ItemBuilder.material(XMaterial.DIAMOND_CHESTPLATE).displayName("§aInventory Backups").lore("§7Click to see more options").build(),
                (event) -> this.openNext(new InventoryBackupListGUI(getPlayer(), target)));
        
        addItem(15, ItemBuilder.material(XMaterial.PAPER).displayName("§6Strikes")
                .amount(getFixedSize(VaroPlayer.getPlayer(getPlayer()).getStats().getStrikes().size())).build(),
                (event) -> this.openNext(new StrikeListGUI(getPlayer(), this.target)));

        addItem(18, ItemBuilder.material(XMaterial.CHEST).displayName("§eChests/Furnaces")
                .amount(getFixedSize(target.getStats().getSaveables().size())).build(),
                (event) -> this.openNext(new PlayerSavableChooseGUI(getPlayer(), target)));
        
        addItem(26, ItemBuilder.material(XMaterial.SKELETON_SKULL).displayName("§cReset").build(),
                (event) -> this.openNext(new ConfirmInventory(this, "§4Reset?", (accept) -> {
                    if (accept) {
                        if (target.isOnline())
                            target.getPlayer().kickPlayer("§7You've been resetted.\n§cPlease join again.");

                        target.getStats().loadDefaults();
                    }

                    this.open();
                })));
        
        addItem(22, ItemBuilder.material(XMaterial.BOOK).displayName("§5More Options").lore(target.getStats().getStatsListed()).build(),
                (event) -> this.openNext(new PlayerOptionsGUI(getPlayer(), target)));
        
        addItem(28, ItemBuilder.material(XMaterial.COMPASS).displayName("§5Videos").build(),
                (event) -> this.openNext(new YouTubeVideoListGUI(getPlayer(), this.target)));

        addItem(34, ItemBuilder.material(XMaterial.RED_DYE).displayName("§4Remove").build(),
                (event) -> this.openNext(new ConfirmInventory(this, "§4Remove?", (accept) -> {
                    if (accept) {
                        this.back();
                        target.delete();
                    } else this.open();
                })));
    }
}