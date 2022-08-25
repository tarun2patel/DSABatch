package com.ctfs.dsa.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.ctal.utility.app.context.ApplicationContext;
import com.ctal.utility.log.Log;

public class ConnectionUtil {

	private static Logger log = Logger.getLogger(ConnectionUtil.class.getName());

	static String dbUserName;
	static String dbPassword;
	static String connectionString;
	static String driverName;

	public static Connection getOracleDBConnection() throws Exception {
		dbUserName = ApplicationContext.getProperty("db_user_id");
		dbPassword = ApplicationContext.getProperty("db_password");
		connectionString = ApplicationContext.getProperty("db_connection_url");
		driverName = ApplicationContext.getProperty("db_driver_name");
		return getConnection();

	}

	public static Connection getWICIDBConnection() throws Exception {
		dbUserName = ApplicationContext.getProperty("db_wici_user_id");
		dbPassword = ApplicationContext.getProperty("db_wici_password");
		connectionString = ApplicationContext.getProperty("db_wici_connection_url");
		driverName = ApplicationContext.getProperty("db_wici_driver_name");
		return getConnection();

	}

	public static Connection getConnection() throws Exception {
		log.info("Entering insde the geInetDBConnection method");
		try {
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(connectionString, dbUserName, dbPassword);
			log.info("Exiting insde the geInetDBConnection method");
			return con;
		} catch (SQLException sqle) {
			Log.error("SQLException while opening connection to DB", sqle);
			throw sqle;
		} catch (ClassNotFoundException cnfe) {
			Log.error("Oracle driver class missing", cnfe);
			throw new Exception("Missing Driver Class");
		} catch (Exception e) {
			Log.error("Exception occurred while opening connection to DB", e);
			throw e;
		}
	}

}
