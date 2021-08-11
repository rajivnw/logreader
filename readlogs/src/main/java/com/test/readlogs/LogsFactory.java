package com.test.readlogs;

import java.time.Duration;
import java.time.Instant;

import org.apache.log4j.Logger;

/**
 * @author rajiv This class is responsible to fetch log details and inserted
 *         into DB. This class will spawn a new thread for DB insertion
 */
public class LogsFactory {

	static final Logger logger = Logger.getLogger(LogsFactory.class);
	LogPojo currentPojo;

	public LogsFactory(LogPojo pojo) {
		this.currentPojo = pojo;
	}

	/**
	 * this method add the POJO in a List and before adding it check if same log Id
	 * POJO is added or not. For example log id '123' POJO with status 'started' is
	 * added to list and next POJO with status 'Finished' will not be added to list
	 * just there details will be fetched. Once the status 'Started and 'Finished'
	 * found, POJO will be removed from the list to make list sorter.
	 * 
	 */
	public void getLogDetailsAndInsertIntoDB() {

		LogPojo previousPojo = LogsList.getPreviousPojoFromListIfSavedWithDiffStatus(currentPojo);

		// if not null then Same id POJO has been added in the list earlier
		if (previousPojo != null) {

			Long duration = getDuration(previousPojo);

			if (duration != -1L) {
				LogWriter writer = new LogWriter(previousPojo, duration);

				// starting new thread to insert
				writer.start();
			}

			// both started and finished logs found so removing from list to keep list small
			// so search will be faster
			LogsList.remove(previousPojo);

		}

	}

	/**
	 * @param previousPojo
	 * @return duration between started and finished
	 * 
	 */
	public Long getDuration(LogPojo previousPojo) {
		logger.info(
				"Log status '" + previousPojo.getStatus() + "' is already fetched for Log id " + currentPojo.getId());

		Instant startTime;
		Instant endTime;

		// now check the details. Is this status Started or Finished.
		if (currentPojo.getStatus().equalsIgnoreCase("started")) {
			startTime = Instant.ofEpochMilli(currentPojo.getTimestamp());
			endTime = Instant.ofEpochMilli(previousPojo.getTimestamp());

		} else if (currentPojo.getStatus().equalsIgnoreCase("finished")) {
			startTime = Instant.ofEpochMilli(previousPojo.getTimestamp());
			endTime = Instant.ofEpochMilli(currentPojo.getTimestamp());

		} else {
			logger.error(
					"Log status '" + currentPojo.getStatus() + "' is invalid only started and finished is allowed");
			return -1L;
		}

		long duration = Duration.between(startTime, endTime).toMillis();
		return duration;
	}

}
