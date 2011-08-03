/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.listener;

import java.util.HashMap;
import me.olloth.lordned.seasons.Seasons;
import me.olloth.lordned.seasons.util.Config;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
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
        SpoutPlayer sPlayer = (SpoutPlayer) event.getPlayer();
        playerInit(sPlayer);

        
    }
    
    public void playerInit(SpoutPlayer sPlayer) {
        
    }
    
    
}
