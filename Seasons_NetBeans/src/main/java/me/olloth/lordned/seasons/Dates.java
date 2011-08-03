/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons;

import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Enums;
import org.bukkit.Bukkit;

/**
 *
 * @author Matt
 */
public class Dates
{
    private final Seasons plugin;
    public static int DAY_OF_WEEK,
            DAY_OF_SEASON,
            SEASON,
            YEAR;
    
    public static long TIME_OF_DAY,
                        FULL_TIME;
    
    private int dayOfWeek,
            dayOfSeason,
            season,
            year;
    
    private long time;
    
    public Dates(Seasons plugin)
    {
        this.plugin = plugin;
    }
        
    public void SetupScheduledTasks()
    {
        long fullTime = Bukkit.getServer().getWorld(Config.WORLD).getFullTime();
        time = Bukkit.getServer().getWorld(Config.WORLD).getTime();
        dayOfWeek = Times.getDayOfWeek(fullTime);
        dayOfSeason = Times.getDayOfSeason(fullTime);
        season = Times.getSeason(fullTime);
        year = Times.getYear(fullTime);
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            public void run()
            {
                onSecond();
                
            }
        }, 0, 20);
    }
    
    private void onSecond() {
        
        FULL_TIME = Bukkit.getServer().getWorld(Config.WORLD).getFullTime();
        TIME_OF_DAY = Bukkit.getServer().getWorld(Config.WORLD).getTime();
        
        DAY_OF_WEEK = Times.getDayOfWeek(FULL_TIME);
        DAY_OF_SEASON = Times.getDayOfSeason(FULL_TIME);
        SEASON = Times.getSeason(FULL_TIME);
        YEAR = Times.getYear(FULL_TIME);
        
        if(DAY_OF_WEEK != dayOfWeek) {
            // New day code here
            System.out.println(Enums.getDate());
            System.out.println(Times.getDays(FULL_TIME));
        }
        
        if(DAY_OF_SEASON != dayOfSeason ) {
            // new season code fires here
        }
        
        if(SEASON > season) {
            // new season code fires as well
            season = SEASON;
        }
        
        if(YEAR > year) {
            // New year code fires here;
            year = YEAR;
        }
        
        time = TIME_OF_DAY;
        dayOfWeek = DAY_OF_WEEK;
        dayOfSeason = DAY_OF_SEASON;
    }
    
}
