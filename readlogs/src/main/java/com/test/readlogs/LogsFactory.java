package com.test.readlogs;

import java.time.Duration;
import java.time.Instant;

import org.apache.log4j.Logger;

public class LogsFactory {

	static final Logger logger = Logger.getLogger(LogsFactory.class);
	LogPojo pojo;

	public LogsFactory(LogPojo pojo) {
		this.pojo = pojo;
	}

	public void getLogDetailsAndInsertIntoDB() {

		LogPojo id = null;
		synchronized (this) {

			if (LogsList.getLogList().size() > 0) {
				System.err.println(LogsList.getLogList());
				id = LogsList.getLogList().stream().filter(e -> e.getId().equalsIgnoreCase(pojo.getId())).findFirst()
						.orElse(null);
			}

			if (id == null) {
				LogsList.addLogList(pojo);
				logger.info("logger pojo added to list. Log id " + pojo.getId());
			}

		}

		if (id != null) {

			logger.info("Log status '" + id.getStatus() + "' is already fetched for Log id " + pojo.getId());

			Instant startTime;
			Instant endTime;

			if (pojo.getStatus().equalsIgnoreCase("started")) {
				startTime = Instant.ofEpochMilli(pojo.getTimestamp());
				endTime = Instant.ofEpochMilli(id.getTimestamp());

			} else if (pojo.getStatus().equalsIgnoreCase("finished")) {
				startTime = Instant.ofEpochMilli(id.getTimestamp());
				endTime = Instant.ofEpochMilli(pojo.getTimestamp());

			} else {
				logger.error("Log status '" + pojo.getStatus() + "' is invalid only started and finished is allowed");
				return;
			}

			long duration = Duration.between(startTime, endTime).toMillis();

			LogWriter writer = new LogWriter(id, duration);
			writer.start();

		}

	}

}
