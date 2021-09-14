package com.readlogs.pojo;

import java.util.Objects;

public class LogPojo {

	private String id, status, type, host;
	private long timestamp;
	private long finishedTime;
	private long startTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		if (getStatus().contains("finished")) {
			this.finishedTime = timestamp;

		}

		if (getStatus().contains("started")) {
			this.startTime = timestamp;

		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogPojo other = (LogPojo) obj;

		if (Objects.equals(id, other.id)) {
			if (other.status.contains("started")) {
				startTime = other.timestamp;
				finishedTime = timestamp;
			} else if (other.status.contains("finished")) {
				finishedTime = other.timestamp;
				startTime = timestamp;
			} else {
				System.out.println("only started or finished");
			}
			return true;
		} else {
			return false;
		}

	}

	public long getDuration() {
		return (finishedTime - startTime);
	}

	public boolean isLonger() {
		return (finishedTime - startTime) > 4;
	}

	@Override
	public String toString() {
		return "LogPojo [id=" + id + ", status=" + status + ", type=" + type + ", host=" + host + ", timestamp="
				+ timestamp + ", finishedTime=" + finishedTime + ", startTime=" + startTime + ", duration="
				+ getDuration() + "]";
	}

}
