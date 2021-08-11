package com.test.readlogs;

import org.junit.Assert;
import org.junit.Test;

public class TestApp {

	@Test
	public void testPojoParser_Valid() {

		String log = "{\"id\":\"scba\",\"status\":\"started\",\"type\":\"application_log\",\"host\":\"1234\",\"timestamp\":1345865567}";
		LogHandeler handler = new LogHandeler(log);
		LogPojo pojo = handler.parseLogLineAndCreatePojo();
		Assert.assertTrue(pojo != null);
	}

	@Test
	public void testPojoParser_InValid() {

		String log = "{\"id\":\"scba\",\"status:\"started\",\"type\":\"application_log\",\"host\":\"1234\",\"timestamp\":1345865567}";
		LogHandeler handler = new LogHandeler(log);
		LogPojo pojo = handler.parseLogLineAndCreatePojo();
		Assert.assertTrue(pojo == null);
	}

	@Test
	public void testFileTypeTxt_valid() {
		LogReader.logFile = "log.txt";
		boolean bol = LogReader.isTxtFileValid();
		Assert.assertTrue(bol);
	}

	@Test
	public void testFileTypeTxt_Invalid() {
		LogReader.logFile = "log.taxt";
		boolean bol = LogReader.isTxtFileValid();
		Assert.assertFalse(bol);
	}

	@Test
	public void testFileName_Invalid() {
		LogReader.logFile = "a.taxt";
		boolean bol = LogReader.isTxtFileValid();
		Assert.assertFalse(bol);
	}

	@Test
	public void testFileName_valid() {
		LogReader.logFile = "a.txt";
		boolean bol = LogReader.isTxtFileValid();
		Assert.assertTrue(bol);
	}

	@Test
	public void testPojoListIfReturningExistingPojo() {
		String log1 = "{\"id\":\"scba\",\"status\":\"started\",\"type\":\"application_log\",\"host\":\"1234\",\"timestamp\":1345865567}";

		LogHandeler handler = new LogHandeler(log1);
		LogPojo pojo = handler.parseLogLineAndCreatePojo();

		LogsList.addLogList(pojo);

		String log2 = "{\"id\":\"scba\",\"status\":\"finished\",\"type\":\"application_log\",\"host\":\"1234\",\"timestamp\":1345865567}";

		LogHandeler handler2 = new LogHandeler(log2);
		LogPojo pojo2 = handler2.parseLogLineAndCreatePojo();

		LogPojo bol = LogsList.getPreviousPojoFromListIfSavedWithDiffStatus(pojo2);

		Assert.assertTrue(bol != null);
	}

}
