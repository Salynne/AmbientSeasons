package me.olloth.lordned.seasons;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;
import me.olloth.lordned.seasons.listener.Players;
import me.olloth.lordned.seasons.listener.SListener;
import me.olloth.lordned.seasons.util.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Seasons extends JavaPlugin {

	// Setup Bukkit's Logger
	public static final Logger log = Logger.getLogger("Minecraft");

	public static boolean RELOAD;
	String logPrefix = "[Seasons] ";
	private PluginDescriptionFile info;
	private PluginManager pm;
	private File directory, config;
	private Players players;
	private SListener sListener;
	public static HashMap<String, UUID> labels;
	public static HashMap<String, Boolean> HUDEnable;

	public void onDisable() {

		System.out.println("[" + info.getName() + "] is now disabled!");
	}

	public void onEnable() {
		directory = getDataFolder();
		info = getDescription();
		pm = getServer().getPluginManager();
		players = new Players(this);
		sListener = new SListener(this);

		RELOAD = true;
		labels = new HashMap<String, UUID>();
		HUDEnable = new HashMap<String, Boolean>();

		// Load the Config
		Config.configSetup(directory, config);

		pm.registerEvent(Type.PLAYER_JOIN, players, Priority.Low, this);
		pm.registerEvent(Type.CUSTOM_EVENT, sListener, Priority.Low, this);

		players.playersInit(getServer().getOnlinePlayers());

		System.out.println("[" + info.getName() + "] version "
				+ info.getVersion() + " is now enabled!");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String commandName = command.getName().toLowerCase();
		if (commandName.equals("test")) {
			System.out.println("Test");

		}
		return false;
	}
}
