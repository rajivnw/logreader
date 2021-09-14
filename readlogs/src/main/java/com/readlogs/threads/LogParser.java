package com.readlogs.threads;

import org.apache.log4j.Logger;

import com.readlogs.data.ILog;
import com.readlogs.pojo.LogPojo;

public class LogParser extends Thread {

	static final Logger logger = Logger.getLogger(LogParser.class);

	ILog logData;

	public LogParser(ILog logData) {
		this.logData = logData;
	}

	int i = 0;

	@Override
	public void run() {

		logger.info("Log parser thread started " + System.currentTimeMillis());

		while (!logData.isLogLineReaderDone() || !logData.getLogLines().isEmpty()) {
			String logLine = logData.getLogLine();

			if (logLine != null && !logLine.isEmpty()) {
				LogPojo pojo = logData.parseLogLineAndCreatePojo(logLine);
				if (pojo != null) {
					logData.addLogPojo(pojo);
					i++;
				}
			}
		}

		logData.setLogParserDone(true);
		logger.info("Log Line parser thread ended " + System.currentTimeMillis());
		logger.info("Total Log line parsed : " + i);

	}

}
