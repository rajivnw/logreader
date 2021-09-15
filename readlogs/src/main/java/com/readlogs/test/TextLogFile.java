package com.readlogs.test;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readlogs.data.LogsData;

public class TextLogFile extends LogsData {

	static final Logger logger = Logger.getLogger(TextLogFile.class);

	private final String extentionExpected = ".txt";

	public TextLogFile() {
		super(getLogFile());
	}

	@Override
	protected void validateFileExtentionCorrect() {

		String extentionActual = getFilePath().substring(getFilePath().lastIndexOf("."), getFilePath().length());
		String fileName = getFilePath().substring(0, getFilePath().lastIndexOf("."));

		if (extentionActual.equalsIgnoreCase(extentionExpected) && fileName.length() > 0) {
			logger.info("'" + extentionActual + "' extention is correct for  Log File " + getFilePath());
		} else {
			logger.info("'" + extentionActual + "' extention is not correct for  Log File " + getFilePath()
					+ ". Expected extention is '" + extentionExpected + "'. So exiting .....");
			System.exit(1);
		}

	}

	public Gson getJsonParser() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson;
	}

	/**
	 * Removed the hardcoded "logfile.txt" file name and uncomment
	 * System.getProperty("logfile") in order to get the file path from command line
	 * for example -Dlogfile="<log_file_path>"
	 * 
	 * @return logfile
	 */
	static String getLogFile() {
		//
		String logFile = "logfile.txt"; // System.getProperty("logfile");
		return logFile;

	}

}
