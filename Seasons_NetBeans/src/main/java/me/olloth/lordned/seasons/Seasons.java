package me.olloth.lordned.seasons;

import java.io.File;
import java.util.logging.Logger;
import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Enums.Season;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Seasons extends JavaPlugin {
    //Setup Bukkit's Logger
    Logger log = Logger.getLogger("Minecraft");
    String logPrefix = "[Seasons] ";
    
    private PluginDescriptionFile info;
    private PluginManager pm;
    private File directory, config;
    
    
    public void onDisable()
    {
        System.out.println("[" + info.getName() + "] is now disabled!");
    }

    public void onEnable()
    {        
        directory = getDataFolder();
        info = getDescription();
        pm = getServer().getPluginManager();
        
        //Load the Config
        Config.configSetup(directory, config);
        
        pm.registerEvent(Type.PIG_ZAP, null, Priority.Low, this);
        
        System.out.println("[" + info.getName() + "] version " + 
                info.getVersion() + " is now enabled!");

    }
    
    //getSeason() returns 1-4 based off of the time since the map was created.
    //1 = Spring, 2 = Summer, 3 = Fall, 4 = Winter
    public Season getSeason()
    {
        //Seasons are read from the config as minutes!
        long time = getServer().getWorld(Config.WORLD).getFullTime();
        
        long season = (time / 20) / 60; //Current full world time in minutes.
        switch ((int)((season % (4 * Config.SEASON_LENGTH)) / 4 + 1))
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
}
