package me.olloth.lordned.seasons.util;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.olloth.lordned.seasons.Seasons;
import org.bukkit.util.config.Configuration;

public class Config {
	
	private static File hudMap;

	public static Configuration CONFIG;
	public static String CALENDAR_WORLD;
	public static int SEASON_LENGTH;
	public static int SEASONS;
	public static int WEEKDAY_COUNT;
	public static List<String> SEASON_STRINGS;
	public static List<String> SEASON_URLS;
	public static List<String> WEEKDAYS;
	public static List<String> ENABLED_WORLDS;

	public static void load(Configuration config) {
		config.load();
		CONFIG = config;
		SEASON_STRINGS = new ArrayList<String>();
		SEASON_URLS = new ArrayList<String>();
		WEEKDAYS = new ArrayList<String>();
		ENABLED_WORLDS = new ArrayList<String>();

		int seasonsCount = 0;
		int weekdayCount = 0;
		int urlCount = 0;

		for (Object string : config.getList("weekdays")) {
			WEEKDAYS.add((String) string);
			weekdayCount++;
		}

		for (Object string : config.getList("season_urls")) {
			SEASON_URLS.add((String) string);
			urlCount++;
		}

		for (Object string : config.getList("seasons")) {
			SEASON_STRINGS.add((String) string);
			seasonsCount++;
		}

		for (Object string : config.getList("enabled_worlds")) {
			if(string instanceof String) {
				ENABLED_WORLDS.add((String) string);
			}
		}

		if (urlCount != seasonsCount) {
			Seasons.log.log(Level.WARNING, "You don't have the same number "
					+ "of URLs as you do seasons");
		}

		SEASONS = seasonsCount;
		WEEKDAY_COUNT = weekdayCount;

		SEASON_LENGTH = config.getInt("season_length", 28);

		CALENDAR_WORLD = config.getString("calendar_world");

		if (SEASON_LENGTH <= 0) {
			SEASON_LENGTH = 1;
		}

	}

	public static Configuration newConfig(Configuration config) {

		List<String> seasons = new ArrayList<String>();

		// Default season config block
		seasons.add("Djilba");
		seasons.add("Kamba");
		seasons.add("Birak");
		seasons.add("Bunuru");
		seasons.add("Djeran");
		seasons.add("Makuru");

		List<String> seasonUrls = new ArrayList<String>();

		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djilba.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Kamba.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Birak.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Bunuru.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Djeran.zip");
		seasonUrls.add("http://www.retributiongames.com/quandary/files/Quandary_4.1_Makuru.zip");

		List<String> weekdays = new ArrayList<String>();

		// Default days of the week config block
		weekdays.add("Sunday");
		weekdays.add("Monday");
		weekdays.add("Tuesday");
		weekdays.add("Wednesday");
		weekdays.add("Thursday");
		weekdays.add("Friday");
		weekdays.add("Saturday");

		List<String> enabledWorlds = new ArrayList<String>();
		enabledWorlds.add("world");

		config.setProperty("weekdays", weekdays);
		config.setProperty("season_urls", seasonUrls);
		config.setProperty("seasons", seasons);
		config.setProperty("season_length", 28);
		config.setProperty("calendar_world", "world");
		config.setProperty("enabled_worlds", enabledWorlds);

		config.save();

		System.out
				.println("Created default config.yml - See Seasons/config.yml to edit it");
		return config;
	}


	@SuppressWarnings("unchecked")
	public static void configSetup(File directory, File configFile) {
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
			Configuration config = newConfig(new Configuration(new File(
					directory, "config.yml")));

			// Load the configuration file
			load(config);
		} else {
			// Load the configuration file
			load(new Configuration(new File(directory, "config.yml")));
		}
		
		hudMap = new File(directory, "settings.bin");
		if(!hudMap.exists()) {
			System.out.println("Making new settings file");
			saveMap();
		}
		else {
			Seasons.HUDEnable = (HashMap<String, Boolean>) HMapSL.load(hudMap.getPath());
		}
	}

	public static void saveMap() {
		HMapSL.save(Seasons.HUDEnable, hudMap.getPath());
		
	}
}
