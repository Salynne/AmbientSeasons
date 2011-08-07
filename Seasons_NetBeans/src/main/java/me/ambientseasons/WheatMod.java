/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 *
 * @author Matt
 */
public class WheatMod
{
    private AmbientSeasons plugin;
    
    //-1 = slow, 0 = normal, 1 = fast
    public int WheatGrowthSpeed;
    private int nextGrowthTime;
    
    public WheatMod(AmbientSeasons plugin)
    {
        this.plugin = plugin;
        
        plugin.log.info("[SEASONS] WheatMod enabled!");
        
        //TODO:
        //Calculate the first growth time
        nextGrowthTime = 25;
        GrowWheat();
        
        //TODO:
        //Hook weather change, if it starts raining grow faster.
    }
    
    public void GrowWheat()
    {
        //Set up a runnable to start growing
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            public void run() {
                for(int i = 0; i < plugin.WheatBlockLocations.size(); i++)
                {                    
                    Location location = plugin.WheatBlockLocations.get(i);
                    plugin.log.info("[SEASONS] Wheat Mod is growing: " + location.toString());
                    Block block = plugin.getServer().getWorld("world").getBlockAt(location);
                    
                    byte dataValue = block.getData();
                   
                    if (dataValue == 0x7)
                        continue;
                    else
                    {
                        dataValue++;
                        block.setData(dataValue);
                    }
                }
                
                //TODO:
                //Calculate the time for the next growth. 
                //Modify by WheatGrowthSpeed
                //Take block's biome into account ("Wheat grows best in a dry, mild climate. Too hot or too cold ruin the crop.")
                nextGrowthTime = 10;
                
                //TODO: Create a temporary list in here, and randomly pick wheat
                //to add to it (and remove from original list). Then schedule
                //second delayed runnable that grows those a bit later, so it's not total global growth all at once.
                
                //Schedule the next growth
                GrowWheat();
            }
        }, nextGrowthTime * 20);
    }
    
    public void UpdateSettings()
    {
        //Read the season off of the config
        plugin.log.info("WheatMod is updating settings for the new season.");
    }
    
}
