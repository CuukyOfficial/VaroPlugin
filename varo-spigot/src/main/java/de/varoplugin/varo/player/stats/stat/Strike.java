package de.varoplugin.varo.player.stats.stat;

import java.util.Date;

import de.varoplugin.varo.config.VaroConfig;
import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Location;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.config.language.Contexts.StrikeContext;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import io.github.almightysatan.slams.Placeholder;

public class Strike implements VaroSerializeable {

	@VaroSerializeField(path = "strikedId")
	private int strikedId;

	@VaroSerializeField(path = "template")
	private StrikeTemplate template;

	@VaroSerializeField(path = "striker")
	private String striker;

	@VaroSerializeField(path = "acquired")
	private Date acquired;

	@VaroSerializeField(path = "banUntil")
	private Date banUntil;

	@VaroSerializeField(path = "posted")
	private boolean posted;

	@VaroSerializeField(path = "reason")
	private String reason;

	private VaroPlayer striked;

	public Strike() {}

	public Strike(StrikeTemplate template, String reason, VaroPlayer striked, String striker) {
		this.template = template;
		this.reason = reason;
		this.striker = striker;
		this.striked = striked;
		this.strikedId = striked.getId();
		this.acquired = new Date();
		this.posted = false;
	}

	public void activate() {
		if (this.template.isClearInventory()) {
			if (striked.isOnline())
				striked.getStats().clearInventory();
			else
				striked.getStats().setWillClear(true);
		}

		if (this.template.isEliminate()) {
			striked.getStats().setState(PlayerState.DEAD);
			if (striked.isOnline())
				Messages.PLAYER_KICK_TOO_MANY_STRIKES.kick(this.striked);
		} else if (this.template.getBanHours() > 0) {
			this.banUntil = DateUtils.addHours(new Date(), this.template.getBanHours());
			if (striked.isOnline())
				Messages.PLAYER_KICK_STRIKE_BAN.kick(this.striked, Placeholder.constant("ban-hours", String.valueOf(this.template.getBanHours())));
		}

		if (!VaroConfig.STRIKE_POST_AT_REST.getValue())
			post();
	}

	public void post() {
		StrikeContext ctx = new StrikeContext(this.striked, this.reason, this.striker, this.striked.getStats().getStrikes().size());
		if (this.template.isPostCoordinates()) {
			if (striked.getStats().getLastLocation() == null) {
				Location loc = Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation();
				Messages.LOG_STRIKE_COORDINATES_NEVER_ONLINE.log(LogType.STRIKE, ctx, Placeholder.constant("strike-location", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()));
			} else {
				Location loc = striked.isOnline() ? striked.getPlayer().getLocation() : striked.getStats().getLastLocation();
				Messages.LOG_STRIKE_COORDINATES.log(LogType.STRIKE, ctx, Placeholder.constant("strike-location", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()));
			}
		} else
			Messages.LOG_STRIKE_GENERAL.log(LogType.STRIKE, ctx);

		posted = true;
	}

	public StrikeTemplate getTemplate() {
		return template;
	}

	public Date getAcquiredDate() {
		return acquired;
	}

	public Date getBanUntil() {
		return banUntil;
	}

	public String getReason() {
		return reason;
	}

	public VaroPlayer getStriked() {
		return striked;
	}

	public String getStriker() {
		return striker;
	}

	public boolean isPosted() {
		return posted;
	}

	@Override
	public void onDeserializeEnd() {
		this.striked = VaroPlayer.getPlayer(strikedId);
	}

	@Override
	public void onSerializeStart() {}
}