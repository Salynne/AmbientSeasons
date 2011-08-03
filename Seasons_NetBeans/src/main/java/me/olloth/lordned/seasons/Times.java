/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons;

import me.olloth.lordned.seasons.util.Config;

/**
 *
 * @author Olloth
 */
public class Times {
    
    public static int getDayOfWeek(long fullTime) {
        
        int day = (int) (getDays(fullTime) % 7) + 1;
        
        return day;
    }
    
    public static int getDayOfSeason(long fullTime) {
        
        int days = (int) (getDays(fullTime) % Config.SEASON_LENGTH) + 1;
        
        return days;
    }
    
    public static int getSeason(long fullTime) {
        
        int season = (int) ((getDays(fullTime) / Config.SEASON_LENGTH) 
                % Config.SEASONS) + 1;
     
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
}
