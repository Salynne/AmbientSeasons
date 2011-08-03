package me.olloth.lordned.seasons;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import me.olloth.lordned.seasons.listener.Players;
import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Enums.Day;
import me.olloth.lordned.seasons.util.Enums.Season;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Seasons extends JavaPlugin {
    //Setup Bukkit's Logger
    static final Logger log = Logger.getLogger("Minecraft");
    String logPrefix = "[Seasons] ";
    
    //Scheduled Task Stuff
    private SeasonsScheduledTasks scheduledTasks = new SeasonsScheduledTasks(this);
    
    private PluginDescriptionFile info;
    private PluginManager pm;
    private File directory, config;
    private Players players;
    
    HashMap labels;
    
    public void onDisable()
    {
        System.out.println("[" + info.getName() + "] is now disabled!");
    }

    public void onEnable()
    {        
        directory = getDataFolder();
        info = getDescription();
        pm = getServer().getPluginManager();
        players = new Players(this);
        
        labels = new HashMap();
        
        //Load the Config
        Config.configSetup(directory, config);
        
        pm.registerEvent(Type.PLAYER_JOIN, players, Priority.Low, this);
        
        System.out.println("[" + info.getName() + "] version " + 
                info.getVersion() + " is now enabled!");

        
        //Setup misc. Scheduled Tasks for this
        scheduledTasks.SetupScheduledTasks();

    }
    
    //getSeason() returns 1-4 based off of the time since the map was created.
    //1 = Spring, 2 = Summer, 3 = Fall, 4 = Winter
    public Season getSeason()
    {
        long fullTime = getServer().getWorld(Config.WORLD).getFullTime();
        
        //Seasons are read from the config as minutes!
        long numberOfDays = (fullTime / 24000); //24000 Ticks to a Minecraft-Day
        int season = (int) (((numberOfDays) / Config.SEASON_LENGTH) % 4) + 1;
        switch (season)
        {
            case 1:
                return Season.SPRING;
            case 2:
                return Season.SUMMER;
            case 3:
                return Season.FALL;
            case 4:
                return Season.WINTER;
            default:
                log.severe(logPrefix + "Calculated Invalid Season!");
                return Season.SPRING;
        }
    }
    
    //getDay() returns 1-7 based off of the time since the map was created.
    public Day getDay()
    {
        long fullTime = getServer().getWorld(Config.WORLD).getFullTime();
        
        //Seasons are read from the config as minutes!
        long numberOfDays = (fullTime / 24000); //24000 Ticks to a Minecraft-Day
        
        int day = (int) ((numberOfDays) % 7) + 1;
        switch(day)
        {
            case 1:
                return Day.SUNDAY;
            case 2:
                return Day.MONDAY;
            case 3:
                return Day.TUESDAY;
            case 4:
                return Day.WEDNESDAY;
            case 5:
                return Day.THURSDAY;
            case 6:
                return Day.FRIDAY;
            case 7:
                return Day.SATURDAY;
            default:
                log.severe(logPrefix + "Calculated Invalid Day of the Week!");
                return Day.SATURDAY;
        }
    }
    
    //getDayOfSeason, returns 0-???
    public int getDayOfSeason()
    {
       long fullTime = getServer().getWorld(Config.WORLD).getFullTime();
        
        //Seasons are read from the config as minutes!
        long numberOfDays = (fullTime / 24000); //24000 Ticks to a Minecraft-Day
        
        return (int) (numberOfDays % Config.SEASON_LENGTH);
    }
    
    //getYear() returns the number of years since the map was created.
    public int getYear()
    {
        long fullTime = getServer().getWorld(Config.WORLD).getFullTime();
        
        //Seasons are read from the config as minutes!
        long numberOfDays = (fullTime / 24000); //24000 Ticks to a Minecraft-Day
        
        //Return the number of years
        return (int) (numberOfDays / 360) + 1;
    }
    
    public HashMap getLabels() {
        return labels;
    }
}
