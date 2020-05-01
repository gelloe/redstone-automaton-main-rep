package me.gelloe.RedstoneAutomaton.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.Direction;
import me.gelloe.RedstoneAutomaton.Main;

public class ConfigurationHandler {

	private static Main plugin = Main.getPlugin(Main.class);
	public static File autFILE;
	public static FileConfiguration autYAML;

	public static void saveAutomatonList() {
		for (Automaton a : Automaton.automaton) {
			UUID id = a.getPlayer().getUniqueId();
			Location l = a.getLocation();
			Direction d = a.getDirection();
			autYAML.set(id + "." + a.getID() + ".world", l.getWorld().getName());
			autYAML.set(id + "." + a.getID() + ".x", l.getX());
			autYAML.set(id + "." + a.getID() + ".y", l.getY());
			autYAML.set(id + "." + a.getID() + ".z", l.getZ());
			autYAML.set(id + "." + a.getID() + ".d", d.toString());
			autYAML.set(id + "." + a.getID() + ".i", saveInventory(a));
			a.destroyMinecart();
		}
		Automaton.automaton.clear();
		saveFile();
	}

	public static void loadAutomatonList() {
		for (String player : autYAML.getKeys(false)) {
			for (String autID : autYAML.getConfigurationSection(player).getKeys(false)) {
				String world_name = autYAML.getString(player + "." + autID + ".world");
				int x = autYAML.getInt(player + "." + autID + ".x");
				int y = autYAML.getInt(player + "." + autID + ".y");
				int z = autYAML.getInt(player + "." + autID + ".z");
				Direction d = Direction.getDirection(autYAML.getString(player + "." + autID + ".d"));
				Location l = new Location(Bukkit.getWorld(world_name), x, y, z);
				OfflinePlayer off_p = Bukkit.getOfflinePlayer(UUID.fromString(player));
				Automaton a = new Automaton(l, d, off_p, autID);
				loadInventory(autYAML.getString(player + "." + autID + ".i"), a);
			}
		}
	}

	public static void removeAutomaton(String playername, String automatonname) {
		autYAML.getConfigurationSection(playername).set(automatonname, null);
	}

	public static void saveFile() {
		try {
			autYAML.save(autFILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reloadFile() {
		autYAML = YamlConfiguration.loadConfiguration(autFILE);
	}

	public static void setUp() {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		if (!(new File(plugin.getDataFolder(), "\\inv")).exists())
			new File(plugin.getDataFolder(), "\\inv").mkdir();

		autFILE = new File(plugin.getDataFolder(), "automatons.yml");

		if (!autFILE.exists()) {
			try {
				autFILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		autYAML = YamlConfiguration.loadConfiguration(autFILE);
	}

	public FileConfiguration getAutomatons() {
		return autYAML;
	}
	
	public static String saveInventory(Automaton a) {
		Inventory inventory = a.getInventory();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (final BukkitObjectOutputStream data = new BukkitObjectOutputStream(stream)) {
            data.writeInt(9);
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.flush();
            data.close();
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (final IOException e) {
            e.printStackTrace();
        }
		return null;
    }
	
	public static void loadInventory(String s, Automaton a) {
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(s)))) {
            data.readInt();
    		Inventory inv = Bukkit.createInventory(a, 9);
            for (int i = 0; i < 9; i++)
                inv.setItem(i, (ItemStack) data.readObject());
            a.setInventory(inv);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}