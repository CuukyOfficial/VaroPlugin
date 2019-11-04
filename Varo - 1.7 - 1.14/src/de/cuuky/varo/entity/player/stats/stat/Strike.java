package de.cuuky.varo.entity.player.stats.stat;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Location;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class Strike implements VaroSerializeable {

	@VaroSerializeField(path = "reason")
	private String reason;
	@VaroSerializeField(path = "acquired")
	private Date acquired;
	@VaroSerializeField(path = "banUntil")
	private Date banUntil;
	@VaroSerializeField(path = "striker")
	private String striker;
	@VaroSerializeField(path = "strikedId")
	private int strikedId;
	@VaroSerializeField(path = "posted")
	private boolean posted;
	@VaroSerializeField(path = "number")
	private int number;

	private VaroPlayer striked;

	public Strike() {}
	
	@Override
	public void onDeserializeEnd() {
		this.striked = VaroPlayer.getPlayer(strikedId);
	}

	@Override
	public void onSerializeStart() {}

	public Strike(String reason, VaroPlayer striked, String striker) {
		this.reason = reason;
		this.striker = striker;
		this.striked = striked;
		this.strikedId = striked.getId();
		this.acquired = new Date();
		this.posted = false;
	}
	
	public void activate(int number) {
		this.number = number;

		if(ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && !ConfigEntry.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		switch(number) {
		case 1:
			break;
		case 2:
			striked.getStats().clearInventory();
			break;
		case 3:
			striked.getStats().setState(PlayerState.DEAD);
			if(striked.isOnline())
				striked.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());
			break;
		default:
			striked.getStats().setState(PlayerState.DEAD);
			if(striked.isOnline())
				striked.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());
			break;
		}

		if(!ConfigEntry.STRIKE_POST_RESET_HOUR.getValueAsBoolean())
			post();
	}

	public void post() {
		if(ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && ConfigEntry.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		switch(number) {
		case 1:
			Location loc = striked.isOnline() ? striked.getPlayer().getLocation() : striked.getStats().getLastLocation();
			Main.getLoggerMaster().getEventLogger().println(LogType.STRIKE, ConfigMessages.ALERT_FIRST_STRIKE.getValue().replace("%player%", striked.getName()).replace("%pos%", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()));
			break;
		case 2:
			Main.getLoggerMaster().getEventLogger().println(LogType.STRIKE, ConfigMessages.ALERT_SECOND_STRIKE.getValue().replace("%player%", striked.getName()));
			break;
		case 3:
			Main.getLoggerMaster().getEventLogger().println(LogType.STRIKE, ConfigMessages.ALERT_THRID_STRIKE.getValue().replace("%player%", striked.getName()));
			break;
		default:
			Main.getLoggerMaster().getEventLogger().println(LogType.STRIKE, striked.getName() + " hat nun den " + number + "ten Strike!");
			break;
		}

		posted = true;
	}

	public Date getAcquiredDate() {
		return acquired;
	}

	public String getReason() {
		return reason;
	}

	public String getStriker() {
		return striker;
	}

	public VaroPlayer getStriked() {
		return striked;
	}

	public Date getBanUntil() {
		return banUntil;
	}

	public boolean isPosted() {
		return posted;
	}
}