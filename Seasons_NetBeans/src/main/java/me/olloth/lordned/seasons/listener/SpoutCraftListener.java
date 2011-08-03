/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.olloth.lordned.seasons.listener;

import me.olloth.lordned.seasons.Seasons;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;

/**
 *
 * @author Olloth
 */
public class SpoutCraftListener extends SpoutListener {
    
    Seasons plugin;
    
    SpoutCraftListener(Seasons plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
//        event.getPlayer().setTexturePack("Texture pack URL");
    }
    
    
    
}
