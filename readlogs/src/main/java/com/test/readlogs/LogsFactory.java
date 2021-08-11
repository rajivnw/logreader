package com.test.readlogs;

import java.time.Duration;
import java.time.Instant;

import org.apache.log4j.Logger;

/**
 * @author rajiv This class is responsible to fetch log details and inserted int
 *         DB This class will spawn a new thread for DB insertation
 */
public class LogsFactory {

	static final Logger logger = Logger.getLogger(LogsFactory.class);
	LogPojo pojo;

	public LogsFactory(LogPojo pojo) {
		this.pojo = pojo;
	}

	/**
	 * this method add the pojo in a List and before adding it check if same log Id
	 * POJO is added or not. For exmple log id '123' POJO with status 'started' is
	 * added to list and next pojo with status 'Finished' will not be added to list
	 * just there details will be fetched. Once the status 'Started and 'Finished'
	 * found, Pojo will be removed from the list to make list sorter.
	 * 
	 */
	public void getLogDetailsAndInsertIntoDB() {

		LogPojo id = null;

		synchronized (this) {

			if (LogsList.getLogList().size() > 0) {
				id = LogsList.getLogList().stream().filter(e -> e.getId().equalsIgnoreCase(pojo.getId())).findFirst()
						.orElse(null);
			}

			if (id == null) {
				LogsList.addLogList(pojo);
				logger.info("logger pojo added to list. Log id " + pojo.getId());
			}

		}

		// if not null then Same id POJO has been added in the list earlier
		if (id != null) {

			logger.info("Log status '" + id.getStatus() + "' is already fetched for Log id " + pojo.getId());

			Instant startTime;
			Instant endTime;

			// now check the details. Is this status Started or Finished.
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

			// both started and finished logs found so removing from list to keep list small
			// so search will be faster
			LogsList.getLogList().remove(id);

		}

	}

}
