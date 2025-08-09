package de.varoplugin.varo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class MySQLClient {

	private boolean connected;
	private Connection connection;
	private String host, database, user, password;

	public MySQLClient() {
		if (!ConfigSetting.DISCORDBOT_USE_VERIFYSTSTEM_MYSQL.getValueAsBoolean())
			return;

		this.host = ConfigSetting.DISCORDBOT_VERIFY_HOST.getValueAsString();
		this.database = ConfigSetting.DISCORDBOT_VERIFY_DATABASE.getValueAsString();
		this.user = ConfigSetting.DISCORDBOT_VERIFY_USER.getValueAsString();
		this.password = ConfigSetting.DISCORDBOT_VERIFY_PASSWORD.getValueAsString();
		this.connected = false;

		Main.getInstance().getLogger().log(Level.INFO, "Connecting to MySQL...");
		connect();

		if (connected)
			update("CREATE TABLE IF NOT EXISTS verify(uuid VARCHAR(255) NOT NULL, userid long, code int, bypass boolean, name VARCHAR(255) NOT NULL);");
	}

	public void close() {
		if (connection == null)
			return;

		try {
			connection.close();
		} catch (SQLException e) {}

		this.connected = false;
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", user, password);
			connected = true;
		} catch (SQLException e) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Invalid MYSQL host, user or password! Disabling MYSQL!");
			return;
		}
	}

	public ResultSet getQuery(String qry) {
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
			Main.getInstance().getLogger().log(Level.SEVERE, "Connection to MySQL-Database lost!");
			connect();
		}

		return rs;
	}

	public void update(String qry) {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Main.getInstance().getLogger().log(Level.SEVERE, "Connection to MySQL-Database lost!");
			connect();
		}
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException {
	    try {
	        return connection.prepareStatement(query);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Main.getInstance().getLogger().log(Level.SEVERE, "Connection to MySQL-Database lost!");
	        connect();
	        return connection.prepareStatement(query);
	    }
	}

	public boolean isConnected() {
		return connected;
	}
}