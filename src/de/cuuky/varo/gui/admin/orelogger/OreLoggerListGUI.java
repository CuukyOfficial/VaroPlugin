package de.cuuky.varo.gui.admin.orelogger;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.BukkitUtils;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.logger.logger.LoggedBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OreLoggerListGUI extends VaroListInventory<LoggedBlock> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public OreLoggerListGUI(Player opener, List<LoggedBlock> blocks) {
        super(Main.getInventoryManager(), opener, blocks);
    }

    public OreLoggerListGUI(Player opener) {
        this(opener, Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs());
    }

    @Override
    public String getTitle() {
        return "§6OreLogger";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(LoggedBlock block) {
        List<String> lore = new ArrayList<>();
        String color = Main.getColorCode();
        lore.add("§7Mined at: " + color + String.format("x:%d y:%d z:%d world: %s", block.getX(), block.getY(), block.getZ(), block.getWorld()));
        lore.add("§7Time mined: " + color + DATE_FORMAT.format(new Date(block.getTimestamp())));
        lore.add("§7Mined by: " + color + block.getName());
        lore.add(" ");
        lore.add(color + "Click to teleport!");
        return new BuildItem().displayName(color + block.getMaterial())
            .itemstack(new ItemStack(Material.matchMaterial(block.getMaterial()))).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(LoggedBlock block) {
        return (event) -> BukkitUtils.saveTeleport(getPlayer(), new Location(Bukkit.getWorld(block.getWorld()), block.getX(), block.getY(), block.getZ()));
    }
}