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

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutWeather;
import org.getspout.spoutapi.player.BiomeManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.ambientseasons.util.ASConfig;
import me.ambientseasons.util.Times;

public class Calendar {

	private AmbientSeasons plugin;
	private ASConfig config;
	private Map<String, Times> calendars;
	private BiomeManager bm;

	public Calendar(AmbientSeasons plugin) {
		this.plugin = plugin;
		config = plugin.getASConfig();
		calendars = new HashMap<String, Times>();
		bm = SpoutManager.getBiomeManager();
		for (String world : config.getWorlds()) {
			calendars.put(world, new Times(plugin, plugin.getServer().getWorld(world)));
		}
	}

	/**
	 * Runs every second, BE CAREFUL HERE.
	 */
	public void onSecond() {

		for (Times time : calendars.values()) {

			if (time.newDay()) {

				if (time.newSeason()) {
					updateTextures();

					for (Biome biome : Biome.values()) {
						SpoutWeather weather = config.getSeasonBiomeWeather(time.getSeasonString(), biome.toString());
						bm.setGlobalBiomeWeather(biome, weather);

					}
				}

				time.newMonth();
				time.newDayOfMonth();
				time.newDayOfWeek();
				time.newYear();

				sendNotifications();

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
		if (config.isWorldEnabled(player.getWorld()) && player.hasPermission("ambientseasons.forcetexture")) {
			SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
			sPlayer.setTexturePack(getTimes(sPlayer).getSeasonUrl());
		}
	}

	public void sendNotifications() {
		for (SpoutPlayer player : SpoutManager.getPlayerManager().getOnlinePlayers()) {
			if (player.hasPermission("ambientseasons.notify") && plugin.getASConfig().isWorldEnabled(player.getWorld())) {
				String date = getTimes(player).getShortDate();
				player.sendNotification("A new day!", date, Material.WATCH);
			}
		}
	}

	public Times getTimes(Player player) {
		return calendars.get(player.getWorld().getName());
	}

}
