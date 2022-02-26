package de.cuuky.varo.gui.admin.setuphelp;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.app.Main;
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

        private final String name;
        private final Material icon;
        private final String[] description;
        private boolean checked;

        SetupCheckList(String name, String description, Material icon) {
            this.name = name;
            this.description = description.split("\n");
            this.icon = icon;
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
    protected ItemStack getItemStack(SetupCheckList check) {
        return new BuildItem().displayName(Main.getColorCode() + check.name)
                .itemstack(new ItemStack(check.checked ? Materials.GUNPOWDER.parseMaterial() : check.icon))
                .lore(check.description).build();
    }

    @Override
    protected ItemClick getClick(SetupCheckList check) {
        return (event) -> check.checked = !check.checked;
    }
}