/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent; 

/**
 *
 * @author Matt
 */
public class WheatGrow implements Runnable
{
    private BlockPlaceEvent event;
    private AmbientSeasons plugin;
    public WheatGrow(BlockPlaceEvent event, AmbientSeasons plugin)
    {
        this.event = event;
        this.plugin = plugin;
    }
    
    public void run()
    {
        AmbientSeasons.log.info(this.event.getBlock().getLocation().toString());
        Block block = this.event.getBlock();
        byte data = block.getData();
        
        //If the wheat is full grown, just return
        if (data == 0x7)
            return;
        
        //Needs to grow again
        data++;
        block.setData(data);
        
        //Don't re-run the scheduler if we just grew up fully.
        if (data <= 0x6)
        {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 10 * 20);
        }
        
    }
    
}