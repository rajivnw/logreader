package com.readlogs.test;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readlogs.data.LogsData;

public class TextLogFile extends LogsData {

	static final Logger logger = Logger.getLogger(TextLogFile.class);

	private final String extentionExpected = ".txt";

	public TextLogFile(String filePath) {
		super(filePath);
	}

	@Override
	protected void isFileExtentionCorrect() {

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

}
