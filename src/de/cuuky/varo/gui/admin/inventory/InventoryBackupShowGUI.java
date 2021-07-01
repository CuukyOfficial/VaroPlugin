package de.cuuky.varo.gui.admin.inventory;

import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.inventory.InfoProvider;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class InventoryBackupShowGUI extends VaroInventory {

    private final InventoryBackup backup;
    private final InfoProvider clickInfoProvider;

    public InventoryBackupShowGUI(Player player, InventoryBackup backup) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.backup = backup;
        this.clickInfoProvider = new InfoProvider() {
            @Override
            public int getPriority() {
                return 10;
            }

            @Override
            public List<Info<?>> getProvidedInfos() {
                return Arrays.asList(Info.CANCEL_CLICK, Info.PLAY_SOUND);
            }

            @Override
            public boolean cancelClick() {
                return false;
            }

            @Override
            public Consumer<Player> getSoundPlayer() {
                return null;
            }
        };
    }

    private boolean isStuff() {
        return super.getLastClickedSlot() <= 39 && super.getLastClickedSlot() >= 0;
    }

    @Override
    protected List<InfoProvider> getTemporaryProvider() {
        return this.isStuff() ? Arrays.asList(this.clickInfoProvider) : super.getTemporaryProvider();
    }

    @Override
    public String getTitle() {
        return "§7Inventory: " + Main.getColorCode() + backup.getVaroPlayer().getName();
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void update() {
        this.updateProvider();
        if (!this.isOpen())
            super.update();
    }

    @Override
    public void refreshContent() {
        for (int i = 0; i < 36; i++) {
            ItemStack st = backup.getInventory().getInventory().getContents()[i];
            addItem(i, st);
        }

        for (int i = 0; i < backup.getArmor().size(); i++) {
            ItemStack st = backup.getArmor().get(i);
            addItem(36 + i, st);
        }

        addItem(this.getSize() - 1, new ItemBuilder().itemstack(new ItemStack(Material.PAPER)).displayname("§aSave backup").build(), (event) -> {
            backup.getInventory().getInventory().clear();
            backup.getArmor().clear();

            for (int i = 0; i < 36; i++) {
                if (getInventory().getItem(i) == null)
                    continue;

                backup.getInventory().getInventory().setItem(i, getInventory().getItem(i));
            }

            for (int i = 0; i < 4; i++)
                backup.getArmor().add(getInventory().getItem(36 + i) == null ? new ItemStack(Material.AIR) : getInventory().getItem(36 + i));
        });
    }
}