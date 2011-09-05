/*
 * This file is part of AmbientSeasons (https://github.com/Olloth/AmbientSeasons).
 * 
 * AmbientSeasons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

	public BlockPlaceListener(AmbientSeasons plugin) {
		this.plugin = plugin;
		if (AmbientSeasons.DEBUG)
			AmbientSeasons.log.info("Block Listener enabled.");
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.CROPS) {
			// What we do is register a Runnable to grow the crops.
			// This way we can accurately speed up (or slow down) crop growth
			// without growing them all at once.
			if (AmbientSeasons.DEBUG)
				AmbientSeasons.log.info("Biome: " + event.getBlock().getBiome().toString());

			plugin.wheatMod.CreateWheatGrowthScheduler(event);
		}
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.CROPS) {
			// Remove it from the list if it's on there.
			// plugin.WheatBlockLocations.remove(event.getBlock().getLocation());
		}
	}

}
