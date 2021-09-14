package com.readlogs.data;

import java.util.Queue;

import com.readlogs.pojo.LogPojo;

public interface ILog {

	boolean isLogParserDone();

	Queue<LogPojo> getLogPojos();

	LogPojo getLogPojo();

	String getTotalTimeTaken();

	String getFilePath();

	void addLogLine(String line);

	void setLogLineReaderDone(boolean b);

	boolean isLogLineReaderDone();

	Queue<String> getLogLines();

	String getLogLine();

	LogPojo parseLogLineAndCreatePojo(String logLine);

	void addLogPojo(LogPojo pojo);

	void setLogParserDone(boolean b);

	void setStartTime();

	void setEndtime();
}