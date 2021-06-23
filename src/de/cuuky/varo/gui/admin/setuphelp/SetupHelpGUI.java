package de.cuuky.varo.gui.admin.setuphelp;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SetupHelpGUI extends VaroListInventory<SetupHelpGUI.SetupCheckList> {

    public enum SetupCheckList {
        BORDER_SETUP("Border Setup", "Haben sie die Border entsprechend gesetzt?", Material.STICK),
        CONFIG_SETUP("Config Setup", "Haben sie die Config augesetzt? (GUI)", Materials.SIGN.parseMaterial()),
        DISCORD_SETUP("Discord Setup", "Haben sie den DiscordBot aufgesetzt?", Material.ANVIL),
        LOBBY_SETUP("Lobby Setup", "Haben sie die Lobby gesetzt? (GUI) (/Lobby)", Material.DIAMOND),
        PORTAL_SETUP("Portal Setup", "Haben sie das Portal gesetzt?", Material.OBSIDIAN),
        SCOREBOARD_SETUP("Scoreboard Setup", "Haben sie das Scoreboard aufgesetzt?", Material.REDSTONE),
        SPAWN_SETUP("Spawn Setup", "Haben sie die Spawns gesetzt? /varo spawns", Materials.OAK_SLAB.parseMaterial()),
        TEAM_SETUP("Team Setup", "Haben sie alle Teams oder Spieler eingetragen? /varo team", Material.DIAMOND_HELMET),
        WORLDSPAWN_SETUP("Worlspawn Setup", "Haben sie den Worldspawn in der Mitte\ngesetzt? /setworldspawn", Material.BEACON);

        private boolean checked;
        private String[] description;
        private Material icon;
        private String name;

        SetupCheckList(String name, String description, Material icon) {
            this.name = name;
            this.description = description.split("\n");
            this.icon = icon;
            this.checked = false;
        }

        public String[] getDescription() {
            return description;
        }

        public Material getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    public SetupHelpGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, Arrays.asList(SetupCheckList.values()));
    }

    @Override
    public String getTitle() {
        return "§eSetup Assistant";
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    protected ItemStack getItemStack(SetupCheckList check) {
        return new ItemBuilder().displayname(Main.getColorCode() + check.getName())
                .itemstack(new ItemStack(check.isChecked() ? Materials.GUNPOWDER.parseMaterial() : check.getIcon()))
                .lore(check.getDescription()).build();
    }

    @Override
    protected ItemClick getClick(SetupCheckList check) {
        return (event) -> check.setChecked(!check.isChecked());
    }
}