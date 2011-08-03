/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.listener;

import java.util.HashMap;
import me.olloth.lordned.seasons.Dates;
import me.olloth.lordned.seasons.Seasons;
import me.olloth.lordned.seasons.util.Config;
import me.olloth.lordned.seasons.util.Enums;
import org.bukkit.entity.Player;
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
        GenericLabel label = new GenericLabel(Enums.getDate());
        label.setHexColor(Integer.parseInt("FFFFFF", 16)).setX(10).setY(10);
        HashMap labels = plugin.getLabels();
        labels.put(sPlayer.getName(), label.getId());
        sPlayer.getMainScreen().attachWidget(label);
    }
    
    public void playersInit(Player[] players) {
        for(Player player : players) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            playerInit(sPlayer);
        }
    }
    
}
