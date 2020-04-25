package me.gelloe.RedstoneAutomaton.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.Direction;
import me.gelloe.RedstoneAutomaton.Main;

public class ConfigurationHandler {

	private static Main plugin = Main.getPlugin(Main.class);

	public static File automatonsFile;
	public static FileConfiguration automatonsYAML;

	public static void saveAutomatonList() {
		for (Automaton a : Automaton.automaton) {
			automatonsYAML.set(a.getPlayer().getUniqueId() + "." + a.getID() + ".world", a.getLocation().getWorld().getName());
			automatonsYAML.set(a.getPlayer().getUniqueId() + "." + a.getID() + ".x", a.getLocation().getX());
			automatonsYAML.set(a.getPlayer().getUniqueId() + "." + a.getID() + ".y", a.getLocation().getY());
			automatonsYAML.set(a.getPlayer().getUniqueId() + "." + a.getID() + ".z", a.getLocation().getZ());
			automatonsYAML.set(a.getPlayer().getUniqueId() + "." + a.getID() + ".d", a.getDirection().toString());
			a.destroyMinecart();
		}
		Automaton.automaton.clear();
		saveFile();
	}

	public static void loadAutomatonList() {
		for (String player : automatonsYAML.getKeys(false)) {
			for (String automatonPreLoaded : automatonsYAML.getConfigurationSection(player).getKeys(false)) {
				String world_name = automatonsYAML.getString(player + "." + automatonPreLoaded + ".world");
				int x = automatonsYAML.getInt(player + "." + automatonPreLoaded + ".x");
				int y = automatonsYAML.getInt(player + "." + automatonPreLoaded + ".y");
				int z = automatonsYAML.getInt(player + "." + automatonPreLoaded + ".z");
				String d = automatonsYAML.getString(player + "." + automatonPreLoaded + ".d");
				new Automaton(new Location(Bukkit.getWorld(world_name), x, y, z), Direction.getDirection(d), Bukkit.getOfflinePlayer(UUID.fromString(player)), automatonPreLoaded);
			}
		}
	}
	
	public static void removeAutomaton(String playername, String automatonname) {
		automatonsYAML.getConfigurationSection(playername).set(automatonname, null);
	}
	
	public static void saveFile() {
		try {
			automatonsYAML.save(automatonsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadFile() {
		automatonsYAML = YamlConfiguration.loadConfiguration(automatonsFile);
	}

	public static void setUp() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		automatonsFile = new File(plugin.getDataFolder(), "automatons.yml");

		if (!automatonsFile.exists()) {
			try {
				automatonsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		automatonsYAML = YamlConfiguration.loadConfiguration(automatonsFile);
	}
	
	public FileConfiguration getAutomatons() {
		return automatonsYAML;
	}

}
