package de.cuuky.varo.gui;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

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
import de.varoplugin.cfw.inventory.Info;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.location.SimpleLocationFormat;
import de.varoplugin.cfw.player.SafeTeleport;

public class MainMenu extends VaroInventory {

    public MainMenu(Player player) {
        super(Main.getInventoryManager(), player);
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
    	addItem(3, ItemBuilder.material(XMaterial.EMERALD).displayName("§bSpawn")
                .lore(new String[]{new SimpleLocationFormat(Main.getColorCode() + "x§7, " + Main.getColorCode() + "y§7, " + Main.getColorCode() + "z").format(getPlayer().getWorld().getSpawnLocation())}).build(),
	        inventoryClickEvent -> {
	            if (!getPlayer().hasPermission("varo.teleportSpawn"))
	                return;
	
	            SafeTeleport.tp(getPlayer(), getPlayer().getWorld().getSpawnLocation());
        });

        addItem(5, ItemBuilder.material(XMaterial.APPLE).displayName("§7All §5Events").build(),
                (event) -> this.openNext(new EventListGUI(getPlayer())));

        addItem(10, ItemBuilder.material(XMaterial.PAPER).displayName("§7Your §6Strikes")
                        .amount(getFixedSize(VaroPlayer.getPlayer(getPlayer()).getStats().getStrikes().size())).build(),
                (event) -> this.openNext(new StrikeListGUI(getPlayer(), getPlayer())));

        addItem(16, ItemBuilder.skull(getPlayer()).amount(getFixedSize(VaroPlayer.getVaroPlayers().size()))
                .displayName("§7All §aPlayers").build(), (event) ->
                this.openNext(new PlayerListChooseGUI(getPlayer()))
        );

        addItem(18, ItemBuilder.material(XMaterial.COMPASS).displayName("§7Your §5Videos")
                .amount(getFixedSize(YouTubeVideo.getVideos().size())).build(), (event) ->
                this.openNext(new YouTubeVideoListGUI(getPlayer()))
        );

        addItem(22, ItemBuilder.material(XMaterial.CHEST).displayName("§7Your §eChests/Furnaces")
                .amount(getFixedSize(VaroSaveable.getSaveable(VaroPlayer.getPlayer(getPlayer())).size())).build(), (event) ->
                this.openNext(new PlayerSavableChooseGUI(getPlayer(), VaroPlayer.getPlayer(getPlayer())))
        );

        addItem(26, ItemBuilder.material(XMaterial.DIAMOND_HELMET).displayName("§7All §2Teams")
                .amount(getFixedSize(VaroTeam.getTeams().size())).build(), (event) ->
                this.openNext(new TeamCategoryChooseGUI(getPlayer()))
        );

        addItem(28, ItemBuilder.material(XMaterial.CRAFTING_TABLE).displayName("§5Settings").build(), (event) ->
                this.openNext(new VaroSettingsMenu(getPlayer()))
        );

        addItem(34, ItemBuilder.material(XMaterial.ITEM_FRAME).displayName("§aItem-Settings").build(),
                (e) -> this.openNext(new ItemListSelectInventory(getPlayer())));

        if (getPlayer().hasPermission("varo.admin")) {
            addItem(36, ItemBuilder.material(XMaterial.OAK_FENCE_GATE).displayName("§cAdmin-Section").lore("§cOnly available to admins").build(), (event) ->
                    this.openNext(new AdminMainMenu(getPlayer()))
            );
        }

        if (ConfigSetting.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
            addItem(this.getInfo(Info.SIZE) - 1, ItemBuilder.material(XMaterial.MAP).displayName("§5Info").build(), (event) -> sendInfo());
    }
}