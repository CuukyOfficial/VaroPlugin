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
        return super.getLastClickedSlot() < this.backup.getAllContents().length && super.getLastClickedSlot() >= 0;
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
    	ItemStack[] contents = this.backup.getAllContents();
        for (int i = 0; i < 45; i++) {
            ItemStack st = i < contents.length ? contents[i] : getFillerStack();
            addItem(i, st);
        }

        addItem(this.getSize() - 1, new ItemBuilder().itemstack(new ItemStack(Material.PAPER)).displayname("§aSave backup").build(), (event) -> {
            this.backup.clear();
            
            for (int i = 0; i < contents.length; i++) {
            	ItemStack item = getInventory().getItem(i);
            	if (item != null)
            		this.backup.setItem(i, item);
            }
        });
    }
}