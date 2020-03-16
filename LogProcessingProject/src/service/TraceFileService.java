package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TraceFileService {
	public void writeTraceFile(String fileName, long pointer) {
		try {
			Files.writeString(Paths.get("src/logfile/trace/file.txt"), fileName);
			Files.writeString(Paths.get("src/logfile/trace/pointer.txt"), Long.toString(pointer));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileNameFromTraceFile() {
		String fileName = "";
		try {
			fileName = Files.readString(Paths.get("src/logfile/trace/file.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public int getPointerFromTraceFile() {
		int pointer = 0;
		try {
			pointer = Integer.parseInt(Files.readString(Paths.get("src/logfile/trace/pointer.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pointer;
	}
}
