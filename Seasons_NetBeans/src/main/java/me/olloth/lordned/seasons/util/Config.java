package me.olloth.lordned.seasons.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

public class Config {
    
        public static Configuration CONFIG;
	
	public static String WORLD;
        public static int SEASON_LENGTH;
        public static int SEASONS;
	
	public static void load(Configuration config) {
		config.load();
                CONFIG = config;
		
		//Let the world be known!
		//Now committing as LordNed?
		WORLD = config.getString("world");
                SEASON_LENGTH = config.getInt("season_length", 28);
                SEASONS = config.getInt("seasons", 6);
                if (SEASON_LENGTH <= 0)
                    SEASON_LENGTH = 1;
		
	}
	
	public static Configuration newConfig(Configuration config) {

		config.setProperty("world","world");
		config.setProperty("season_length", 28);
                config.setProperty("seasons", 6);
		config.save();

		System.out.println("New Config file made");
		return config;
	}
	
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
			Configuration config = newConfig(new Configuration(new File(directory, "config.yml")));

			// Load the configuration file
			load(config);
		}
		else {
			// Load the configuration file
			load(new Configuration(new File(directory, "config.yml")));
		}
	}

}
