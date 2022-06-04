package de.varoplugin.varo.ui.listener;

import de.cuuky.varo.event.VaroEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroDebuggerListener extends UiListener {

    public VaroDebuggerListener(Plugin plugin) {
        super(plugin);
    }

    // this does not work, would be nice to figure out if there is a way to do this
    @EventHandler
    public void onVaroEvent(VaroEvent event) {
        System.out.println(event.toString());
    }
}