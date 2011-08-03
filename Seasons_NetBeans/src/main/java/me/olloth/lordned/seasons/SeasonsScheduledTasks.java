/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons;

import me.olloth.lordned.seasons.util.Config;
import org.bukkit.Bukkit;

/**
 *
 * @author Matt
 */
public class SeasonsScheduledTasks
{
    private final Seasons plugin;
    public SeasonsScheduledTasks(Seasons plugin)
    {
        this.plugin = plugin;
    }
        
    public void SetupScheduledTasks()
    {
        //Create the Dawn/Dusk Check
        //We'll re-use this code when Bukkit allows scheduledEvents to listen to time jumps.
        /*long currentTime = plugin.getServer().getWorld(Config.WORLD).getTime();
        int timeUntilNextCycle;
        
        if (currentTime > 12000)
            timeUntilNextCycle = (int)(24000 - currentTime);
        else
            timeUntilNextCycle = (int) (12000 - currentTime);
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            public void run()
            {
                plugin.log.info("[Seasons] The cycle has now changed.");
                plugin.log.info("[Seasons] Modulo is: " + (plugin.getServer().getWorld(Config.WORLD).getFullTime() % 24000));
            }
        }, timeUntilNextCycle, 12000);*/
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            public void run()
            {
                if (plugin.getDayOfSeason() == Config.SEASON_LENGTH)
                {
                    plugin.log.info("[Seasons] The month will change soon.");
                }
                //plugin.log.info("[Seasons] Modulo is: " + (plugin.getServer().getWorld(Config.WORLD).getFullTime() % 24000));
            }
        }, 0, 10);
    }
    
}
