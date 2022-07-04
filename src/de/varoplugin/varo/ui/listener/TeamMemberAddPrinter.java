package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.team.VaroTeamMemberAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class TeamMemberAddPrinter extends UiListener {

    @EventHandler
    public void onTeamableAdd(VaroTeamMemberAddEvent event) {
        // TODO: For all teamables
        Bukkit.getPlayer(event.getMember().getUuid()).sendMessage("Added to team " + event.getTeam().getName());
    }
}
