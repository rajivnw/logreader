package com.test.readlogs;

import java.util.ArrayList;
import java.util.List;

public class LogsList {

	private static List<LogPojo> list = new ArrayList<LogPojo>();

	public static void addLogList(LogPojo pojo) {
		if (pojo != null) {
			list.add(pojo);
		} else {
			System.out.println("pojo is null");
		}
	}

	public static List<LogPojo> getLogList() {

		return list;
	}

}
