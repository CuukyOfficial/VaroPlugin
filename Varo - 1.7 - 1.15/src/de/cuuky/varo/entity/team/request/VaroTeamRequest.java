package de.cuuky.varo.entity.team.request;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class VaroTeamRequest {

	private static ArrayList<VaroTeamRequest> requests;

	static {
		requests = new ArrayList<>();
	}

	private VaroPlayer invited, invitor;
	private int sched;

	public VaroTeamRequest(VaroPlayer invitor, VaroPlayer invited) {
		this.invitor = invitor;
		this.invited = invited;

		requests.add(this);

		startSched();
	}

	private void addToTeam(VaroTeam team) {
		if (!team.isMember(invitor))
			team.addMember(invitor);

		if (invited.getTeam() != null)
			invited.getTeam().removeMember(invited);

		if (ConfigSetting.TEAMREQUEST_MAXTEAMMEMBERS.getValueAsInt() <= team.getMember().size()) {
			invitor.sendMessage(ConfigMessages.TEAMREQUEST_TEAM_FULL, invitor).replace("%invited%", invited.getName());
			return;
		}

		team.addMember(invited);
	}

	private void sendChatHook() {
		Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(invitor.getPlayer(), Main.getColorCode() + ConfigMessages.TEAMREQUEST_ENTER_TEAMNAME.getValue(invitor).replace("%invited%", invited.getName()), new ChatHookHandler() {

			@Override
			public boolean onChat(AsyncPlayerChatEvent event) {
				String message = event.getMessage();
				if (message.contains("#")) {
					invitor.sendMessage(ConfigMessages.TEAMREQUEST_NO_HASHTAG, invitor);
					return false;
				}

				if (message.contains("&") && !invitor.getPlayer().hasPermission("Varo.teamcolorcode")) {
					invitor.sendMessage(ConfigMessages.TEAMREQUEST_NO_COLORCODE, invitor);
					return false;
				}

				int maxLength = ConfigSetting.TEAMREQUEST_MAXTEAMNAMELENGTH.getValueAsInt();
				if (message.length() > (maxLength) - 1) {
					invitor.sendMessage(ConfigMessages.TEAMREQUEST_MAX_TEAMNAME_LENGTH, invitor).replace("%maxLength%", String.valueOf(maxLength));
					return false;
				}

				addToTeam(invitor.getTeam() == null ? new VaroTeam(message) : invitor.getTeam());
				return true;
			}
		}));
	}

	@SuppressWarnings("deprecation")
	private void startSched() {
		if (!ConfigSetting.TEAMREQUEST_EXPIRETIME.isIntActivated())
			return;

		this.sched = Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (!invited.isOnline())
					invitor.sendMessage(Main.getPrefix() + "§7Deine Einladung an " + Main.getColorCode() + invited.getName() + " §7ist abgelaufen!");

				if (!invitor.isOnline())
					invited.sendMessage(Main.getPrefix() + "§7Die Einladung von " + Main.getColorCode() + invitor.getName() + " §7ist abgelaufen!");

				remove();
			}

		}, 20 * ConfigSetting.TEAMREQUEST_EXPIRETIME.getValueAsInt());
	}

	public void accept() {
		if (!invitor.isOnline()) {
			invited.sendMessage(ConfigMessages.TEAMREQUEST_PLAYER_NOT_ONLINE, invited).replace("%invitor%", invitor.getName());
			remove();
			return;
		}

		if (invitor.getTeam() == null)
			sendChatHook();
		else
			addToTeam(invitor.getTeam());
		remove();
	}

	public void decline() {
		remove();
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(sched);
		requests.remove(this);
	}

	public void revoke() {
		invitor.sendMessage(ConfigMessages.TEAMREQUEST_REVOKED, invitor);
		remove();
	}

	public VaroPlayer getInvited() {
		return invited;
	}

	public VaroPlayer getInvitor() {
		return invitor;
	}

	public static ArrayList<VaroTeamRequest> getAllRequests() {
		return requests;
	}

	public static VaroTeamRequest getByAll(VaroPlayer inviter, VaroPlayer invited) {
		for (VaroTeamRequest req : requests)
			if (req.getInvitor().equals(inviter) && req.getInvited().equals(invited))
				return req;

		return null;
	}

	public static VaroTeamRequest getByInvited(VaroPlayer invited) {
		for (VaroTeamRequest req : requests)
			if (req.getInvitor().equals(invited))
				return req;

		return null;
	}

	public static VaroTeamRequest getByInvitor(VaroPlayer invitor) {
		for (VaroTeamRequest req : requests) {
			if (req.getInvitor().equals(invitor))
				return req;
		}
		return null;
	}
}
