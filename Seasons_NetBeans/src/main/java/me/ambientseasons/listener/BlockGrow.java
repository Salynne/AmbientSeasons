/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons.listener;

import me.ambientseasons.AmbientSeasons;
import org.bukkit.Material;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * 
 * @author Matt
 */
public class BlockGrow extends BlockListener {
	private AmbientSeasons plugin;

	public BlockGrow(AmbientSeasons plugin) {
		this.plugin = plugin;
	}

	@Override
	// Prevent Crops from growing. We will manually grow them on our own later.
	public void onBlockPhysics(BlockPhysicsEvent event) {
            
            if (!plugin.WHEAT_MOD)
                return;
            
            if (event.getBlock().getType() == Material.CROPS) {
                    event.setCancelled(true);

                    // If the list doesn't already have this block, go ahead and add it.
                    if (!plugin.WheatBlockLocations.contains(event.getBlock().getLocation())) {
                            plugin.WheatBlockLocations.add(event.getBlock().getLocation());
                    }
            }
	}
}
