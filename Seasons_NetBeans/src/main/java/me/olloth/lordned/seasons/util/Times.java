package me.olloth.lordned.seasons.util;

import me.olloth.lordned.seasons.listener.SListener;

/**
 * 
 * @author Olloth
 */
public class Times {

	public static int getDayOfWeek(long fullTime) {

		int day = (int) (getDays(fullTime) % Config.WEEKDAY_COUNT) + 1;

		return day;
	}

	public static int getDayOfSeason(long fullTime) {

		int days = (int) (getDays(fullTime) % Config.SEASON_LENGTH) + 1;

		return days;
	}

	public static int getSeason(long fullTime) {

		int season = (int) ((getDays(fullTime) / Config.SEASON_LENGTH) % Config.SEASONS) + 1;

		return season;
	}

	public static int getYear(long fullTime) {

		int year = (int) (getDays(fullTime) / (Config.SEASON_LENGTH * Config.SEASONS)) + 1;

		return year;
	}

	public static long getDays(long fullTime) {
		long days = fullTime / 24000;
		return days;
	}

	public static String getDayString(int day) {
		String string = "";
		for (int i = 0; i < day; i++) {
			if (i == (day - 1)) {
				string = (String) Config.WEEKDAYS.get(i);
			}
		}

		return string;
	}

	public static String getSeasonString(int season) {
		String string = "";
		for (int i = 0; i < season; i++) {
			if (i == (season - 1)) {
				string = (String) Config.SEASON_STRINGS.get(i);
			}
		}

		return string;
	}

	public static String getSeasonUrl() {
		int season = SListener.SEASON;
		String string = "";
		for (int i = 0; i < season; i++) {
			if (i == (season - 1)) {
				string = (String) Config.SEASON_URLS.get(i);
			}
		}

		return string;
	}

	public static String getModString(int day) {
		String string = "";
		if ((day == 11) || (day == 12) || (day == 13)) {
			string = "th";
		} else {
			switch (day % 10) {
			case 1:
				string = "st";
				break;
			case 2:
				string = "nd";
				break;
			case 3:
				string = "rd";
				break;
			default:
				string = "th";
				break;
			}
			;
		}

		return string;
	}

	public static String getDate() {

		String date = getDayString(SListener.DAY_OF_WEEK) + " the "
				+ SListener.DAY_OF_SEASON
				+ getModString(SListener.DAY_OF_SEASON) + " of "
				+ getSeasonString(SListener.SEASON) + ", " + SListener.YEAR
				+ "AN";
		return date;
	}
}
