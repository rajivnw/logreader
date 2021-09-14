package com.readlogs.test;

import org.apache.log4j.Logger;

import com.readlogs.data.ILog;
import com.readlogs.threads.LogDbInsertion;
import com.readlogs.threads.LogFileReader;
import com.readlogs.threads.LogParser;

public class AppTest {

	static final Logger logger = Logger.getLogger(AppTest.class);

	public static void main(String[] args) {
		//String logFile = System.getProperty("logfile");

		 String logFile = "logfile.txt";
		ILog logData = new TextLogFile(logFile);

		LogFileReader reader = new LogFileReader(logData);
		LogParser parser = new LogParser(logData);
		LogDbInsertion dbInsert = new LogDbInsertion(logData);

		reader.start();
		parser.start();
		dbInsert.start();

	}

}
