package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import model.LogWithCount;

public class LogAccessRepeator extends LogAccessor {

	public void makeLogFile() {
		String latestFileName = logFileService.getLatestFileName();
		String fileNameInTraceFile = traceFileService.getFileNameFromTraceFile();
		int startPoint = traceFileService.getPointerFromTraceFile();
		if (!latestFileName.equals(fileNameInTraceFile)) { /* 정상 실행 */
			makeAllFile(latestFileName, 0);
		} else { /* 다시 실행 */
			makeAllFile(latestFileName, startPoint);
		}
	}

	public void makeAllFile(String fileName, int startPoint) {
		System.out.println(fileName + " 처리를 시작합니다.");
		RandomAccessFile access = null;
		try {
			String logLine;
			String beforeTime = null;
			ArrayList<LogWithCount> logWithCountList = new ArrayList<LogWithCount>();
			access = new RandomAccessFile("src/logfile/access/" + fileName, "r");
			while (true) {
				access.seek(startPoint);
				
				while ((logLine = access.readLine()) != null) { 
					System.out.println("입력된 로그 : "+logLine);
					String afterTime = parser.getDateFromOriginalLog(logLine);
					if (beforeTime == null) {
						beforeTime = afterTime;
					} else if (!beforeTime.equals(afterTime)) {
						makeMinuteFile(beforeTime, logWithCountList);
						if (!beforeTime.substring(0, 10).equals(afterTime.substring(0, 10))) {
							makeHourFile(beforeTime.substring(0, 10));
						}
						beforeTime = afterTime;
						logWithCountList = new ArrayList<LogWithCount>();
					}
					logWithCountList.add(parser.makeLogWithCount("1 " + parser.makeStringLog(logLine)));
					traceFileService.writeTraceFile(fileName, access.getFilePointer());
				}
				
				if(!logWithCountList.isEmpty() && !beforeTime.equals(logFileService.getCurrentTime())) {
					makeMinuteFile(beforeTime, logWithCountList);
					if(!beforeTime.substring(0, 10).equals(logFileService.getCurrentTime().substring(0, 10))) {						
						makeHourFile(beforeTime.substring(0, 10));	
					}
					logWithCountList = new ArrayList<LogWithCount>();
				}
				
				startPoint = (int) access.getFilePointer();
				
				String latestFileName = logFileService.getLatestFileName();
				if (!fileName.equals(latestFileName)) {
					makeMinuteFile(beforeTime, logWithCountList);
					makeHourFile(beforeTime.substring(0, 10));
					makeDayFile(beforeTime.substring(0, 8));
					makeAllFile(latestFileName, 0);
				}
				Thread.sleep(5000);
				System.out.println("입력된 로그가 없습니다.");
			}
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			try {
				if (access != null) access.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void makeMinuteFile(String minuteFileName, ArrayList<LogWithCount> logWithCountList) {
		super.makeMinuteFile(minuteFileName, logWithCountList);
	}

	@Override
	protected void makeHourFile(String hourFileName) {
		super.makeHourFile(hourFileName);
	}

	@Override
	protected void makeDayFile(String dayFileName) {
		super.makeDayFile(dayFileName);
	}
}