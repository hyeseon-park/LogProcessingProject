package service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import model.LogWithCount;

public class LogFileService {
	LogParser parser = new LogParser();

	public ArrayList<LogWithCount> readFromFiles(String where, String[] fileNames) {
		ArrayList<LogWithCount> logWithCountList = new ArrayList<LogWithCount>();
		for (String fileName : fileNames) {
			Path path = Paths.get("src/logfile/" + where + "/" + fileName);
			Charset charset = Charset.forName("UTF-8");
			try {
				for (String logString : Files.readAllLines(path, charset)) {
					logWithCountList.add(parser.makeLogWithCount(logString));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logWithCountList;
	}

	public List<LogWithCount> count(List<LogWithCount> logWithCountList) {
		for (int i = 0; i < logWithCountList.size(); i++) {
			int count = logWithCountList.get(i).getCount();
			for (int j = i + 1; j < logWithCountList.size(); j++) {
				if (logWithCountList.get(i).getLog().equals(logWithCountList.get(j).getLog())) {
					count += logWithCountList.get(j).getCount();
					logWithCountList.remove(j);
					j--;
				}
			}
			logWithCountList.get(i).setCount(count);
		}
		return logWithCountList;
	}

	public void writeToDayFile(String where, String fileName, List<LogWithCount> logWithCountList) {
		Path path = Paths.get("src/logfile/" + where + "/stat_" + fileName.substring(0, 4) + "-" + fileName.substring(4, 6) + "-" + fileName.substring(6, 8) + ".txt");
		logWithCountList = sortFile(logWithCountList);
		for (LogWithCount l : logWithCountList) {
			try {
				Files.writeString(path, l.toString() + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeToFile(String where, String fileName, List<LogWithCount> logWithCountList) {
		Path path = Paths.get("src/logfile/" + where + "/" + fileName + ".txt");
		logWithCountList = sortFile(logWithCountList);
		for (LogWithCount l : logWithCountList) {
			try {
				Files.writeString(path, l.toString() + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<LogWithCount> sortFile(List<LogWithCount> logWithCountList) {
		logWithCountList.sort(new Comparator<LogWithCount>() {
			@Override
			public int compare(LogWithCount o1, LogWithCount o2) {
				int count1 = o1.getCount();
				int count2 = o2.getCount();
				if (count1 == count2)
					return 0;
				else if (count2 > count1)
					return 1;
				else
					return -1;
			}
		});
		return logWithCountList;
	}

	public void deleteFile(String where, String fileName) {
		try {
			File folder = new File("src/logfile/" + where);
			String[] fileNameArray = folder.list();
			if (where.equals("minute")) {
				fileName = getWantedTime(fileName, "yyyyMMddHHmm", -1);
				for (String TempFileName : fileNameArray) {
					if (Long.parseLong(TempFileName.substring(0, 12)) <= Long.parseLong(fileName)) {
						Path path = Paths.get("src/logfile/" + where + "/" + TempFileName);
						Files.deleteIfExists(path);
					}
				}
			} else if (where.equals("hour")) {
				fileName = getWantedTime(fileName, "yyyyMMddHH", -24);
				for (String TempFileName : fileNameArray) {
					if (Long.parseLong(TempFileName.substring(0, 10)) <= Long.parseLong(fileName)) {
						Path path = Paths.get("src/logfile/" + where + "/" + TempFileName);
						Files.deleteIfExists(path);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getFileNameArray(String fromWhere, String frontWordOfFileName) {
		File folder = new File("src/logfile/" + fromWhere);
		String[] fileNameArray = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(frontWordOfFileName);
			}
		});
		return fileNameArray;
	}
	
	public int modifyFileName(String fileName) {
		return Integer.parseInt(fileName.substring(7, 11) + fileName.substring(12, 14) + fileName.substring(15, 17));
	}
	
	public String getLatestFileName() {
		File accessFolder = new File("src/logfile/access");
		String[] fileNameList = accessFolder.list();
		return fileNameList[fileNameList.length - 1];
	}

	public String getWantedTime(String originalDate, String format, int addHour) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = dateFormat.parse(originalDate);
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, addHour);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormat.format(calendar.getTime());
	}

	public String getCurrentTime() {
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		return dateFormat.format(currentTime);
	}
}
