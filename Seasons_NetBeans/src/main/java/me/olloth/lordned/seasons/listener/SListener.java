package me.olloth.lordned.seasons.listener;

import java.util.UUID;

import me.olloth.lordned.seasons.Seasons;
import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Times;

import org.bukkit.entity.Player;
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

	private Seasons plugin;
	long count;

	public static int DAY_OF_WEEK, DAY_OF_SEASON, SEASON, YEAR;
	public static long TIME_OF_DAY, FULL_TIME;

	private int dayOfWeek, season, year;

	public SListener(Seasons plugin) {
		count = 0;
		this.plugin = plugin;
	}

	@Override
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {

		if (!event.getPlayer().isSpoutCraftEnabled()) {
			event.getPlayer().sendMessage(
					"This server uses SpoutCraft for the Seasons plugin.");
			event.getPlayer().sendMessage(
					"Install SpoutCraft from http://goo.gl/UbjS1 to see it.");
		}

		event.getPlayer().setTexturePack(Times.getSeasonUrl());
	}

	@Override
	public void onServerTick(ServerTickEvent event) {
		count++;

		if ((count % 20) == 0) {
			onSecond();
		}
	}

	private void onSecond() {

		FULL_TIME = plugin.getServer().getWorld(Config.CALENDAR_WORLD)
				.getFullTime();
		TIME_OF_DAY = plugin.getServer().getWorld(Config.CALENDAR_WORLD)
				.getTime();

		DAY_OF_WEEK = Times.getDayOfWeek(FULL_TIME);
		DAY_OF_SEASON = Times.getDayOfSeason(FULL_TIME);
		SEASON = Times.getSeason(FULL_TIME);
		YEAR = Times.getYear(FULL_TIME);

		if (Seasons.RELOAD) {
			updateHud();
			Seasons.RELOAD = false;
		}

		if (DAY_OF_WEEK != dayOfWeek) {
			updateHud();
			// New day code here
		}

		if (SEASON != season) {
			updateTextures();
		}

		if (YEAR > year) {
			// New year code fires here;
			year = YEAR;
		}

		season = SEASON;
		dayOfWeek = DAY_OF_WEEK;

	}

	public void updateHud() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			SpoutPlayer sPlayer = (SpoutPlayer) player;
			UUID labelId = (UUID) Seasons.labels.get(player.getName());
			GenericLabel label = (GenericLabel) sPlayer.getMainScreen()
					.getWidget(labelId);
			label.setText(Times.getDate());
			label.setDirty(true);
		}
	}

	public void updateTextures() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			SpoutPlayer sPlayer = (SpoutPlayer) player;
			sPlayer.setTexturePack(Times.getSeasonUrl());
		}
	}

}
