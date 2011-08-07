/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons.listener;

import me.ambientseasons.AmbientSeasons;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author Matt
 */
public class BlockPlaceListener extends BlockListener {
    private AmbientSeasons plugin;
    
    public BlockPlaceListener(AmbientSeasons plugin)
    {
        this.plugin = plugin;
        AmbientSeasons.log.info("Block Listener enabled.");
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
