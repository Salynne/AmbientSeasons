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
public class Enums
{
    static public enum Season
    {
        SPRING, SUMMER, FALL, WINTER
    }
    
    static public enum Day
    {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
    
    public static String getDayString(int day) {
        String string = "";
        switch(day){
            case 1: string = "Monday"; break;
            case 2: string = "Tuesday"; break;
            case 3: string = "Wednesday"; break;
            case 4: string = "Thursday"; break;
            case 5: string = "Friday"; break;
            case 6: string = "Saturday"; break;
            case 7: string = "Sunday"; break;
        };
        
        return string;
    }
    
        public static String getSeasonString(int season) {
        String string = "";
        switch(season){
            case 1: string = "Djilba"; break;
            case 2: string = "Kamba"; break;
            case 3: string = "Birak"; break;
            case 4: string = "Bunuru"; break;
            case 5: string = "Djeran"; break;
            case 6: string = "Makuru"; break;
        };
        
        return string;
    }
        
        public static String getModString(int day) {
        String string = "";
        if((day == 11) || (day == 12) || (day == 13)) {
            string = "th";
        }
        else {
            switch(day%10){
                case 1: string = "st"; break;
                case 2: string = "nd"; break;
                case 3: string = "rd"; break;
                default: string = "th"; break;
            };
        }
        
        return string;
    }
        
        public static String getDate() {
                        
            String date = "Time: " + Dates.TIME_OF_DAY + " " +
                    getDayString(Dates.DAY_OF_WEEK) + " the " +
                    Dates.DAY_OF_SEASON + getModString(Dates.DAY_OF_SEASON) + 
                    " of " + getSeasonString(Dates.SEASON) + 
                    ", " + Dates.YEAR + "AN";
            return date;
        }
    
}
