package com.readlogs.threads;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.readlogs.data.ILog;

public class LogFileReader extends Thread {

	static final Logger logger = Logger.getLogger(LogFileReader.class);

	ILog logData;

	public LogFileReader(ILog logData) {
		this.logData = logData;
	}

	int i = 0;

	@Override
	public void run() {

		logger.info("File Reader thread started (time in milliseconds) " + System.currentTimeMillis());
		logData.setStartTime();

		String filePath = logData.getFilePath();

		try (FileInputStream inputStream = new FileInputStream(filePath);
				Scanner sc = new Scanner(inputStream, "UTF-8");) {

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				logData.addLogLine(line);
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("FileNotFound Exception  at path :" + filePath + " so exiting ");
			System.exit(1);
		} catch (IOException e) {
			logger.error("File IO Exception at path :" + filePath + " so exiting ");
			System.exit(1);
		}

		logger.info("Total Log line found : " + i);
		logData.setLogLineReaderDone(true);

		logger.info("File Reader thread ended (time in milliseconds) " + System.currentTimeMillis());

	}

}
