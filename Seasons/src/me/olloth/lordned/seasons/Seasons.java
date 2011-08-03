package me.olloth.lordned.seasons;

import java.io.File;

import me.olloth.lordned.seasons.util.Config;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Seasons extends JavaPlugin {

	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;

	public void onDisable() {

		System.out.println("[" + info.getName() + "] disabled.");

	}

	public void onEnable() {

		info = getDescription();
		pm = getServer().getPluginManager();
		directory = getDataFolder();

		Config.configSetup(directory, config);

		System.out.println("[" + info.getName() + "] version "
				+ info.getVersion() + " enabled.");

	}

}
