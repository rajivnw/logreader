package com.test.readlogs;

import org.apache.log4j.Logger;

public class LogWriter extends Thread {

	static final Logger logger = Logger.getLogger(LogWriter.class);

	String eventId;
	long duration;

	String type;
	String host;

	public LogWriter(LogPojo id, long duration) {
		this.eventId = id.getId();
		this.duration = duration;
		this.type = id.getType();
		this.host = id.getHost();
	}

	@Override
	public void run() {

		ConnectDatabase.insertIntoDB(this);

	}

	public String getEventId() {
		return eventId;
	}

	public long getDuration() {
		return duration;
	}

	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	public String isLonger() {
		if (getDuration() > 4) {
			return "true";
		}
		return "false";

	}

}
