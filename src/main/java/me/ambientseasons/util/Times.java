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

import org.bukkit.World;

import me.ambientseasons.AmbientSeasons;

/**
 * 
 * @author Olloth
 */
public class Times {

	AmbientSeasons plugin;
	Config config;
	World world;
	int daysInYear, monthsInYear;
	
	private String season, oldSeason;

	private int days, dayOfWeek, dayOfMonth, month, year;
	private int oldDays, oldDayOfWeek, oldDayOfMonth, oldMonth, oldYear;

	public Times(AmbientSeasons plugin, World world) {
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.world = world;
		daysInYear = getDaysInYear();
		monthsInYear = config.getMonths(world).size();

		days = oldDays = getTotalDays();
		dayOfWeek = oldDayOfWeek = getDayOfWeek();
		dayOfMonth = oldDayOfMonth = getDayOfMonth();
		month = oldMonth = getMonth();
		year = oldYear = getYear();
		season = oldSeason = config.getSeason(getMonthString(), world);
	}

	public int getDayOfWeek() {

		int day = oldDays % config.getNumberOfWeekdays(world) + 1;

		return day;
	}

	public int getDayOfMonth() {

		int days = oldDays % daysInYear - 1;

		for (String string : config.getMonths(world)) {
			int length = config.getMonthLength(string, world);
			if (days >= length) {
				days -= length;
			} else {
				break;
			}
		}
		return days + 1;
	}

	public int getMonth() {

		int month = 1;
		int days = oldDays % daysInYear - 1;

		for (String string : config.getMonths(world)) {
			int length = config.getMonthLength(string, world);
			if (days >= length) {
				days -= length;
				month++;
			} else {
				break;
			}
		}

		return month;
	}

	public int getYear() {

		int year = oldDays / daysInYear + 1;

		return year;
	}

	public int getDaysInYear() {

		int days = 0;
		for (String month : config.getMonths(world)) {
			days += config.getMonthLength(month, world);
		}
		return days;
	}

	public int getTotalDays() {
		long time = world.getFullTime();

		return (int) (time / 24000) + 1;
	}

	public int getTotalMonths() {
		int totalMonths = oldYear * monthsInYear + oldMonth;

		return totalMonths;
	}

	public String getDayString() {
		String string = "";
		for (int i = 0; i < dayOfWeek; i++) {
			if (i == (dayOfWeek - 1)) {
				string = (String) config.getWeekdays(world).get(i);
			}
		}

		return string;
	}

	public String getMonthString() {
		String string = "";
		for (int i = 0; i < month; i++) {
			if (i == (month - 1)) {
				string = (String) config.getMonths(world).get(i);
			}
		}

		return string;
	}

	public String getSeasonUrl() {
		String string = "";
		string = config.getSeasonURL(getSeasonString());

		return string;
	}
	
	public String getSeasonString() {
		return config.getSeason(getMonthString(), world);
	}

	public String getModString(int day) {
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

	public String getDate() {

		String date = config.getDateMessage();
		date = date.replace("{WEEKDAY}", getDayString());
		date = date.replace("{DATE}", Integer.toString(dayOfMonth));
		date = date.replace("{MOD}", getModString(dayOfMonth));
		date = date.replace("{MONTH}", getMonthString());
		date = date.replace("{YEAR}", Integer.toString(year));
		return date;
	}
	
	public String getShortDate() {
		String date = config.getShortDateMessage();
		date = date.replace("{DATE}", Integer.toString(dayOfMonth));
		date = date.replace("{MONTH}", Integer.toString(month));
		date = date.replace("{YEAR}", Integer.toString(year));
		return date;
	}

	public boolean newDay() {
		boolean newDay;
		days = getTotalDays();
		if (days != oldDays) {
			newDay = true;
			oldDays = days;
		} else {
			newDay = false;
		}

		return newDay;
	}

	public boolean newDayOfWeek() {
		boolean newDayOfWeek;
		dayOfWeek = getDayOfWeek();
		if (dayOfWeek != oldDayOfWeek) {
			newDayOfWeek = true;
			oldDayOfWeek = dayOfWeek;
		} else {
			newDayOfWeek = false;
		}

		return newDayOfWeek;
	}
	
	public boolean newDayOfMonth() {
		boolean newDayOfMonth;
		dayOfMonth = getDayOfMonth();
		if (dayOfMonth != oldDayOfMonth) {
			newDayOfMonth = true;
			oldDayOfMonth = dayOfMonth;
		} else {
			newDayOfMonth = false;
		}

		return newDayOfMonth;
	}
	
	public boolean newMonth() {
		boolean newMonth;
		month = getMonth();
		if (month != oldMonth) {
			newMonth = true;
			oldMonth = month;
		} else {
			newMonth = false;
		}
		
		return newMonth;
	}
	
	public boolean newYear() {
		boolean newYear;
		year = getYear();
		if (year != oldYear) {
			newYear = true;
			oldYear = year;
		} else {
			newYear = false;
		}
		
		return newYear;
	}
	
	public boolean newSeason() {
		boolean newSeason;
		season = getSeasonString();
		if (!season.equals(oldSeason)) {
			newSeason = true;
			oldSeason = season;
		} else {
			newSeason = false;
		}
		
		return newSeason;
	}
	public World getWorld() {
		return world;
	}

}
