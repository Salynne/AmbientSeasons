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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.ambientseasons.util.Config;
import me.ambientseasons.util.Times;

public class Calendar {

	private AmbientSeasons plugin;
	private Config config;
	private Map<String, Times> calendars;
	long count;

	@SuppressWarnings("unused")
	private int dayOfWeek, dayOfMonth, month, year, season;
	@SuppressWarnings("unused")
	private int oldDayOfWeek, oldDayOfMonth, oldMonth, oldYear, oldSeason;

	public Calendar(AmbientSeasons plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
		calendars = new HashMap<String, Times>();
		for (String world : config.getWorlds()) {
			calendars.put(world, new Times(plugin, plugin.getServer().getWorld(world)));
		}
	}

	/**
	 * Runs every second, BE CAREFUL HERE.
	 */
	public void onSecond() {

		for (Times time : calendars.values()) {
			dayOfWeek = time.getDayOfWeek();
			dayOfMonth = time.getDayOfMonth();
			month = time.getMonth();
			year = time.getYear();

			if (dayOfMonth != oldDayOfMonth || month != oldMonth || year != oldYear) {
				dayOfMonth = oldDayOfMonth;
				year = oldYear;
			}

			if (month != oldMonth) {
				updateTextures();

				if (AmbientSeasons.WHEAT_MOD) {
					plugin.wheatMod.updateSettings();
				}

				month = oldMonth;
			}
		}

	}

	/**
	 * Updates the texture pack for every player currently online.
	 */
	public void updateTextures() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			updateTexture(player);
		}
	}

	public void updateTexture(Player player) {
		if (config.isWorldEnabled(player.getWorld()) && !player.hasPermission("ambientseasons.exempt")) {
			SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
			sPlayer.setTexturePack(calendars.get(player.getWorld().getName()).getSeasonUrl());
		}
	}

	public Times getTimes(Player player) {
		return calendars.get(player.getWorld().getName());
	}

}
