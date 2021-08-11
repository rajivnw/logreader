package com.test.readlogs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * @author rajiv This is the entry point of the app. txt file path will be
 *         provided from the command line
 *
 */
public class LogReader {

	static final Logger logger = Logger.getLogger(LogReader.class);

	static String logFile = "";

	public static void main(String[] args) {

		logFile = System.getProperty("logfile");

		logger.info("File name is " + logFile);

		if (logFile != null && !logFile.isEmpty() && isTxtFileValid()) {

			try (FileInputStream inputStream = new FileInputStream(logFile);
					Scanner sc = new Scanner(inputStream, "UTF-8");) {

				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					LogHandeler handler = new LogHandeler(line);
					handler.fetchLogAndInsertIntoDB();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("File not found " + logFile);

				logger.error(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());

			}
		} else {
			logger.info("File is not valid. Either null or empty or not having .txt extention " + logFile);
		}
	}

	public static boolean isTxtFileValid() {
		String extention = logFile.substring(logFile.lastIndexOf("."), logFile.length());
		String fileName = logFile.substring(0, logFile.lastIndexOf("."));

		if (extention.equalsIgnoreCase(".txt") && fileName.length() > 0) {
			return true;
		}
		return false;
	}
}
