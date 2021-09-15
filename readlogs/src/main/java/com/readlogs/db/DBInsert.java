package com.readlogs.db;

import org.apache.log4j.Logger;

import com.readlogs.pojo.LogPojo;

public class DBInsert {

	static final Logger logger = Logger.getLogger(DBInsert.class);

	@SuppressWarnings("unused")
	public static void insertIntoDB(LogPojo logPojo) {

		String query = constructDbInsertQuery(logPojo);

		// If you have DB configuration ready please uncomment below line
		// And check the details in class ConnectDatabase

		// ConnectDatabase.InsertIntoDB(query);

	}

	private static String constructDbInsertQuery(LogPojo logPojo) {

		String values = logPojo.getId() + "," + logPojo.getDuration() + "," + logPojo.getHost() + ","
				+ logPojo.getType() + "," + logPojo.isLonger();

		String columns = "\"id\",\"duration\", \"host\",\"type\",\"isLonger\"";

		String query = "INSERT INTO <table_name> (" + columns + ") VALUES (" + values + ")";
		return query;

	}

}
