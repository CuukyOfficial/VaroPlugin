package de.cuuky.varo.team;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.utils.VaroUtils;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;
import io.github.almightysatan.slams.Placeholder;

public class VaroTeamRequest {

	private static ArrayList<VaroTeamRequest> requests;

	static {
		requests = new ArrayList<>();
	}

	private VaroPlayer invited, inviter;
	private BukkitTask sched;

	public VaroTeamRequest(VaroPlayer invitor, VaroPlayer invited) {
		this.inviter = invitor;
		this.invited = invited;

		requests.add(this);

		startSched();
	}

	private void addToTeam(VaroTeam team) {
		if (!team.isMember(inviter))
			team.addMember(inviter);

		if (ConfigSetting.TEAMREQUEST_MAXTEAMMEMBERS.getValueAsInt() <= team.getMember().size()) {
			Messages.TEAMREQUEST_TEAM_FULL.send(this.inviter, Placeholder.constant("invited", this.invited.getName()));
			return;
		}
		
		if (invited.getTeam() != null)
			invited.getTeam().removeMember(invited);

		team.addMember(invited);
	}

	private void sendChatHook() {
		new PlayerChatHookBuilder().message(Messages.TEAMREQUEST_ENTER_TEAMNAME.value(this.inviter, Placeholder.constant("invited", this.invited.getName())))
        .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
            String message = hookEvent.getMessage();
            if (!message.matches(VaroTeam.NAME_REGEX)) {
                Messages.TEAMREQUEST_NAME_INVALID.send(this.inviter);
                return;
            }
            
            VaroTeam duplicateTeam = VaroTeam.getTeam(message);
            if (duplicateTeam != null) {
                Messages.TEAMREQUEST_NAME_DUPLICATE.send(this.inviter);
                return;
            }

            int maxLength = ConfigSetting.TEAM_MAX_NAME_LENGTH.getValueAsInt();
            if (message.length() > maxLength) {
                Messages.TEAMREQUEST_NAME_TOO_LONG.send(this.inviter, Placeholder.constant("max-length", String.valueOf(maxLength)));
                return;
            }

            hookEvent.getHook().unregister();
            addToTeam(inviter.getTeam() == null ? new VaroTeam(message) : inviter.getTeam());
        }).complete(inviter.getPlayer(), Main.getInstance());
	}

	private void startSched() {
		if (!ConfigSetting.TEAMREQUEST_EXPIRETIME.isIntActivated())
			return;

		this.sched = new BukkitRunnable() {
			@Override
			public void run() {
				if (!invited.isOnline())
					inviter.sendMessage(Main.getPrefix() + "§7Deine Einladung an " + Main.getColorCode() + invited.getName() + " §7ist abgelaufen!");

				if (!inviter.isOnline())
					invited.sendMessage(Main.getPrefix() + "§7Die Einladung von " + Main.getColorCode() + inviter.getName() + " §7ist abgelaufen!");

				remove();
			}
		}.runTaskLater(Main.getInstance(), 20 * ConfigSetting.TEAMREQUEST_EXPIRETIME.getValueAsInt());
	}

	public void accept() {
		if (!inviter.isOnline()) {
			Messages.TEAMREQUEST_PLAYER_NOT_ONLINE.send(this.invited, Placeholder.constant("inviter", this.inviter.getName()));
			remove();
			return;
		}

		if (inviter.getTeam() == null) {
		    if (ConfigSetting.TEAMREQUEST_RANDOM_NAME.getValueAsBoolean())
		        this.addToTeam(new VaroTeam(VaroUtils.getRandomTeamName(Arrays.asList(this.inviter, this.invited))));
		    else
		        this.sendChatHook();
		} else
			this.addToTeam(inviter.getTeam());
		this.remove();
	}

	public void decline() {
		remove();
	}

	public void remove() {
		sched.cancel();
		requests.remove(this);
	}

	public void revoke() {
		Messages.TEAMREQUEST_REVOKED.send(this.inviter);
		remove();
	}

	public VaroPlayer getInvited() {
		return invited;
	}

	public VaroPlayer getInvitor() {
		return inviter;
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
