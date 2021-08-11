package com.test.readlogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class LogsList {

	static final Logger logger = Logger.getLogger(LogsList.class);

	// this list contains logs till the next status not found. If found then after
	// calculation removed the item
	private static List<LogPojo> list = new ArrayList<LogPojo>();

	public static void addLogList(LogPojo pojo) {
		if (pojo != null) {
			list.add(pojo);
		}
	}

	public static List<LogPojo> getLogList() {

		return list;
	}

	/**
	 * @param previousPojo
	 * @author rajiv Remove the POJO when both status 'Started' and 'finished' found
	 */
	public static void remove(LogPojo previousPojo) {
		list.remove(previousPojo);

	}

	/**
	 * @return LogPojo
	 * @author rajiv Storing the POJO in a list till differ status POJO not found.
	 *         For example if first line have status 'started' then store in list
	 *         till the line not found with status 'finished for the same id. If its
	 *         found do the calculation and insert into DB.
	 * 
	 */
	public static LogPojo getPreviousPojoFromListIfSavedWithDiffStatus(LogPojo currentPojo) {

		LogPojo previousPojo = null;

		synchronized (LogsList.class) {

			if (LogsList.getLogList().size() > 0) {
				previousPojo = LogsList.getLogList().stream()
						.filter(e -> e.getId().equalsIgnoreCase(currentPojo.getId())).findFirst().orElse(null);
				return previousPojo;
			}

			if (previousPojo == null) {
				LogsList.addLogList(currentPojo);
				logger.info("logger pojo added to list. Log id " + currentPojo.getId());
			}

		}

		return previousPojo;

	}

}
