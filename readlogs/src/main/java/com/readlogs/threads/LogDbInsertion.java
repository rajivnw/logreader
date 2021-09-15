package com.readlogs.threads;

import org.apache.log4j.Logger;

import com.readlogs.data.ILog;
import com.readlogs.db.DBInsert;
import com.readlogs.pojo.LogPojo;

public class LogDbInsertion extends Thread {

	static final Logger logger = Logger.getLogger(LogDbInsertion.class);

	ILog logData;

	public LogDbInsertion(ILog logData) {
		this.logData = logData;
	}

	int count;

	@Override
	public void run() {

		logger.info("Db insert thread started " + System.currentTimeMillis());

		while (!logData.isLogParserDone() || !logData.getLogPojos().isEmpty()) {

			LogPojo logPojo = logData.getLogPojo();
			if (logPojo != null) {
				count++;
				DBInsert.insertIntoDB(logPojo);
			}

		}

		logger.info("Db insert thread ended " + System.currentTimeMillis());
		logger.info("Total Records inserted into DB " + count);

		logData.setEndtime();
		logger.info("Total time taken in whole execution " + logData.getTotalTimeTaken());
	}

}
