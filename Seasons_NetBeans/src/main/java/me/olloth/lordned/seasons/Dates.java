/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons;

import java.util.UUID;
import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Times;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author Matt
 */
public class Dates {

    private final Seasons plugin;
    public static int DAY_OF_WEEK,
            DAY_OF_SEASON,
            SEASON,
            YEAR;
    public static long TIME_OF_DAY,
            FULL_TIME;
    @SuppressWarnings("unused")
	private int dayOfWeek,
            dayOfSeason,
            season,
            year;
    @SuppressWarnings("unused")
	private long time;

    public Dates(Seasons plugin) {
        this.plugin = plugin;
    }

    public void SetupScheduledTasks() {
        long fullTime = Bukkit.getServer().getWorld(Config.CALENDAR_WORLD).getFullTime();
        time = Bukkit.getServer().getWorld(Config.CALENDAR_WORLD).getTime();
        dayOfWeek = Times.getDayOfWeek(fullTime);
        dayOfSeason = Times.getDayOfSeason(fullTime);
        season = Times.getSeason(fullTime);
        year = Times.getYear(fullTime);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            public void run() {
                onSecond();

            }
        }, 0, 20);
    }

    private void onSecond() {

        FULL_TIME = Bukkit.getServer().getWorld(Config.CALENDAR_WORLD).getFullTime();
        TIME_OF_DAY = Bukkit.getServer().getWorld(Config.CALENDAR_WORLD).getTime();

        DAY_OF_WEEK = Times.getDayOfWeek(FULL_TIME);
        DAY_OF_SEASON = Times.getDayOfSeason(FULL_TIME);
        SEASON = Times.getSeason(FULL_TIME);
        YEAR = Times.getYear(FULL_TIME);
        
        if(Seasons.RELOAD) {
            updateHud();
            Seasons.RELOAD = false;
        }

        if (DAY_OF_WEEK != dayOfWeek) {
            updateHud();
            // New day code here
        }

        if (DAY_OF_SEASON != dayOfSeason) {
            updateTextures();
        }


        if (YEAR > year) {
            // New year code fires here;
            year = YEAR;
        }

        time = TIME_OF_DAY;
        dayOfWeek = DAY_OF_WEEK;
        dayOfSeason = DAY_OF_SEASON;

    }

    public void updateHud() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            UUID labelId = (UUID) Seasons.labels.get(player.getName());
            GenericLabel label = (GenericLabel) sPlayer.getMainScreen().getWidget(labelId);
            label.setText(Times.getDate());
            label.setDirty(true);
        }
    }
    
    public void updateTextures() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTexturePack(Times.getSeasonUrl());
        }
    }
}
