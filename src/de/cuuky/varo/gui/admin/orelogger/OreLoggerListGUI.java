package de.cuuky.varo.gui.admin.orelogger;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.BukkitUtils;
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

    public OreLoggerListGUI(Player opener) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener);
    }

    @Override
    public String getTitle() {
        return "§6OreLogger";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public List<LoggedBlock> getList() {
        return Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs();
    }

    @Override
    protected ItemStack getItemStack(LoggedBlock block) {
        List<String> lore = new ArrayList<>();
        lore.add("Block Type: §c" + block.getMaterial());
        lore.add("Mined at: §c" + String.format("x:%d y:%d z:%d world:%s", block.getX(), block.getY(), block.getZ(), block.getWorld()));
        lore.add("Time mined: §c" + DATE_FORMAT.format(new Date(block.getTimestamp())));
        lore.add("Mined by: " + Main.getColorCode() + block.getName());
        lore.add(" ");
        lore.add("§cClick to teleport!");
        return new ItemBuilder().displayname(block.getName()).itemstack(new ItemStack(Material.matchMaterial(block.getMaterial()))).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(LoggedBlock block) {
        return (event) -> BukkitUtils.saveTeleport(getPlayer(), new Location(Bukkit.getWorld(block.getWorld()), block.getX(), block.getY(), block.getZ()));
    }
}