package com.readlogs.data;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.readlogs.db.ConnectDatabase;
import com.readlogs.pojo.LogPojo;

public abstract class LogsData implements ILog {

	static final Logger logger = Logger.getLogger(LogsData.class);

	private Queue<String> logLines = new ConcurrentLinkedQueue<String>();

	private Queue<LogPojo> logPojos = new ConcurrentLinkedQueue<LogPojo>();
	private Queue<LogPojo> logPojosFinal = new ConcurrentLinkedQueue<LogPojo>();

	private boolean islogLineReaderDone = false;
	private boolean isParseraDone = false;

	private long startTime;
	private long endTime;

	private String filePath;

	public LogsData(String filePath) {
		this.filePath = filePath;
		sanatiycheckForFile();
	}

	public void sanatiycheckForFile() {
		validateFilePresent();
		isFileExtentionCorrect();
	}

	protected abstract void isFileExtentionCorrect();

	public String getFilePath() {
		return filePath;
	}

	public void validateFilePresent() {

		if (filePath != null && !filePath.isEmpty()) {
			logger.info("Log File path is : " + filePath);

		} else {
			logger.info("File path either null or empty so exiting ...." + filePath);
			System.exit(1);

		}

	}

	/**
	 * @return parse the log line and create object
	 */
	public LogPojo parseLogLineAndCreatePojo(String logLine) {
		try {
			JsonReader reader = new JsonReader(new StringReader(logLine));
			reader.setLenient(true);

			LogPojo pojo = getJsonParser().fromJson(reader, LogPojo.class);
			//logger.debug("Pojo is created for log line : " + logLine);
			return pojo;
		} catch (Exception e) {
			logger.error("Error while parsing JSON log using Gson API " + e.getMessage());
			return null;
		}

	}

	protected abstract Gson getJsonParser();

	@Override
	public void addLogLine(String logLine) {
		logLines.add(logLine);
	}

	@Override
	public String getLogLine() {
		return logLines.poll();
	}

	@Override
	public void addLogPojo(LogPojo logPojo) {
		if (logPojos.contains(logPojo)) {
			logPojos.remove(logPojo);
			logPojosFinal.add(logPojo);

		} else {
			// System.out.println(logPojo);
			logPojos.add(logPojo);
			// System.out.println(logPojos.size());
		}
	}

	@Override
	public LogPojo getLogPojo() {
		return logPojosFinal.poll();
	}

	@Override
	public Queue<String> getLogLines() {
		return logLines;
	}

	@Override
	public Queue<LogPojo> getLogPojos() {
		return logPojosFinal;
	}

	@Override
	public boolean isLogLineReaderDone() {
		return islogLineReaderDone;
	}

	@Override
	public void setLogLineReaderDone(boolean bol) {
		this.islogLineReaderDone = bol;

	}

	@Override
	public void setLogParserDone(boolean b) {
		this.isParseraDone = b;

	}

	@Override
	public boolean isLogParserDone() {
		return isParseraDone;
	}

	@Override
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();

	}

	@Override
	public void setEndtime() {
		this.endTime = System.currentTimeMillis();
	}

	@Override
	public String getTotalTimeTaken() {
		Instant start = Instant.ofEpochMilli(startTime);
		Instant end = Instant.ofEpochMilli(endTime);
		long duration = Duration.between(start, end).toMillis();

		int a = (int) (duration / 60000);
		int b = (int) (duration % 60000);
		String x = a + "." + b;
		x = String.format("%.2f", new BigDecimal(x));
		return x;
	}

	int dbCount = 0;

	public void DBIntoInsert() {
		System.out.println("Db insert thread started " + System.currentTimeMillis());

		while (!isLogParserDone() || !getLogPojos().isEmpty()) {

			LogPojo logPojo = getLogPojo();
			if (logPojo != null) {
				dbCount++;
				ConnectDatabase.insertIntoDB(logPojo);
			}

		}

		System.out.println("Log DB " + dbCount);
		System.out.println("Db insert thread ended " + System.currentTimeMillis());

		setEndtime();
		System.out.println("Total time " + getTotalTimeTaken());
	}

}
