package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.ui.UiElement;
import org.bukkit.command.CommandExecutor;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
// ONLY FOR TESTS
public abstract class VaroCommand implements UiElement, CommandExecutor {

    private final String name;
    private VaroPlugin plugin;

    public VaroCommand(String name) {
        this.name = name;
    }

    protected Varo getVaro() {
        return this.plugin.getVaro();
    }

    @Override
    public void register(VaroPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginCommand(this.name).setExecutor(this);
    }
}
