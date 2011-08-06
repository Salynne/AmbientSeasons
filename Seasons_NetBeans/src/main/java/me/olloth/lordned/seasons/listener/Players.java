/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.listener;

import me.olloth.lordned.seasons.Seasons;
import me.olloth.lordned.seasons.util.Times;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * 
 * @author Olloth
 */
public class Players extends PlayerListener {

	Seasons plugin;

	public Players(Seasons plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
                //When a player(s) join, set up the clientside HUD via SPOUT
		SpoutPlayer sPlayer = (SpoutPlayer) event.getPlayer();
		playerInit(sPlayer);

	}

	public void playersInit(Player[] players) {
		for (Player player : players) {
			SpoutPlayer sPlayer = (SpoutPlayer) player;
			playerInit(sPlayer);
		}
	}
	
	public void playerInit(SpoutPlayer sPlayer) {
		
		GenericLabel label = new GenericLabel(Times.getDate());
		label.setHexColor(Integer.parseInt("FFFFFF", 16)).setX(10).setY(10);
		Seasons.labels.put(sPlayer.getName(), label.getId());
		label.setVisible(false);
		sPlayer.getMainScreen().attachWidget(label);
		
		if(!Seasons.HUDEnable.containsKey(sPlayer.getName())) {
			Seasons.HUDEnable.put(sPlayer.getName(), true);
		}
		
		if(Seasons.HUDEnable.get(sPlayer.getName())) {
			label.setVisible(true);
		}
		
	}
        
        
}
