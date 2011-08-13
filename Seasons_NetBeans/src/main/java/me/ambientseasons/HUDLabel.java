package me.ambientseasons;

import me.ambientseasons.util.Times;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericLabel;

public class HUDLabel extends GenericLabel {
	Integer count = 0;

	public HUDLabel() {
		super();
	}
	
	@Override
	public boolean isDirty() {
		count++;
		if (count % 10 == 0) {
			this.setText(ChatColor.WHITE + Times.getDate());
		}
		return true;
	}

}
