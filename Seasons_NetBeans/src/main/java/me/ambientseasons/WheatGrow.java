/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ambientseasons;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 
 * @author Matt
 */
public class WheatGrow implements Runnable {
	private BlockPlaceEvent event;
	private AmbientSeasons plugin;

	public WheatGrow(BlockPlaceEvent event, AmbientSeasons plugin) {
		this.event = event;
		this.plugin = plugin;
	}

	public void run() {
		if (AmbientSeasons.DEBUG)
			AmbientSeasons.log.info(this.event.getBlock().getLocation().toString());

		Block block = this.event.getBlock();
		if (block.getType() != Material.CROPS)
			return;

		byte data = block.getData();

		// Incriment the growth amount
		data++;

		// If they're in the wrong biome, stunt growth randomly
		Biome biome = block.getBiome();
		if (biome == Biome.DESERT || biome == Biome.SAVANNA || biome == Biome.TUNDRA) {
			// If it's Desert, Savannah or Tundra it's just too damn hot/cold to
			// grow well.

			// Has a 66% chance of de-growing
			int chance = Random(0, 2);
			AmbientSeasons.log.info("Random Number: " + chance);
			if (chance != 0)
				data--;
		}

		if (biome == Biome.RAINFOREST || biome == Biome.SWAMPLAND || biome == Biome.SEASONAL_FOREST) {
			// If it's a Rain Forest, Swampland, or Seasonal Forest it's too wet
			// to really grow well.

			// Has a 33% chance of de-growing
			int chance = Random(0, 1);
			AmbientSeasons.log.info("Random Number 2:" + chance);
			if (chance == 0)
				data--;
		}

		// Now that we may have stunted it, actually 'grow' it.
		block.setData(data);

		// Don't re-run the scheduler if we just grew up fully.
		if (data <= 0x6) {
			// Calculate the next time...
			// TODO: Get this from WheatMod.java!
			int min = 10;
			int max = 15; // Seconds

			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, Random(min, max) * 20);
		}

	}

	private int Random(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

}