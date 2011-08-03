package me.olloth.lordned.seasons.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

public class Config {
	
	public static String WORLD;
	
	public static void load(Configuration config) {
		config.load();
		
		WORLD = config.getString("world");
		
	}
	
	public static Configuration newConfig(Configuration config) {

		config.setProperty("world","world");
		
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
