package de.varoplugin.varo.bot.discord;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import de.varoplugin.varo.VaroPlugin;

public class VerifyManager implements Listener {

	private static final Random CODE_RANDOM = new SecureRandom();
	private static final char[] CODE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final int CODE_LENGTH = 8;
	private static final int CODE_DURATION_MINUTES = 10;

	private final VaroPlugin varo;
	private final DiscordBot bot;
	private final Collection<VerifyCode> codes = new ArrayList<>();

	private final Dao<AccountLink, UUID> dao;

	public VerifyManager(VaroPlugin varo, DiscordBot bot, ConnectionSource connectionSource) throws SQLException {
		this.varo = varo;
		this.bot = bot;
		TableUtils.createTableIfNotExists(connectionSource, AccountLink.class);
		this.dao = DaoManager.createDao(connectionSource, AccountLink.class);
	}

	public String genCode(UUID uuid) {
		this.codes.removeIf(code -> code.uuid.equals(uuid));
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < CODE_LENGTH; i++)
			stringBuilder.append(CODE_CHARS[CODE_RANDOM.nextInt(CODE_CHARS.length)]);
		VerifyCode code = new VerifyCode(System.currentTimeMillis(), uuid, stringBuilder.toString());
		synchronized (this) {
			this.codes.add(code);
		}
		return code.code;
	}

	public boolean tryVerify(long discordSnowflakeId, String code) throws SQLException {
		long minTimestamp = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(CODE_DURATION_MINUTES);
		UUID uuid;
		sync: synchronized (this) {
			Iterator<VerifyCode> iterator = this.codes.iterator();
			while (iterator.hasNext()) {
				VerifyCode verifyCode = iterator.next();
				if (verifyCode.timestamp < minTimestamp)
					iterator.remove();
				else if (verifyCode.code.equals(code)) {
					iterator.remove();
					uuid = verifyCode.uuid;
					break sync;
				}
			}
			return false;
		}
		// This should not be executed in the synchronized block
		this.verify(uuid, discordSnowflakeId);
		return true;
	}

	private void verify(UUID uuid, long discordSnowflakeId) throws SQLException {
		this.dao.createOrUpdate(new AccountLink(uuid, discordSnowflakeId));
	}

	public void unVerify(UUID uuid) throws SQLException {
		this.dao.deleteById(uuid);
	}

	public boolean isVerified(UUID uuid) throws SQLException {
		return this.getVerification(uuid) != null;
	}

	public AccountLink getVerification(UUID uuid) throws SQLException {
		return this.dao.queryForId(uuid);
	}

	public boolean isVerified(long discordSnowflakeId) throws SQLException {
		return this.getVerification(discordSnowflakeId) != null;
	}

	public AccountLink getVerification(long discordSnowflakeId) throws SQLException {
		List<AccountLink> list = this.dao.queryForEq("discordSnowflakeId", discordSnowflakeId);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@EventHandler
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		try {
			AccountLink verification = this.getVerification(event.getUniqueId());
			if (!this.varo.getVaroConfig().bot_discord_verify_optional.getValue() && verification == null) {
				String code = this.genCode(event.getUniqueId());
				event.setKickMessage(this.varo.getMessages().bot_discord_notverified.value(code));
				event.setLoginResult(Result.KICK_OTHER);
			}

			if (!this.varo.getVaroConfig().bot_discord_verify_checkmember.getValue() && this.bot.getGuild().retrieveMemberById(verification.getDiscordSnowflakeId()).complete() == null) {
				event.setKickMessage(this.varo.getMessages().bot_discord_notmember.value());
				event.setLoginResult(Result.KICK_OTHER);
			}
		} catch (SQLException e) {
			this.varo.getLogger().log(Level.SEVERE, "Unable to fetch discord verification", e);
			event.setKickMessage(e.getMessage());
			event.setLoginResult(Result.KICK_OTHER);
		}
	}

	@DatabaseTable(tableName = "AccountLink")
	public static class AccountLink {

		@DatabaseField(columnName = "playerUuid", canBeNull = false, id = true)
		private UUID playerUuid;
		@DatabaseField(columnName = "discordSnowflakeId", canBeNull = false, unique = true)
		private long discordSnowflakeId;

		AccountLink() {}

		AccountLink(UUID playerUuid, long discordSnowflakeId) {
			this.playerUuid = playerUuid;
			this.discordSnowflakeId = discordSnowflakeId;
		}

		public UUID getPlayerUuid() {
			return this.playerUuid;
		}

		public long getDiscordSnowflakeId() {
			return this.discordSnowflakeId;
		}
	}

	private static class VerifyCode {

		private final long timestamp;
		private final UUID uuid;
		private final String code;

		public VerifyCode(long timestamp, UUID uuid, String code) {
			this.timestamp = timestamp;
			this.uuid = uuid;
			this.code = code;
		}
	}
}
