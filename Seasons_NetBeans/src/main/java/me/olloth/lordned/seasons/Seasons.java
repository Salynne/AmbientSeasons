package me.olloth.lordned.seasons;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import me.olloth.lordned.seasons.listener.Blocks;
import me.olloth.lordned.seasons.listener.Players;
import me.olloth.lordned.seasons.listener.SListener;
import me.olloth.lordned.seasons.util.Config;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Seasons Plugin for Bukkit and Spout
 * 
 * Let's the server have a custom calendar and rotating seasons based on it.
 * 
 * @author Olloth, LordNed
 *
 */
public class Seasons extends JavaPlugin {

	// Setup Bukkit's Logger
	public static final Logger log = Logger.getLogger("Minecraft");

	// Wheat Modifier (Modified growing times based on season)
	public static boolean WHEAT_MOD;
	public List<Location> WheatBlockLocations;

	String logPrefix = "[Seasons] ";
	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;
	private Players players;
	private SListener sListener;
	private Blocks bListener;
	public static HashMap<String, UUID> labels;
	public static HashMap<String, Boolean> HUDEnable;

	/**
	 * Calls when the plugin disables.
	 */
	public void onDisable() {
	
		Config.saveMap();
		System.out.println("[" + info.getName() + "] is now disabled!");
	}

	/**
	 * Calls on activation of the plugin.
	 */
	public void onEnable() {
		directory = getDataFolder();
		info = getDescription();
		pm = getServer().getPluginManager();
		
		// Initialize listeners
		players = new Players(this);
		sListener = new SListener(this);
		bListener = new Blocks(this);

		WHEAT_MOD = true; // TEMP (Load from config in future)
		labels = new HashMap<String, UUID>();
		HUDEnable = new HashMap<String, Boolean>();

		// Load the Config
		Config.configSetup(directory, config);

		// Register events
		pm.registerEvent(Type.PLAYER_JOIN, players, Priority.Low, this);
		pm.registerEvent(Type.CUSTOM_EVENT, sListener, Priority.Low, this);
		pm.registerEvent(Type.BLOCK_PLACE, bListener, Priority.Low, this);

		// Update any players who were online (In case of /reload)
		players.playersInit(getServer().getOnlinePlayers());
		sListener.updateHud();

		// Send an enable message
		System.out.println("[" + info.getName() + "] version "
				+ info.getVersion() + " is now enabled!");

	}

	/**
	 * Calls when a player uses a command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String commandName = command.getName().toLowerCase();
		
		/*
		 * Checks to see if the command is /seasonshud
		 * If so, it toggles the players HUD on or off.
		 */
		if (commandName.equals("seasonshud")) {
			Player player = (Player) sender;
			if (HUDEnable.containsKey(player.getName())) {
				if (HUDEnable.get(player.getName())) {
					HUDEnable.put(player.getName(), false);
					UUID labelId = labels.get(player.getName());
					SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
					sPlayer.getMainScreen().getWidget(labelId)
							.setVisible(false).setDirty(true);
				} else {
					HUDEnable.put(player.getName(), true);
					UUID labelId = labels.get(player.getName());
					SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
					sPlayer.getMainScreen().getWidget(labelId).setVisible(true)
							.setDirty(true);
				}
			}
		}
		return false;
	}
}
