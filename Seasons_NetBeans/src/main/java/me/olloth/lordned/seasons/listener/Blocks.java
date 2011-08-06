/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.listener;

import java.util.List;
import me.olloth.lordned.seasons.Seasons;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author Matt
 */
public class Blocks extends BlockListener {
    private Seasons plugin;
    
    public Blocks(Seasons plugin)
    {
        this.plugin = plugin;
        this.plugin.log.info("Block Listener enabled.");
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {       
        if (event.getBlock().getType() == Material.CROPS)
        {
            //Track the location of all wheat blocks
            plugin.WheatBlockLocations.add(event.getBlock().getLocation());
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.CROPS)
        {
            //Remove it from the list if it's on there.
            plugin.WheatBlockLocations.remove(event.getBlock().getLocation());
        }
    }
    
    
}
