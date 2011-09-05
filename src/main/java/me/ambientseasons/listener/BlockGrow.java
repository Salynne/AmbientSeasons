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

		if (!AmbientSeasons.WHEAT_MOD)
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
