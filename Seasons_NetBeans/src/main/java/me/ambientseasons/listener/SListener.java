package me.ambientseasons.listener;

import java.util.UUID;

import me.ambientseasons.AmbientSeasons;
import me.ambientseasons.util.Config;
import me.ambientseasons.util.Times;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.ServerTickEvent;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * 
 * @author Olloth
 */
public class SListener extends SpoutListener {

	private AmbientSeasons plugin;
	long count;
	int seconds;

	public static int DAY_OF_WEEK, DAY_OF_SEASON, SEASON, YEAR;

	private int dayOfSeason, season, year;

	/**
	 * Constructor, sets the ticks counter to 0;
	 * 
	 * @param plugin
	 */
	public SListener(AmbientSeasons plugin) {
		seconds = Config.getSeconds();
		count = 0;
		this.plugin = plugin;
	}

	/**
	 * Runs when SpoutCraftEnables after a player joins.
	 */
	@Override
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {

		if (Config.isWorldEnabled(event.getPlayer().getWorld())) {
			event.getPlayer().setTexturePack(Times.getSeasonUrl());
		}
	}

	/**
	 * Runs every tick, BE CAREFUL HERE.
	 */
	@Override
	public void onServerTick(ServerTickEvent event) {

		if ((count % 20) == 0) {
			onSecond();
		}

		count++;
	}

	/**
	 * Runs every second, BE CAREFUL HERE.
	 */
	private void onSecond() {
		seconds++;

		if(Config.getCalcType().toLowerCase().equals("world")) {
			Config.setTimeCalc(plugin.getServer().getWorld(Config.getCalendarWorld()).getFullTime());
		}
		else {
			Config.setTimeCalc(seconds);
		}

		DAY_OF_WEEK = Times.getDayOfWeek(Config.getTimeCalc());
		DAY_OF_SEASON = Times.getDayOfSeason(Config.getTimeCalc());
		SEASON = Times.getSeason(Config.getTimeCalc());
		YEAR = Times.getYear(Config.getTimeCalc());

		if (DAY_OF_SEASON != dayOfSeason || SEASON != season || YEAR != year) {
			updateHud();
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
	 * Updates the HUD for every player currently online.
	 */
	public void updateHud() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
			UUID labelId = (UUID) AmbientSeasons.labels.get(player.getName());
			GenericLabel label = (GenericLabel) sPlayer.getMainScreen().getWidget(labelId);
			label.setText(Times.getDate());
			label.setDirty(true);
		}
	}

	/**
	 * Updates the texture pack for every player currently online.
	 */
	public void updateTextures() {

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
			
			if (Config.isWorldEnabled(player.getWorld())) {
				sPlayer.setTexturePack(Times.getSeasonUrl());
			}
		}
	}
	
	public int getSeconds() {
		return seconds;
	}

}
