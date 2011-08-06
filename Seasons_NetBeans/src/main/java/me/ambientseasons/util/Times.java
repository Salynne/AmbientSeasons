package me.ambientseasons.util;

import me.ambientseasons.listener.SListener;

/**
 * 
 * @author Olloth
 */
public class Times {

	/**
	 * Gets the day of the week
	 * @param fullTime - Full time since the world started in ticks
	 * @return Day of the week
	 */
	public static int getDayOfWeek(long fullTime) {

		int day = (int) (getDays(fullTime) % Config.WEEKDAY_COUNT) + 1;

		return day;
	}

	/**
	 * Gets the date in the season
	 * @param fullTime - Full time since the world started in ticks
	 * @return Day of the season
	 */
	public static int getDayOfSeason(long fullTime) {

		int days = (int) (getDays(fullTime) % Config.SEASON_LENGTH) + 1;

		return days;
	}

	/**
	 * Gets the season
	 * @param fullTime - Full time since the world started in ticks
	 * @return Season
	 */
	public static int getSeason(long fullTime) {

		int season = (int) ((getDays(fullTime) / Config.SEASON_LENGTH) % Config.SEASONS) + 1;

		return season;
	}

	/**
	 * Gets the year
	 * @param fullTime - Full time since the world started in ticks
	 * @return Year
	 */
	public static int getYear(long fullTime) {

		int year = (int) (getDays(fullTime) / (Config.SEASON_LENGTH * Config.SEASONS)) + 1;

		return year;
	}

	/**
	 * Days that have gone by in total
	 * @param fullTime - Full time since the world started in ticks
	 * @return Number of days since the world started
	 */
	public static long getDays(long fullTime) {
		long days = fullTime / 24000;
		return days;
	}

	/**
	 * Gets the String for the day of the week based on the Config file
	 * @param day - Day of the week number
	 * @return Name of the day of the week
	 */
	public static String getDayString(int day) {
		String string = "";
		for (int i = 0; i < day; i++) {
			if (i == (day - 1)) {
				string = (String) Config.WEEKDAYS.get(i);
			}
		}

		return string;
	}

	/**
	 * Gets the String for the name of the season based on the Config fil
	 * @param season - Season number
	 * @return Name of the season
	 */
	public static String getSeasonString(int season) {
		String string = "";
		for (int i = 0; i < season; i++) {
			if (i == (season - 1)) {
				string = (String) Config.SEASON_STRINGS.get(i);
			}
		}

		return string;
	}

	/**
	 * Gets the URL for the specified season number
	 * @return URL for the texture pack
	 */
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

	/**
	 * Gets the modifier to add to the date, ie 1st, 2nd, 3rd, 4th
	 * @param day - Day of the season
	 * @return Modifier
	 */
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

	/**
	 * Gets the full date as a string
	 * @return Full date
	 */
	public static String getDate() {

		String date = getDayString(SListener.DAY_OF_WEEK) + " the "
				+ SListener.DAY_OF_SEASON
				+ getModString(SListener.DAY_OF_SEASON) + " of "
				+ getSeasonString(SListener.SEASON) + ", " + SListener.YEAR
				+ "AN";
		return date;
	}
}
