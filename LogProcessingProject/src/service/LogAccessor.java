package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import model.LogWithCount;

public class LogAccessor {
	LogParser parser = new LogParser();
	LogFileService logFileService = new LogFileService();
	TraceFileService traceFileService = new TraceFileService();

	public void makeLogFile() {
		File traceFile = new File("src/logfile/trace/file.txt");
		File accessFolder = new File("src/logfile/access");
		String[] fileNameArray = accessFolder.list();
		int startPoint = 0;

		if (!traceFile.exists()) { /* 최초실행 */
			for (int i = 0; i < fileNameArray.length - 1; i++) {
				makeAllFile(fileNameArray[i], startPoint);
			}
		} else { /* 재실행 */
			String fileName = traceFileService.getFileNameFromTraceFile();
			startPoint = traceFileService.getPointerFromTraceFile();
			File fileAccessed = new File("src/logfile/access/" + fileName);
			if (startPoint != fileAccessed.length() && !fileName.equals(fileNameArray[fileNameArray.length - 1])) {
				makeAllFile(fileName, startPoint);
				for (int i = 0; i < accessFolder.list().length - 1; i++) {
					if (logFileService.modifyFileName(accessFolder.list()[i]) > logFileService.modifyFileName(fileName)) {
						makeAllFile(accessFolder.list()[i], 0);
					}
				}
			}
		}
	}

	protected void makeAllFile(String fileName, int startPoint) {
		System.out.println(fileName + " 처리를 시작합니다.");
		RandomAccessFile access = null;
		try {
			String logLine;
			String beforeTime = null;
			ArrayList<LogWithCount> logWithCountList = new ArrayList<LogWithCount>();
			access = new RandomAccessFile("src/logfile/access/" + fileName, "r");
			access.seek(startPoint);
			while ((logLine = access.readLine()) != null) {
				String afterTime = parser.getDateFromOriginalLog(logLine);
				if (beforeTime == null) {
					beforeTime = afterTime;
				} else if (!beforeTime.equals(afterTime)) {
					makeMinuteFile(beforeTime, logWithCountList);
					if (!beforeTime.substring(0, 10).equals(afterTime.substring(0, 10))) {
						makeHourFile(beforeTime.substring(0, 10));												/*시 단위 파일 생성*/
					}
					beforeTime = afterTime;
					logWithCountList = new ArrayList<LogWithCount>();
				}
				logWithCountList.add(parser.makeLogWithCount("1 " + parser.makeStringLog(logLine)));
				traceFileService.writeTraceFile(fileName, access.getFilePointer());
			}
			if (!logWithCountList.isEmpty()) {
				makeMinuteFile(beforeTime, logWithCountList);
				makeHourFile(beforeTime.substring(0, 10));														/*시 단위 파일 생성*/
			}
			makeDayFile(beforeTime.substring(0, 8));															/*일 단위 파일 생성*/
		} catch (FileNotFoundException e) {
			System.out.println(fileName+" 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(access != null) access.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(fileName + " 처리를 완료했습니다.");
	}
	protected void makeMinuteFile(String minuteFileName, ArrayList<LogWithCount> logWithCountList) {
		logFileService.writeToFile("minute", minuteFileName, 
				logFileService.count(logWithCountList));
		logFileService.deleteFile("minute", minuteFileName);
	}

	protected void makeHourFile(String hourFileName) {
		String[] fileNameArray = logFileService.getFileNameArray("minute", hourFileName); 
		logFileService.writeToFile("hour", hourFileName,
				logFileService.count(logFileService.readFromFiles("minute", fileNameArray)));
		logFileService.deleteFile("hour", hourFileName);
	}

	protected void makeDayFile(String dayFileName) {
		String[] fileNameArray = logFileService.getFileNameArray("hour", dayFileName);
		logFileService.writeToDayFile("day", dayFileName,
				logFileService.count(logFileService.readFromFiles("hour", fileNameArray)));
	}
}