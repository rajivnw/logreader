package com.readlogs.db;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.readlogs.pojo.LogPojo;

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

	public static void insertIntoDB(LogPojo logWriter) {

//		logger.info("Rows inserted successfully. with details :: " + logPojo.getId() + "," + logPojo.getDuration() + ","
//				+ logPojo.getHost() + "," + logPojo.getType() + "," + logPojo.isLonger());
	}

}
