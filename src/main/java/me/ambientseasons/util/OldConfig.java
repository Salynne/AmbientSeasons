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

package me.ambientseasons.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import me.ambientseasons.AmbientSeasons;

import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;
import org.getspout.spoutapi.block.SpoutWeather;

@SuppressWarnings("deprecation")
public class OldConfig {

	private File hudMap;
	private AmbientSeasons plugin;
	private File directory;
	private Configuration config;
	private File configFile;

	public OldConfig(AmbientSeasons plugin) {
		this.plugin = plugin;

		directory = plugin.getDataFolder();
		configFile = new File(directory, "config.yml");

		try {
			URL jarConfigURL = plugin.getClass().getResource("/config.yml");

			if (!directory.exists()) {
				directory.mkdir();
			}

			if (!configFile.exists()) {
				plugin.info("Copying new config file");
				FileUtils.copyURLToFile(jarConfigURL, configFile);
				config = new Configuration(configFile);
				config.load();
			}

			else {
				config = new Configuration(configFile);
				config.load();

				File jarConfigFile = new File(directory, "newconfig.yml");
				FileUtils.copyURLToFile(jarConfigURL, jarConfigFile);

				Configuration jarConfig = new Configuration(jarConfigFile);
				jarConfig.load();

				String oldVersion = config.getString("version", "0");
				String jarVersion = jarConfig.getString("version");

				if (!oldVersion.equals(jarVersion)) {

					plugin.info("Backing up old config file");
					File backupFile = new File(directory, oldVersion + "_backup_config.yml");
					FileUtils.copyFile(configFile, backupFile);

					plugin.info("Copying new config file");
					FileUtils.copyURLToFile(jarConfigURL, configFile);
				}

				jarConfigFile.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		loadMap();
	}

	public List<String> getWorlds() {
		return config.getKeys("worlds");
	}

	public List<String> getSeasons() {
		return config.getKeys("seasons");
	}

	public SpoutWeather getSeasonBiomeWeather(String season, String biome) {
		SpoutWeather weather = SpoutWeather.RESET;
		String weatherString = config.getString("seasons." + season + ".Biome_Weather." + biome, "RESET");
		weather = SpoutWeather.valueOf(weatherString);
		return weather;
	}

	public List<String> getMonths(World world) {
		return config.getKeys("worlds." + world.getName() + ".months");
	}

	public List<String> getWeekdays(World world) {
		return config.getStringList("worlds." + world.getName() + ".weekdays", null);
	}

	public String getSeason(String month, World world) {
		return config.getString("worlds." + world.getName() + ".months." + month + ".season");
	}

	public int getMonthLength(String month, World world) {
		return config.getInt("worlds." + world.getName() + ".months." + month + ".days", 30);
	}

	public String getSeasonURL(String season) {
		return config.getString("seasons." + season + ".URL", "http://www.retributiongames.com/quandary/files/Quandary_4.1_Djilba.zip");
	}

	public Boolean isWorldEnabled(World world) {
		return getWorlds().contains(world.getName());
	}

	public int getNumberOfMonths(World world) {
		return getMonths(world).size();
	}

	public int getNumberOfWeekdays(World world) {
		return getWeekdays(world).size();
	}

	public int getHUDY() {
		return config.getInt("HUD_Y", 10);
	}

	public int getHUDX() {
		return config.getInt("HUD_X", 0);
	}

	public String getDateMessage() {
		return config.getString("date_message", "{WEEKDAY} the {DATE}{MOD} of {MONTH} {YEAR}AN");
	}

	public String getShortDateMessage() {
		return config.getString("short_date_message", "{DATE}/{MONTH}/{YEAR}");
	}

	public int getFontSize() {
		return config.getInt("font_size", 30);
	}

	@SuppressWarnings("unchecked")
	public void loadMap() {
		hudMap = new File(directory, "settings.bin");
		if (!hudMap.exists()) {
			System.out.println("Making new settings file");
			saveMap();
		} else {
			plugin.setHUDEnable((HashMap<String, Boolean>) HMapSL.load(hudMap.getPath()));
		}
	}

	public void saveMap() {
		HMapSL.save(plugin.getHUDEnable(), hudMap.getPath());

	}

}
