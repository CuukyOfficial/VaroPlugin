package de.cuuky.varo.bot.discord.register;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class BotRegister {

	/*
	 * OLD CODE
	 */

	private static ArrayList<BotRegister> register;

	static {
		register = new ArrayList<>();

		loadAll();
	}

	private String uuid;
	private long userId = -1;
	private int code = -1;
	private boolean bypass = false;
	private String name;

	public BotRegister(String uuid, boolean start) {
		this.uuid = uuid;

		if(start)
			if(code == -1)
				this.code = generateCode();

		register.add(this);
	}

	public int generateCode() {
		int code = new Random().nextInt(1000000) + 1;
		for(BotRegister br : register)
			if(!br.equals(this))
				if(br.getCode() == code)
					return generateCode();

		return code;
	}

	public void delete() {
		if(getPlayer() != null)
			getPlayer().kickPlayer("§cBotRegister §7unregistered");

		register.remove(this);
	}

	public int getCode() {
		return this.code;
	}

	public String getPlayerName() {
		return this.name;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public Member getMember() {
		try {
			JDA jda = BotLauncher.getDiscordBot().getJda();
			return jda.getGuildById(ConfigEntry.DISCORDBOT_SERVERID.getValueAsLong()).getMemberById(this.userId);
		} catch(Exception e) {
			return null;
		}
	}

	public boolean isBypass() {
		return bypass;
	}

	public boolean isActive() {
		if(bypass)
			return true;

		return userId > 0;
	}

	public void setUserId(long user) {
		this.userId = user;
	}

	public long getUserId() {
		return this.userId;
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getKickMessage() {
		return ConfigMessages.DISCORD_NOT_REGISTERED_DISCORD.getValue().replace("%code%", String.valueOf(getCode()));
	}

	public static ArrayList<BotRegister> getBotRegister() {
		return register;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}

	public static BotRegister getRegister(String uuid) {
		for(BotRegister br : register)
			if(br.getUUID().equals(uuid))
				return br;

		return null;
	}

	public static BotRegister getBotRegisterByPlayerName(String name) {
		for(BotRegister br : register)
			if(br.getPlayerName() != null)
				if(br.getPlayerName().equals(name))
					return br;
		return null;
	}

	public static BotRegister getRegister(User user) {
		for(BotRegister br : register)
			if(br.getUserId() == user.getIdLong())
				return br;
		return null;
	}

	public static void saveAll() {
		if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean())
			return;

		if(ConfigEntry.DISCORDBOT_USE_VERIFYSTSTEM_MYSQL.getValueAsBoolean()) {
			if(!MySQL.getInstance().isConnected())
				return;

			MySQL.getInstance().update("TRUNCATE TABLE verify;");

			for(final BotRegister reg : register) {
				MySQL.getInstance().update("INSERT INTO verify (uuid, userid, code, bypass, name) VALUES ('" + reg.getUUID() + "', " + (reg.getUserId() != -1 ? reg.getUserId() : "null") + ", " + reg.getCode() + ", " + reg.isBypass() + ", '" + (reg.getPlayerName() == null ? "null" : reg.getPlayerName()) + "');");
			}
		} else {
			File file = new File("plugins/Varo", "registrations.yml");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			for(String s : cfg.getKeys(true))
				cfg.set(s, null);

			for(final BotRegister reg : register) {
				cfg.set(reg.getUUID() + ".userId", reg.getUserId() != -1 ? reg.getUserId() : "null");
				cfg.set(reg.getUUID() + ".code", reg.getCode());
				cfg.set(reg.getUUID() + ".bypass", reg.isBypass());
				cfg.set(reg.getUUID() + ".name", reg.getPlayerName() == null ? "null" : reg.getPlayerName());
			}

			try {
				cfg.save(file);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void loadAll() {
		if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean())
			return;

		if(ConfigEntry.DISCORDBOT_USE_VERIFYSTSTEM_MYSQL.getValueAsBoolean()) {
			if(!MySQL.getInstance().isConnected()) {
				System.err.println(Main.getConsolePrefix() + "Failed to load BotRegister!");
				return;
			}

			ResultSet rs = MySQL.getInstance().getQuery("SELECT * FROM verify");

			try {
				while(rs.next()) {
					String uuid = rs.getString("uuid");
					BotRegister reg = new BotRegister(uuid, false);

					try {
						reg.setUserId(rs.getLong("userid"));
					} catch(Exception e) {
						reg.setUserId(-1);
					}

					reg.setCode(rs.getInt("code"));
					reg.setBypass(rs.getBoolean("bypass"));
					reg.setPlayerName(rs.getString("name"));

					if(Bukkit.getPlayer(UUID.fromString(uuid)) != null && !reg.isActive())
						Bukkit.getPlayer(UUID.fromString(uuid)).kickPlayer(reg.getKickMessage());
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}

		} else {
			File file = new File("plugins/Varo", "registrations.yml");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			for(String key : cfg.getKeys(true)) {
				if(!key.contains(".userId"))
					continue;

				String uuid = key.replace(".userId", "");
				BotRegister reg = new BotRegister(uuid, false);

				try {
					reg.setUserId(cfg.getLong(uuid + ".userId"));
				} catch(Exception e) {
					reg.setUserId(-1);
				}

				reg.setBypass(cfg.getBoolean(uuid + ".bypass"));
				reg.setCode(cfg.getInt(uuid + ".code"));
				reg.setPlayerName(cfg.getString(uuid + ".name"));

				if(Bukkit.getPlayer(UUID.fromString(uuid)) != null && !reg.isActive())
					Bukkit.getPlayer(UUID.fromString(uuid)).kickPlayer(reg.getKickMessage());
			}
		}
	}
}
