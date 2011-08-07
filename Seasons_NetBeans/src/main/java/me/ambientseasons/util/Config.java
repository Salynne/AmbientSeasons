package me.ambientseasons.util;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.ambientseasons.AmbientSeasons;

import org.bukkit.util.config.Configuration;

public class Config {

	// Hashmap file location
	private static File hudMap;

	// Static fields loaded from the configuration file
	public static Configuration CONFIG;
	public static String CALENDAR_WORLD;
	public static int SEASON_LENGTH;
	public static int SEASONS;
	public static int WEEKDAY_COUNT;
	public static List<Object> SEASON_STRINGS;
	public static List<Object> SEASON_URLS;
	public static List<Object> WEEKDAYS;
	public static List<Object> ENABLED_WORLDS;
	private static List<String> seasonStrings;
	private static List<String> seasonUrls;
	private static List<String> enabledWorlds;
	private static List<String> weekdays;

	/**
	 * Load the configuration file
	 * 
	 * @param config
	 *            - Configuration to load
	 */
	public static void load(Configuration config) {
		config.load();
		setDefaults();

		// Initialize the static fields
		CONFIG = config;
		SEASON_STRINGS = new ArrayList<Object>();
		SEASON_URLS = new ArrayList<Object>();
		WEEKDAYS = new ArrayList<Object>();
		ENABLED_WORLDS = new ArrayList<Object>();

		// Check if it's empty, if so set default, if not, load it
		if (config.getList("seasons") == null) {
			config.setProperty("seasons", seasonStrings);
			SEASON_STRINGS = config.getList("seasons");
		} else {
			SEASON_STRINGS = config.getList("seasons");
		}

		// Check if it's empty, if so set default, if not, load it
		if (config.getList("season_urls") == null) {
			config.setProperty("season_urls", seasonUrls);
			SEASON_URLS = config.getList("season_urls");
		} else {
			SEASON_URLS = config.getList("season_urls");
		}

		// Check if it's empty, if so set default, if not, load it
		if (config.getList("weekdays") == null) {
			config.setProperty("weekdays", weekdays);
			WEEKDAYS = config.getList("weekdays");
		} else {
			WEEKDAYS = config.getList("weekdays");
		}

		// Check if it's empty, if so set default, if not, load it
		if (config.getList("enabled_worlds") == null) {
			config.setProperty("enabled_worlds", enabledWorlds);
			ENABLED_WORLDS = config.getList("enabled_worlds");
		} else {
			ENABLED_WORLDS = config.getList("enabled_worlds");
		}

		// Check if it's empty, if so set default, if not, load it
		if (config.getString("season_length") == null) {
			config.setProperty("season_length", 28);
			SEASON_LENGTH = config.getInt("season_length", 28);
		} else {
			SEASON_LENGTH = config.getInt("season_length", 28);
		}

		// Check if it's empty, if so set default, if not, load it
		if (config.getString("calendar_world") == null) {
			config.setProperty("calendar_world", "world");
			CALENDAR_WORLD = config.getString("calendar_world");
		} else {
			CALENDAR_WORLD = config.getString("calendar_world");
		}

		SEASONS = config.getList("seasons").size();
		WEEKDAY_COUNT = config.getList("weekdays").size();

		config.save();

	}

	/**
	 * Set default values for the configuration file
	 */
	public static void setDefaults() {
		// Create new list of strings for seasons
		seasonStrings = new ArrayList<String>();

		// Create new list of strings for season urls
		seasonUrls = new ArrayList<String>();

		// Create new list of strings for the weekdays
		weekdays = new ArrayList<String>();

		// Create new list of strings for the enabled worlds
		enabledWorlds = new ArrayList<String>();

		// Add default values to the list of seasons
		seasonStrings.add("Djilba");
		seasonStrings.add("Kamba");
		seasonStrings.add("Birak");
		seasonStrings.add("Bunuru");
		seasonStrings.add("Djeran");
		seasonStrings.add("Makuru");

		// Add default values to the list of urls
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djilba.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Kamba.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Birak.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Bunuru.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djeran.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Makuru.zip");

		// Add default values to the weekday list
		weekdays.add("Sunday");
		weekdays.add("Monday");
		weekdays.add("Tuesday");
		weekdays.add("Wednesday");
		weekdays.add("Thursday");
		weekdays.add("Friday");
		weekdays.add("Saturday");

		// Add default values to the enabled worlds list
		enabledWorlds.add("world");
	}

	/**
	 * Setup and load the configuration file and hashmap
	 * 
	 * @param directory
	 * @param configFile
	 */
	@SuppressWarnings("unchecked")
	public static void configSetup(File directory) {
		File configFile;
		// Make the folder and configuration file if they don't exist.
		if (!directory.exists()) {
			directory.mkdirs();
		}

		configFile = new File(directory, "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Make the new configuration file
			Configuration config = new Configuration(new File(directory, "config.yml"));
			config.save();

			// Load the configuration file
			load(config);
		} else {
			// Load the configuration file
			load(new Configuration(new File(directory, "config.yml")));
		}

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
