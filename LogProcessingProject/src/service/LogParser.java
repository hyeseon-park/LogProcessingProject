package service;

import model.Log;
import model.LogWithCount;

public class LogParser {
	public String makeStringLog(String originalLog) {
		String[] logPart = originalLog.split(" ");
		return logPart[0] + " " + logPart[2] + " " + logPart[5] + " " + logPart[6];
	}

	public LogWithCount makeLogWithCount(String logString) {
		String[] logStringPart = logString.split(" ");
		Log log = new Log(logStringPart[1], logStringPart[2], logStringPart[3], logStringPart[4]);
		LogWithCount logWithCount = new LogWithCount(Integer.parseInt(logStringPart[0]), log);
		return logWithCount;
	}

	public String getDateFromOriginalLog(String originalLog) {
		String[] originalLogPart = originalLog.split(" ");
		String datePart = originalLogPart[3].substring(1, 21);
		String day = datePart.substring(0, 2);
		String month = makeMonth(datePart.substring(3, 6));
		String year = datePart.substring(7, 11);
		String hour = datePart.substring(12, 14);
		String minute = datePart.substring(15, 17);
		return year + month + day + hour + minute;
	}

	public String makeMonth(String month) {
		if (month.equals("Jan")) {
			month = "01";
		} else if (month.equals("Feb")) {
			month = "02";
		} else if (month.equals("Mar")) {
			month = "03";
		} else if (month.equals("Apr")) {
			month = "04";
		} else if (month.equals("May")) {
			month = "05";
		} else if (month.equals("Jun")) {
			month = "06";
		} else if (month.equals("Jul")) {
			month = "07";
		} else if (month.equals("Aug")) {
			month = "08";
		} else if (month.equals("Sep")) {
			month = "09";
		} else if (month.equals("Oct")) {
			month = "10";
		} else if (month.equals("Nov")) {
			month = "11";
		} else if (month.equals("Dec")) {
			month = "12";
		}
		return month;
	}
}