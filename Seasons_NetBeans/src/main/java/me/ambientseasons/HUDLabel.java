package me.ambientseasons;

import me.ambientseasons.util.Times;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

public class HUDLabel extends GenericLabel {
	Integer count = 0;
	SpoutPlayer sPlayer;

	public HUDLabel(SpoutPlayer sPlayer) {
		super();
		this.sPlayer = sPlayer;
	}
	
	@Override
	public boolean isDirty() {
		count++;
		if (count % 10 == 0) {
			this.setVisible(AmbientSeasons.HUDEnable.get(sPlayer.getName()));
			this.setText(ChatColor.WHITE + Times.getDate());
		}
		return true;
	}

}
