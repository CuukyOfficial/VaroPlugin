package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.utils.BukkitUtils;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.utils.item.BuildSkull;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.events.EventListGUI;
import de.cuuky.varo.gui.items.ItemListSelectInventory;
import de.cuuky.varo.gui.player.PlayerListChooseGUI;
import de.cuuky.varo.gui.savable.PlayerSavableChooseGUI;
import de.cuuky.varo.gui.settings.VaroSettingsMenu;
import de.cuuky.varo.gui.strike.StrikeListGUI;
import de.cuuky.varo.gui.team.TeamCategoryChooseGUI;
import de.cuuky.varo.gui.youtube.YouTubeVideoListGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends VaroInventory {

    public MainMenu(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return Main.getProjectName();
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void refreshContent() {
    	addItem(3, new BuildItem().displayName("§bSpawn").itemstack(new ItemStack(Material.EMERALD))
                .lore(new String[]{new LocationFormat(getPlayer().getWorld().getSpawnLocation())
                        .format(Main.getColorCode() + "x§7, " + Main.getColorCode() + "y§7, " + Main.getColorCode() + "z")}).build(),
	        inventoryClickEvent -> {
	            if (!getPlayer().hasPermission("varo.teleportSpawn"))
	                return;
	
	            BukkitUtils.saveTeleport(getPlayer(), getPlayer().getWorld().getSpawnLocation());
        });

        addItem(5, new BuildItem().displayName("§7All §5Events").itemstack(new ItemStack(Material.APPLE)).build(),
                (event) -> this.openNext(new EventListGUI(getPlayer())));

        addItem(10, new BuildItem().displayName("§7Your §6Strikes").itemstack(new ItemStack(Material.PAPER))
                        .amount(getFixedSize(VaroPlayer.getPlayer(getPlayer()).getStats().getStrikes().size())).build(),
                (event) -> this.openNext(new StrikeListGUI(getPlayer(), getPlayer())));

        addItem(16, new BuildSkull().player(getPlayer()).amount(getFixedSize(VaroPlayer.getVaroPlayers().size()))
                .displayName("§7All §aPlayers").build(), (event) ->
                this.openNext(new PlayerListChooseGUI(getPlayer()))
        );

        addItem(18, new BuildItem().displayName("§7Your §5Videos").itemstack(new ItemStack(Material.COMPASS))
                .amount(getFixedSize(YouTubeVideo.getVideos().size())).build(), (event) ->
                this.openNext(new YouTubeVideoListGUI(getPlayer()))
        );

        addItem(22, new BuildItem().displayName("§7Your §eChests/Furnaces").itemstack(new ItemStack(Material.CHEST))
                .amount(getFixedSize(VaroSaveable.getSaveable(VaroPlayer.getPlayer(getPlayer())).size())).build(), (event) ->
                this.openNext(new PlayerSavableChooseGUI(getPlayer(), VaroPlayer.getPlayer(getPlayer())))
        );

        addItem(26, new BuildItem().displayName("§7All §2Teams").itemstack(new ItemStack(Material.DIAMOND_HELMET))
                .amount(getFixedSize(VaroTeam.getTeams().size())).build(), (event) ->
                this.openNext(new TeamCategoryChooseGUI(getPlayer()))
        );

        addItem(28, new BuildItem().displayName("§5Settings")
                .itemstack(Materials.CRAFTING_TABLE.parseItem()).build(), (event) ->
                this.openNext(new VaroSettingsMenu(getPlayer()))
        );

        addItem(34, new BuildItem().material(Materials.ITEM_FRAME).displayName("§aItem-Settings").build(),
                (e) -> this.openNext(new ItemListSelectInventory(getPlayer())));

        if (getPlayer().hasPermission("varo.admin")) {
            addItem(36, new BuildItem().displayName("§cAdmin-Section").lore("§cOnly available to admins")
                    .itemstack(new ItemStack(Materials.OAK_FENCE_GATE.parseMaterial())).build(), (event) ->
                    this.openNext(new AdminMainMenu(getPlayer()))
            );
        }

        if (ConfigSetting.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
            addItem(this.getInfo(Info.SIZE) - 1, new BuildItem().displayName("§5Info")
                    .itemstack(new ItemStack(Materials.MAP.parseMaterial())).build(), (event) -> sendInfo());
    }
}