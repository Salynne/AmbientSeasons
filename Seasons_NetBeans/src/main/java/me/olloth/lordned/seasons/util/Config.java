package me.olloth.lordned.seasons.util;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.config.Configuration;

public class Config {
    
        public static Configuration CONFIG;
	
	public static String WORLD;
        public static int SEASON_LENGTH;
        public static int SEASONS;
        public static List SEASON_STRINGS;
	
	public static void load(Configuration config) {
		config.load();
                CONFIG = config;
                SEASON_STRINGS = new ArrayList();
                
                int seasonsCount = 0;
		
		//Let the world be known!
		//Now committing as LordNed?
		WORLD = config.getString("world");
                SEASON_LENGTH = config.getInt("season_length", 28);
                for(Object string : config.getList("seasons")) {
                    SEASON_STRINGS.add(string);
                    seasonsCount ++;
                }
                SEASONS = seasonsCount;
                if (SEASON_LENGTH <= 0)
                    SEASON_LENGTH = 1;
		
	}
	
	public static Configuration newConfig(Configuration config) {
            
                List seasons = new ArrayList();
                
                // Default season config block
                seasons.add("Djilba");
                seasons.add("Kamba");
                seasons.add("Birak");
                seasons.add("Bunuru");
                seasons.add("Djeran");
                seasons.add("Makuru");
                
                config.setProperty("seasons", seasons);
                config.setProperty("season_length", 28);
                config.setProperty("world","world");
                
		config.save();

		System.out.println("Created default config.yml - See Seasons/config.yml to edit it");
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
