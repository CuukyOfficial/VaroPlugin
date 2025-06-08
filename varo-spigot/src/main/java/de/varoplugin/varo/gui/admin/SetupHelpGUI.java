package de.varoplugin.varo.gui.admin;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;

public class SetupHelpGUI extends VaroListInventory<SetupHelpGUI.SetupCheckList> {

    public enum SetupCheckList {
        BORDER_SETUP("Border Setup", "Haben sie die Border entsprechend gesetzt?", XMaterial.STICK),
        CONFIG_SETUP("Config Setup", "Haben sie die Config augesetzt? (GUI)", XMaterial.OAK_SIGN),
        DISCORD_SETUP("Discord Setup", "Haben sie den DiscordBot aufgesetzt?", XMaterial.ANVIL),
        LOBBY_SETUP("Lobby Setup", "Haben sie die Lobby gesetzt? (GUI) (/Lobby)", XMaterial.DIAMOND),
        PORTAL_SETUP("Portal Setup", "Haben sie das Portal gesetzt?", XMaterial.OBSIDIAN),
        SCOREBOARD_SETUP("Scoreboard Setup", "Haben sie das Scoreboard aufgesetzt?", XMaterial.REDSTONE),
        SPAWN_SETUP("Spawn Setup", "Haben sie die Spawns gesetzt? /varo spawns", XMaterial.OAK_SLAB),
        TEAM_SETUP("Team Setup", "Haben sie alle Teams oder Spieler eingetragen? /varo team", XMaterial.DIAMOND_HELMET),
        WORLDSPAWN_SETUP("Worlspawn Setup", "Haben sie den Worldspawn in der Mitte\ngesetzt? /setworldspawn", XMaterial.BEACON);

        private final String name;
        private final XMaterial icon;
        private final String[] description;
        private boolean checked;

        SetupCheckList(String name, String description, XMaterial icon) {
            this.name = name;
            this.description = description.split("\n");
            this.icon = icon;
        }
    }

    public SetupHelpGUI(Player player) {
        super(Main.getInventoryManager(), player, Arrays.asList(SetupCheckList.values()));
    }

    @Override
    public String getTitle() {
        return "§eSetup Assistant";
    }

    @Override
    protected ItemStack getItemStack(int index, SetupCheckList check) {
        return ItemBuilder.material(check.checked ? XMaterial.GUNPOWDER : check.icon).displayName(Main.getColorCode() + check.name).lore(check.description).build();
    }

    @Override
    protected ItemClick getClick(SetupCheckList check) {
        return (event) -> check.checked = !check.checked;
    }
}