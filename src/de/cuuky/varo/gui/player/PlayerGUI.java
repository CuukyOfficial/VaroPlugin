package de.cuuky.varo.gui.player;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.BukkitUtils;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroConfirmInventory;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.inventory.InventoryBackupListGUI;
import de.cuuky.varo.gui.savable.PlayerSavableChooseGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerGUI extends VaroInventory {

    private final VaroPlayer target;

    public PlayerGUI(Player player, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.target = target;
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + target.getName() + " §7(" + target.getId() + ")";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        // TODO: Strikes

        addItem(1, new ItemBuilder().displayname("§aInventory Backups")
                        .itemstack(new ItemStack(Material.DIAMOND_CHESTPLATE)).lore("§7Click to see more options").build(),
                (event) -> this.openNext(new InventoryBackupListGUI(getPlayer(), target)));

        addItem(4, new ItemBuilder().displayname("§2Last Location")
                        .itemstack(new ItemStack(Materials.MAP.parseMaterial()))
                        .lore(new String[]{"§cClick to teleport", "§7" + (target.getStats().getLastLocation() != null ? new LocationFormat(target.getStats().getLastLocation()).format("x, y, z in world") : "/")}).build(),
                (event) -> {
                    if (target.getStats().getLastLocation() == null)
                        return;

                    BukkitUtils.saveTeleport(getPlayer(), target.getStats().getLastLocation());
                });

        addItem(7, new ItemBuilder().displayname("§eKisten/Öfen").itemstack(Materials.CHEST.parseItem())
                        .amount(getFixedSize(target.getStats().getSaveables().size())).build(),
                (event) -> this.openNext(new PlayerSavableChooseGUI(getPlayer(), target)));

        addItem(11, new ItemBuilder().displayname("§4Remove")
                .itemstack(Materials.ROSE_RED.parseItem()).build(), (event) ->
                this.openNext(new VaroConfirmInventory(this, "§cRemove?", (accept) -> {
                    this.back();
                    target.delete();
                    this.open();
                })));

        addItem(15, new ItemBuilder().displayname("§cReset")
                .itemstack(Materials.SKELETON_SKULL.parseItem()).build(), (event) ->
                this.openNext(new VaroConfirmInventory(this, "§cReset?", (accept) -> {
                    if (target.isOnline())
                        target.getPlayer().kickPlayer("§7You've been resetted.\n§cPlease join again.");

                    target.getStats().loadDefaults();
                    this.open();
                })));

        addItem(22, new ItemBuilder().displayname("§5More Options")
                        .itemstack(new ItemStack(Material.BOOK)).lore(target.getStats().getStatsListed()).build(),
                (event) -> this.openNext(new PlayerOptionsGUI(getPlayer(), target)));
    }
}