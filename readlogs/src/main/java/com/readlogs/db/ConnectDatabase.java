package com.readlogs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class ConnectDatabase {

	static final Logger logger = Logger.getLogger(ConnectDatabase.class);

	static Statement stmt = null;
	static Connection con = null;

	// update with your details its just a simple string
	static String connectionString = "jdbc:hsqldb:hsql://localhost/testdb\", \"SA\", \"\"";

	// table name created with current timestamp
	static String tableName = "logs_details_" + new Timestamp(System.currentTimeMillis());
	static String[] columns = { "id", "duration", "host", "type", "isLonger" };

	static {

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");

			con = DriverManager.getConnection(connectionString);
			if (con != null) {
				logger.info("Connection created successfully");

			} else {
				logger.info("Problem with creating connection");
			}
			stmt = con.createStatement();
			createTable();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public static void createTable() {
		try {
			stmt.executeUpdate("CREATE TABLE " + tableName + " ( id VARCHAR(50) NOT NULL, duration int NOT NULL, "
					+ "host VARCHAR(100), type VARCHAR(100), isLonger VARCHAR(10), PRIMARY KEY (id))");

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		logger.info("Table created successfully");
	}

	public static int InsertIntoDB(String query) {

		query = query.replace("<table_name>", tableName);

		int result = 0;
		try {
			result = stmt.executeUpdate(query);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		if (result > 0) {
			logger.info(result + " rows effected. Query is " + query);
		} else {
			logger.info("Issue in insert, Rows NOT inserted with query :: " + query);
		}
		return result;

	}

}
