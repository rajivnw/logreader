package com.test.readlogs;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ConnectDatabase {

	static final Logger logger = Logger.getLogger(ConnectDatabase.class);

	static Statement stmt = null;
	static Connection con = null;

	static {

//		try {
//			// Registering the HSQLDB JDBC driver
//			Class.forName("org.hsqldb.jdbc.JDBCDriver");
//			// Creating the connection with HSQLDB
//			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
//			if (con != null) {
//				System.out.println("Connection created successfully");
//
//			} else {
//				System.out.println("Problem with creating connection");
//			}
//			stmt = con.createStatement();
		createTable();
//
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//		}
	}

	public static void createTable() {
//		try {
//			stmt.executeUpdate("CREATE TABLE logs_details ( id VARCHAR(50) NOT NULL, duration int NOT NULL, "
//					+ "host VARCHAR(100), type VARCHAR(100), isLonger VARCHAR(10), PRIMARY KEY (id))");
//
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//		}
		logger.info("Table created successfully");
	}

	public static void insertIntoDB(LogWriter logWriter) {

//		int result = 0;
//		try {
//			result = stmt.executeUpdate(
//					"INSERT INTO logs_details VALUES (" + logWriter.getEventId() + "," + logWriter.getDuration() + ","
//							+ logWriter.getHost() + "," + logWriter.getType() + "," + logWriter.isLonger() + ")");
//			con.commit();
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//		}
//		System.out.println(result + " rows effected");
		logger.info(
				"Rows inserted successfully. with details :: " + logWriter.getEventId() + "," + logWriter.getDuration()
						+ "," + logWriter.getHost() + "," + logWriter.getType() + "," + logWriter.isLonger());
	}

}
