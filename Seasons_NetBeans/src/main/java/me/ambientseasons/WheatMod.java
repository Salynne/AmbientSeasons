/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * 
 * @author Matt
 */
public class WheatMod {
	private AmbientSeasons plugin;

	// -1 = slow, 0 = normal, 1 = fast
	public int WheatGrowthSpeed;

	public WheatMod(AmbientSeasons plugin) {
		this.plugin = plugin;

		AmbientSeasons.log.info(AmbientSeasons.PREFIX + "WheatMod enabled!");

		// TODO:
		// Calculate the first growth time
		WheatGrowthSpeed = 10;

		// TODO:
		// Hook weather change, if it starts raining grow faster.
	}

	public void growWheat() {
		List<Location> removedLocations = new ArrayList<Location>();
		for (Location location : plugin.WheatBlockLocations) {
			AmbientSeasons.log.info(AmbientSeasons.PREFIX + "Wheat Mod is growing");
			Block block = plugin.getServer().getWorld("world").getBlockAt(location);

			byte dataValue = block.getData();

			if (dataValue == 0x7) {
				removedLocations.add(location);
				continue;
			} else {
				dataValue++;
				block.setData(dataValue);
			}
		}
		
		plugin.WheatBlockLocations.removeAll(removedLocations);

		// TODO:
		// Calculate the time for the next growth.
		// Modify by WheatGrowthSpeed
		// Take block's biome into account
		// ("Wheat grows best in a dry, mild climate. Too hot or too cold ruin the crop.")

		// TODO: Create a temporary list in here, and randomly
		// pick wheat
		// to add to it (and remove from original list). Then
		// schedule
		// second delayed runnable that grows those a bit later,
		// so it's not total global growth all at once.

	}

	public void updateSettings() {
		// Read the season off of the config
		AmbientSeasons.log.info("WheatMod is updating settings for the new season.");
	}

}
