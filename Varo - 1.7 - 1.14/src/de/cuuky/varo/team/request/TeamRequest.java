package de.cuuky.varo.team.request;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.gui.utils.chat.ChatHook;
import de.cuuky.varo.gui.utils.chat.ChatHookListener;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.Team;

public class TeamRequest {

	private static ArrayList<TeamRequest> requests = new ArrayList<>();

	private VaroPlayer invitor;
	private VaroPlayer invited;
	private int sched;

	public TeamRequest(VaroPlayer invitor, VaroPlayer invited) {
		this.invitor = invitor;
		this.invited = invited;

		requests.add(this);

		startSched();
	}

	public VaroPlayer getInvited() {
		return invited;
	}

	public VaroPlayer getInvitor() {
		return invitor;
	}

	private void sendChatHook() {
		new ChatHook(invitor.getPlayer(),
				Main.getColorCode()
						+ ConfigMessages.TEAMREQUEST_ENTER_TEAMNAME.getValue().replace("%invited%", invited.getName()),
				new ChatHookListener() {

					@Override
					public void onChat(String message) {
						if (message.contains("#")) {
							invitor.sendMessage(Main.getPrefix() + ConfigMessages.TEAMREQUEST_NO_HASHTAG.getValue());
							sendChatHook();
							return;
						}

						if (message.contains("&") && !invitor.getPlayer().hasPermission("Varo.teamcolorcode")) {
							invitor.sendMessage(Main.getPrefix() + ConfigMessages.TEAMREQUEST_NO_COLORCODE.getValue());
							sendChatHook();
							return;
						}

						int maxLength = ConfigEntry.TEAMREQUEST_MAXTEAMNAMELENGTH.getValueAsInt();
						if (message.length() > (maxLength) - 1) {
							invitor.sendMessage(Main.getPrefix() + ConfigMessages.TEAMREQUEST_MAX_TEAMNAME_LENGTH
									.getValue().replace("%maxLength%", String.valueOf(maxLength)));
							sendChatHook();
							return;
						}

						addToTeam(invitor.getTeam() == null ? new Team(message) : invitor.getTeam());
					}
				});
	}

	private void addToTeam(Team team) {
		if (!team.isMember(invitor))
			team.addMember(invitor);

		if (invited.getTeam() != null)
			invited.getTeam().removeMember(invited);

		if (ConfigEntry.TEAMREQUEST_MAXTEAMMEMBERS.getValueAsInt() <= team.getMember().size()) {
			invitor.sendMessage(Main.getPrefix()
					+ ConfigMessages.TEAMREQUEST_TEAM_FULL.getValue().replace("%invited%", invited.getName()));
			return;
		}

		team.addMember(invited);
	}

	@SuppressWarnings("deprecation")
	private void startSched() {
		if (!ConfigEntry.TEAMREQUEST_EXPIRETIME.isIntActivated())
			return;

		this.sched = Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (VaroPlayer.getPlayer(invitor.getPlayer()) != null)
					invitor.sendMessage(Main.getPrefix() + "§7Deine Einladung an " + Main.getColorCode()
							+ invited.getName() + " §7ist abgelaufen!");

				if (VaroPlayer.getPlayer(invited.getPlayer()) != null)
					invited.sendMessage(Main.getPrefix() + "§7Die Einladung von " + Main.getColorCode()
							+ invitor.getName() + " §7ist abgelaufen!");

				remove();
			}

		}, 20 * ConfigEntry.TEAMREQUEST_EXPIRETIME.getValueAsInt());
	}

	public void accept() {
		if (!invitor.isOnline()) {
			invited.sendMessage(Main.getPrefix()
					+ ConfigMessages.TEAMREQUEST_PLAYER_NOT_ONLINE.getValue().replace("%invitor%", invitor.getName()));
			remove();
			return;
		}

		if (invitor.getTeam() == null)
			sendChatHook();
		else
			addToTeam(invitor.getTeam());
		remove();
	}

	public void revoke() {
		invitor.sendMessage(Main.getPrefix() + ConfigMessages.TEAMREQUEST_REVOKED.getValue());
		remove();
	}

	public void decline() {
		remove();
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(sched);
		requests.remove(this);
	}

	public static ArrayList<TeamRequest> getAllRequests() {
		return requests;
	}

	public static TeamRequest getByInvitor(VaroPlayer invitor) {
		for (TeamRequest req : requests) {
			if (req.getInvitor().equals(invitor))
				return req;
		}
		return null;
	}

	public static TeamRequest getByInvited(VaroPlayer invited) {
		for (TeamRequest req : requests)
			if (req.getInvitor().equals(invited))
				return req;

		return null;
	}

	public static TeamRequest getByAll(VaroPlayer inviter, VaroPlayer invited) {
		for (TeamRequest req : requests)
			if (req.getInvitor().equals(inviter) && req.getInvited().equals(invited))
				return req;

		return null;
	}
}
