package model;

public class LogWithCount {
	private int count;
	private Log log;

	public LogWithCount(int count, Log log) {
		this.count = count;
		this.log = log;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return count + " " + log;
	}
}
