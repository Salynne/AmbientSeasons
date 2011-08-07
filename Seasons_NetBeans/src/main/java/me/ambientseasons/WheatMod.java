/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 
 * @author Matt
 */
public class WheatMod {
	private AmbientSeasons plugin;

	public WheatMod(AmbientSeasons plugin) {
		this.plugin = plugin;

		AmbientSeasons.log.info(AmbientSeasons.PREFIX + "WheatMod enabled!");

		// TODO:
		// Hook weather change, if it starts raining grow faster.
	}
        
        public void CreateWheatGrowthScheduler(BlockPlaceEvent event)
        {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new WheatGrow(event, plugin), Random(10, 15) * 20);
        }

	public void updateSettings() {
		// Read the season off of the config
		AmbientSeasons.log.info("WheatMod is updating settings for the new season.");
	}
        
        private int Random(int Min, int Max)
        {
            return Min + (int)(Math.random() * ((Max - Min) + 1));
        }

}