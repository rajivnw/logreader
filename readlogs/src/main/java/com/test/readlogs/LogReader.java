package com.test.readlogs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class LogReader {

	static final Logger logger = Logger.getLogger(LogReader.class);

	public static void main(String[] args) {

		String logFile = System.getProperty("logfile");
		System.out.println("file name is " + logFile);
//		String logFile = "logfile.txt";
		if (logFile != null && !logFile.isEmpty()) {

			try (FileInputStream inputStream = new FileInputStream(logFile);
					Scanner sc = new Scanner(inputStream, "UTF-8");) {

				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					LogHandeler handler = new LogHandeler(line);
					handler.fetchLogAndCreatePojo();
					// handler.start();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("File not found " + logFile);

				logger.error(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());

			}

		}
	}

}
