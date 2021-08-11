package com.test.readlogs;

import java.io.StringReader;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

/**
 * @author rajiv this class is responsible for handling each line of log this
 *         will parse the log line and create POJO for that log line
 *
 */
public class LogHandeler extends Thread {

	static final Logger logger = Logger.getLogger(LogHandeler.class);

	String logLine;

	public LogHandeler(String logLine) {
		this.logLine = logLine;
	}

	/**
	 * Can have another thread to fetching and create POJO
	 * 
	 */
	@Override
	public void run() {
		fetchLogAndInsertIntoDB();
	}

	/**
	 * Create POJO for each line of log and pass that POJO to Logs Factory
	 */
	public void fetchLogAndInsertIntoDB() {

		LogPojo pojo = parseLogLineAndCreatePojo();

		LogsFactory factory = new LogsFactory(pojo);

		factory.getLogDetailsAndInsertIntoDB();

	}

	/**
	 * @return parse the log line and create object
	 */
	public LogPojo parseLogLineAndCreatePojo() {
		try {
			JsonReader reader = new JsonReader(new StringReader(logLine));
			reader.setLenient(true);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			LogPojo pojo = gson.fromJson(reader, LogPojo.class);
			logger.debug("Pojo is created for log line : " + logLine);
			return pojo;
		} catch (Exception e) {
			logger.error("Error while parsing JSON log using Gson API " + e.getMessage());
			return null;
		}

	}

}
