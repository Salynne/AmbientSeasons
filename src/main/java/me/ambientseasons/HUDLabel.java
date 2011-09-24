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

package me.ambientseasons;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

public class HUDLabel extends GenericLabel {
	int count;
	SpoutPlayer sPlayer;
	AmbientSeasons plugin;

	public HUDLabel(SpoutPlayer sPlayer, AmbientSeasons plugin) {
		super();
		count = 0;
		this.sPlayer = sPlayer;
		this.plugin = plugin;
		this.setX(plugin.getConfig().getHUDX()).setY(plugin.getConfig().getHUDY());
	}
	
	@Override
	public void onTick() {
		count++;
		if (count % 10 == 0) {
			this.setVisible(plugin.getHUDEnable().get(sPlayer.getName()));
			this.setText(ChatColor.WHITE + plugin.getCalendar().getTimes(sPlayer).getDate());
			this.setDirty(true);
		}
	}

}
