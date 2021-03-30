package de.cuuky.varo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

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

		System.out.println(Main.getConsolePrefix() + "Connecting to MySQL...");
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
			System.err.println(Main.getConsolePrefix() + "MYSQL USERNAME, IP ODER PASSWORT FALSCH! -> Disabled");
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
			System.err.println(Main.getConsolePrefix() + "Connection to MySQL-Database lost!");
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
			System.err.println(Main.getConsolePrefix() + "Connection to MySQL-Database lost!");
			connect();
		}
	}

	public boolean isConnected() {
		return connected;
	}
}