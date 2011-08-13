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
		config.setProperty("seasons", getSeasons());
		config.setProperty("season_urls",getSeasonURLs());
		config.setProperty("weekdays",getWeekdays());
		config.setProperty("enabled_worlds",getEnabledWorlds());
		getTimeCalc();
		getSeasonLength();
		getSeconds();
		getSecondsInDay();
		getCalendarWorld();
		getCalcType();

		config.save();
	}

	public static List<String> getSeasons() {
		ArrayList<String> seasons = new ArrayList<String>();
		seasons.add("Djilba");
		seasons.add("Kamba");
		seasons.add("Birak");
		seasons.add("Bunuru");
		seasons.add("Djeran");
		seasons.add("Makuru");
		return config.getStringList("seasons", seasons);
	}

	public static List<String> getSeasonURLs() {
		ArrayList<String> seasonURLs = new ArrayList<String>();
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djilba.zip");
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Kamba.zip");
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Birak.zip");
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Bunuru.zip");
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djeran.zip");
		seasonURLs.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Makuru.zip");
		return config.getStringList("season_urls", seasonURLs);
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

	public static String getCalendarWorld() {
		return config.getString("calendar_world", plugin.getServer().getWorlds().get(0).getName());
	}

	public static String getCalcType() {
		return config.getString("calc_type", "world");
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
