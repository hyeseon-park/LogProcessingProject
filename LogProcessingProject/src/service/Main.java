package service;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		LogAccessor logAccessor = new LogAccessor();
		logAccessor.makeLogFile();

		LogAccessRepeator logAccessRepeator = new LogAccessRepeator();
		logAccessRepeator.makeLogFile();
	}
}