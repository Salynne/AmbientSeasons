package me.ambientseasons.listener;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.ambientseasons.AmbientSeasons;
import me.ambientseasons.util.Config;
import me.ambientseasons.util.Times;

public class Calendar {
	
	private AmbientSeasons plugin;
	long count;
	int seconds;

	public static int DAY_OF_WEEK, DAY_OF_SEASON, SEASON, YEAR;

	private int dayOfSeason, season, year;
	
	public Calendar(AmbientSeasons plugin) {
		this.plugin = plugin;
		seconds = Config.getSeconds();
	}
	
	/**
	 * Runs every second, BE CAREFUL HERE.
	 */
	public void onSecond() {
		seconds++;

		if (Config.getCalcType().toLowerCase().equals("world")) {
			Config.setTimeCalc(plugin.getServer().getWorld(Config.getCalendarWorld()).getFullTime());
		} else {
			Config.setTimeCalc(seconds);
		}

		DAY_OF_WEEK = Times.getDayOfWeek(Config.getTimeCalc());
		DAY_OF_SEASON = Times.getDayOfSeason(Config.getTimeCalc());
		SEASON = Times.getSeason(Config.getTimeCalc());
		YEAR = Times.getYear(Config.getTimeCalc());

		if (DAY_OF_SEASON != dayOfSeason || SEASON != season || YEAR != year) {
			dayOfSeason = DAY_OF_SEASON;
			year = YEAR;
		}

		if (SEASON != season) {
			updateTextures();

			if (AmbientSeasons.WHEAT_MOD) {
				plugin.wheatMod.updateSettings();
			}

			season = SEASON;
		}

	}

	/**
	 * Updates the texture pack for every player currently online.
	 */
	public void updateTextures() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (Config.isWorldEnabled(player.getWorld()) && !player.hasPermission("ambientseasons.exempt")) {
				SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
				sPlayer.setTexturePack(Times.getSeasonUrl());
			}
		}
	}

	/**
	 * Gets the seconds that have gone by since the plugin started.
	 * 
	 * @return seconds that have gone by since the plugin started
	 */
	public int getSeconds() {
		return seconds;
	}
}
