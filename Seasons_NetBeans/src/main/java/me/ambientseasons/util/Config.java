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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.ambientseasons.AmbientSeasons;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class Config {
	private final static String QUANDARY = "http://www.retributiongames.com/quandary/files/Quandary_4.1_";

	// Hashmap file location
	private static File hudMap;

	private static Plugin plugin;
	private static File directory;
	private static File configFile;
	private static Configuration config;
	private static long timeCalc;

	public static void init(Plugin plgn) {
		plugin = plgn;
		directory = plugin.getDataFolder();
		configFile = new File(directory, "config.yml");
		if (!directory.exists())
			directory.mkdir();
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		load();

		loadMap();
	}

	public static void load() {
		config = new Configuration(configFile);
		config.load();

		getSeasons();
		config.save();
		config.load();
		config.setProperty("weekdays", getWeekdays());
		config.save();
		config.load();
		config.setProperty("enabled_worlds", getEnabledWorlds());
		config.save();
		config.load();
		getCalendarWorld();
		config.save();
		config.load();
		getSeasonLength();
		config.save();
		config.load();
		getSeconds();
		config.save();
		config.load();
		getSecondsInDay();
		config.save();
		config.load();
		getCalcType();
		config.save();
		config.load();
		getDateMessage();
		config.save();
		config.load();
		getHUDPosition();

		config.save();
	}

	public static List<String> getSeasons() {
		List<String> seasons = new ArrayList<String>();
		seasons.add("Djilba");
		seasons.add("Kamba");
		seasons.add("Birak");
		seasons.add("Bunuru");
		seasons.add("Djeran");
		seasons.add("Makuru");
		if (config.getKeys("seasons") == null) {
			for (String season : seasons) {
				config.setProperty("seasons." + season + ".URL", QUANDARY + season + ".zip");
				config.setProperty("seasons." + season + ".season_type", "Spring");
				config.save();
				config.load();
			}
		}

		return config.getKeys("seasons");
	}

	public static String getSeasonURL(String season) {
		String string = config.getString("seasons." + season + ".URL", QUANDARY + "Djilba.zip");
		System.out.println(string);

		return string;
	}

	public static List<String> getWeekdays() {
		ArrayList<String> weekdays = new ArrayList<String>();
		weekdays.add("Sunday");
		weekdays.add("Monday");
		weekdays.add("Tuesday");
		weekdays.add("Wednesday");
		weekdays.add("Thursday");
		weekdays.add("Friday");
		weekdays.add("Saturday");
		return config.getStringList("weekdays", weekdays);
	}

	private static List<String> getEnabledWorlds() {
		ArrayList<String> enabledWorlds = new ArrayList<String>();
		enabledWorlds.add("world");
		return config.getStringList("enabled_worlds", enabledWorlds);
	}

	public static Boolean isWorldEnabled(World world) {
		return getEnabledWorlds().contains(world.getName());
	}

	public static long getTimeCalc() {
		return timeCalc;
	}

	public static void setTimeCalc(long calc) {
		timeCalc = calc;
	}

	public static int getNumberOfSeasons() {
		return getSeasons().size();
	}

	public static int getNumberOfWeekdays() {
		return getWeekdays().size();
	}

	public static int getSeasonLength() {
		return config.getInt("season_length", 28);
	}

	public static int getSeconds() {
		return config.getInt("seconds", 1);
	}

	public static int getSecondsInDay() {
		return config.getInt("seconds_in_day", 60);
	}

	public static int getHUDPosition() {
		return config.getInt("HUD_Y", 10);
	}

	public static String getCalendarWorld() {
		return config.getString("calendar_world", plugin.getServer().getWorlds().get(0).getName());
	}

	public static String getCalcType() {
		return config.getString("calc_type", "world");
	}

	public static String getDateMessage() {
		return config.getString("date_message", "{WEEKDAY} the {DATE}{MOD} of {SEASON} {YEAR}AN");
	}

	public static Configuration getConfig() {
		return config;
	}

	public static void updateSeconds(int seconds) {
		config.load();
		config.setProperty("seconds", seconds);
		config.save();
	}

	@SuppressWarnings("unchecked")
	public static void loadMap() {
		hudMap = new File(directory, "settings.bin");
		if (!hudMap.exists()) {
			System.out.println("Making new settings file");
			saveMap();
		} else {
			AmbientSeasons.HUDEnable = (HashMap<String, Boolean>) HMapSL.load(hudMap.getPath());
		}
	}

	/**
	 * Save the hash map HUDEnable
	 */
	public static void saveMap() {
		HMapSL.save(AmbientSeasons.HUDEnable, hudMap.getPath());

	}

}
