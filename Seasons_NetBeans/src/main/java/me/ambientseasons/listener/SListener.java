/*
 * This file is part of AmbientSeasons (https://github.com/Olloth/AmbientSeasons).
 * 
 * AmbientSeasons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ambientseasons.listener;

import me.ambientseasons.AmbientSeasons;
import me.ambientseasons.Calendar;
import me.ambientseasons.HUDLabel;
import me.ambientseasons.util.Config;
import me.ambientseasons.util.Times;

import org.getspout.spoutapi.event.spout.ServerTickEvent;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * 
 * @author Olloth
 */
public class SListener extends SpoutListener {

	private AmbientSeasons plugin;
	long count;

	private Calendar calendar;

	/**
	 * Constructor, sets the ticks counter to 0;
	 * 
	 * @param plugin
	 */
	public SListener(AmbientSeasons plugin, Calendar calendar) {
		count = 0;
		this.calendar = calendar;
		this.plugin = plugin;
	}

	/**
	 * Runs when SpoutCraft enables after a player joins.
	 */
	@Override
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {

		SpoutPlayer sPlayer = event.getPlayer();

		if (!AmbientSeasons.HUDEnable.containsKey(sPlayer.getName())) {
			AmbientSeasons.HUDEnable.put(sPlayer.getName(), true);
		}

		HUDLabel label = new HUDLabel(sPlayer);
		label.setX(10).setY(Config.getHUDPosition());

		sPlayer.getMainScreen().attachWidget(plugin, label);

		if (Config.isWorldEnabled(sPlayer.getWorld()) && !sPlayer.hasPermission("ambientseasons.exempt")) {
			sPlayer.setTexturePack(Times.getSeasonUrl());
		}
	}

	/**
	 * Runs every tick, BE CAREFUL HERE.
	 */
	@Override
	public void onServerTick(ServerTickEvent event) {

		if ((count % 20) == 0) {
			calendar.onSecond();
		}

		count++;
	}

}
