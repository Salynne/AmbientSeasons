package me.olloth.lordned.seasons;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import me.olloth.lordned.seasons.listener.Players;
import me.olloth.lordned.seasons.util.Config;
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
    private Dates dates = new Dates(this);
    
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
        
        players.playersInit(getServer().getOnlinePlayers());
        
        System.out.println("[" + info.getName() + "] version " + 
                info.getVersion() + " is now enabled!");

        
        //Setup misc. Scheduled Tasks for this
        dates.SetupScheduledTasks();

    }
    
    public HashMap getLabels() {
        return labels;
    }
}
