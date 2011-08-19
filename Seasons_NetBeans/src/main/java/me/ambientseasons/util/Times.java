/*
 * This file is part of AmbientSeasons (https://github.com/Olloth/AmbientSeasons).
 * 
 * AmbientSeasons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ambientseasons.util;

import me.ambientseasons.Calendar;

/**
 * 
 * @author Olloth
 */
public class Times {

	/**
	 * Gets the day of the week
	 * 
	 * @param time
	 *            - Full time since the world started in ticks
	 * @return Day of the week
	 */
	public static int getDayOfWeek(long time) {

		int day = (int) (getDays(time) % Config.getNumberOfWeekdays()) + 1;

		return day;
	}

	/**
	 * Gets the date in the season
	 * 
	 * @param time
	 *            - Full time since the world started in ticks
	 * @return Day of the season
	 */
	public static int getDayOfSeason(long time) {

		int days = (int) (getDays(time) % Config.getSeasonLength()) + 1;

		return days;
	}

	/**
	 * Gets the season
	 * 
	 * @param time
	 *            - Full time since the world started in ticks
	 * @return Season
	 */
	public static int getSeason(long time) {

		int season = (int) ((getDays(time) / Config.getSeasonLength()) % Config.getNumberOfSeasons()) + 1;

		return season;
	}

	/**
	 * Gets the year
	 * 
	 * @param time
	 *            - Full time since the world started in ticks
	 * @return Year
	 */
	public static int getYear(long time) {

		int year = (int) (getDays(time) / (Config.getSeasonLength() * Config.getNumberOfSeasons())) + 1;

		return year;
	}

	/**
	 * Days that have gone by in total
	 * 
	 * @param time
	 *            - Full time since the world started in ticks
	 * @return Number of days since the world started
	 */
	public static long getDays(long time) {
		long days;

		if (Config.getCalcType().toLowerCase().equals("world")) {
			days = time / 24000;
		} else {
			days = time / Config.getSecondsInDay();
		}

		return days;
	}

	/**
	 * Gets the String for the day of the week based on the Config file
	 * 
	 * @param day
	 *            - Day of the week number
	 * @return Name of the day of the week
	 */
	public static String getDayString(int day) {
		String string = "";
		for (int i = 0; i < day; i++) {
			if (i == (day - 1)) {
				string = (String) Config.getWeekdays().get(i);
			}
		}

		return string;
	}

	/**
	 * Gets the String for the name of the season based on the Config fil
	 * 
	 * @param season
	 *            - Season number
	 * @return Name of the season
	 */
	public static String getSeasonString(int season) {
		String string = "";
		for (int i = 0; i < season; i++) {
			if (i == (season - 1)) {
				string = (String) Config.getSeasons().get(i);
			}
		}

		return string;
	}

	/**
	 * Gets the URL for the specified season number
	 * 
	 * @return URL for the texture pack
	 */
	public static String getSeasonUrl() {
		int season = Calendar.SEASON;
		String string = "";
		for (int i = 0; i < season; i++) {
			if (i == (season - 1)) {
				string = Config.getSeasonURL(getSeasonString(Calendar.SEASON));
			}
		}

		return string;
	}

	/**
	 * Gets the modifier to add to the date, ie 1st, 2nd, 3rd, 4th
	 * 
	 * @param day
	 *            - Day of the season
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
	 * 
	 * @return Full date
	 */
	public static String getDate() {

		String date = Config.getDateMessage();
		date = date.replace("{WEEKDAY}", getDayString(Calendar.DAY_OF_WEEK));
		date = date.replace("{DATE}", Integer.toString(Calendar.DAY_OF_SEASON));
		date = date.replace("{MOD}", getModString(Calendar.DAY_OF_SEASON));
		date = date.replace("{SEASON}", getSeasonString(Calendar.SEASON));
		date = date.replace("{YEAR}", Integer.toString(Calendar.YEAR));
		return date;
	}
}
