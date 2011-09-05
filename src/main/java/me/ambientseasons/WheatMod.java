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

package me.ambientseasons;

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

	public void CreateWheatGrowthScheduler(BlockPlaceEvent event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new WheatGrow(event, plugin), Random(10, 15) * 20);
	}

	public void updateSettings() {
		// Read the season off of the config
		AmbientSeasons.log.info("WheatMod is updating settings for the new season.");
	}

	private int Random(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

}