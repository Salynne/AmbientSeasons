/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.util;

import me.olloth.lordned.seasons.Dates;

/**
 *
 * @author Matt
 */
public class Enums {

    static public enum Season {

        SPRING, SUMMER, FALL, WINTER
    }

    static public enum Day {

        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
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
        int season = Dates.SEASON;
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
            };
        }

        return string;
    }

    public static String getDate() {

        String date = getDayString(Dates.DAY_OF_WEEK) + " the "
                + Dates.DAY_OF_SEASON + getModString(Dates.DAY_OF_SEASON)
                + " of " + getSeasonString(Dates.SEASON)
                + ", " + Dates.YEAR + "AN";
        return date;
    }
}
