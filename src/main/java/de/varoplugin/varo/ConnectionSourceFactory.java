package de.varoplugin.varo;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import de.varoplugin.varo.config.VaroConfig;

public final class ConnectionSourceFactory {

	private ConnectionSourceFactory() {
	}
	
	public static ConnectionSource newConnectionSource(VaroConfig config) throws SQLException {
		switch(config.db_type.getValue()) {
		case "h2": 
			return new JdbcPooledConnectionSource("jdbc:h2:./plugins/VaroPlugin/h2/stats");
		case "mysql":
			return new JdbcPooledConnectionSource("jdbc:mysql://" + config.db_mysql_url.getValue(), config.db_mysql_user.getValue(), config.db_mysql_password.getValue());
		default:
			throw new IllegalArgumentException("Unknown database type");
		}
	}
}
