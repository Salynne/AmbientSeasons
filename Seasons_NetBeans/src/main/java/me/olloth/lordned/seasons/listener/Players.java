package me.olloth.lordned.seasons.listener;

import me.olloth.lordned.seasons.Seasons;
import me.olloth.lordned.seasons.util.Times;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * @author Olloth
 *
 */
public class Players extends PlayerListener {

	Seasons plugin;

	public Players(Seasons plugin) {
		this.plugin = plugin;
	}

	/**
	 * Calls when the player joins the server.
	 */
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
                //When a player(s) join, set up the clientside HUD via SPOUT
		SpoutPlayer sPlayer = (SpoutPlayer) event.getPlayer();
		playerInit(sPlayer);

	}

	/**
	 * Initializes all the players
	 * @param players - Array of players to initialize
	 */
	public void playersInit(Player[] players) {
		for (Player player : players) {
			SpoutPlayer sPlayer = (SpoutPlayer) player;
			playerInit(sPlayer);
		}
	}
	
	/**
	 * Initializes a SpoutPlayer
	 * @param sPlayer - Player to initialize
	 */
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
