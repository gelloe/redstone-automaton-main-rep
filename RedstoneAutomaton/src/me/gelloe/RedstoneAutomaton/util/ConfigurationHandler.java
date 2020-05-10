package me.gelloe.RedstoneAutomaton.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

	public static void handleSave() {
		for (Automaton a : Automaton.automaton)
			a.destroyMinecart();
		saveAutYAML();
		Automaton.automaton.clear();
		saveFile();
	}

	public static void saveAutYAML() {
		for (Automaton a : Automaton.automaton) {
			UUID id = a.getPlayer().getUniqueId();
			Location l = a.getLocation();
			Direction d = a.getDirection();
			autYAML.set(id + "." + a.getID() + ".world", l.getWorld().getName());
			autYAML.set(id + "." + a.getID() + ".x", l.getX());
			autYAML.set(id + "." + a.getID() + ".y", l.getY());
			autYAML.set(id + "." + a.getID() + ".z", l.getZ());
			autYAML.set(id + "." + a.getID() + ".d", d.toString());
			autYAML.set(id + "." + a.getID() + ".u", a.getUpgrades());
			
			autYAML.set(id + "." + a.getID() + ".i.inv", saveInventory(a.getInventory(), a.getInventory().getSize()));
			autYAML.set(id + "." + a.getID() + ".i.picks", encodeItems(a.getPicks()));
			autYAML.set(id + "." + a.getID() + ".i.axes", encodeItems(a.getAxes()));
			autYAML.set(id + "." + a.getID() + ".i.shovels", encodeItems(a.getShovels()));
			autYAML.set(id + "." + a.getID() + ".i.hoes", encodeItems(a.getHoes()));
			
		}
	}

	public static void handleLoad() {
		for (String player : autYAML.getKeys(false)) {
			for (String autID : autYAML.getConfigurationSection(player).getKeys(false)) {
				String world_name = autYAML.getString(player + "." + autID + ".world");
				int x = autYAML.getInt(player + "." + autID + ".x");
				int y = autYAML.getInt(player + "." + autID + ".y");
				int z = autYAML.getInt(player + "." + autID + ".z");
				short upgrades = (short) autYAML.getInt(player + "." + autID + ".u");
				Direction d = Direction.getDirection(autYAML.getString(player + "." + autID + ".d"));
				Location l = new Location(Bukkit.getWorld(world_name), x, y, z);
				OfflinePlayer off_p = Bukkit.getOfflinePlayer(UUID.fromString(player));
				
				ArrayList<ItemStack> picks = decodeItems(autYAML.getString(player + "." + autID + ".i.picks"));
				ArrayList<ItemStack> axes = decodeItems(autYAML.getString(player + "." + autID + ".i.axes"));
				ArrayList<ItemStack> shovels = decodeItems(autYAML.getString(player + "." + autID + ".i.shovels"));
				ArrayList<ItemStack> hoes = decodeItems(autYAML.getString(player + "." + autID + ".i.hoes"));
				
				Automaton a = new Automaton(l, d, off_p, autID);
				loadInventory(autYAML.getString(player + "." + autID + ".i.inv"), a);
				a.setUpgrades(upgrades);
				a.setPicks(picks);
				a.setAxes(axes);
				a.setShovels(shovels);
				a.setHoes(hoes);
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
	
	public static String saveInventory(Inventory inv, int size) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (final BukkitObjectOutputStream data = new BukkitObjectOutputStream(stream)) {
            data.writeInt(size);
            for (int i = 0; i < inv.getSize(); i++) {
                data.writeObject(inv.getItem(i));
            }
            data.flush();
            data.close();
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (final IOException e) {
            e.printStackTrace();
        }
		return null;
    }
	
	public static String encodeItems(ArrayList<ItemStack> items) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (final BukkitObjectOutputStream data = new BukkitObjectOutputStream(stream)) {
            data.writeInt(items.size());
            for (ItemStack i : items)
            	data.writeObject(i);
            data.flush();
            data.close();
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (final IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public static ArrayList<ItemStack> decodeItems(String s) {
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(s)))) {
        	int size = data.readInt();
    		ArrayList<ItemStack> items = new ArrayList<ItemStack>(size);
            for (int i = 0; i < size; i++)
                items.add((ItemStack) data.readObject());
            return items;
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public static void loadInventory(String s, Automaton a) {
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(s)))) {
            int size = data.readInt();
    		Inventory inv = Bukkit.createInventory(a, size);
            for (int i = 0; i < size; i++)
                inv.setItem(i, (ItemStack) data.readObject());
            a.setInventory(inv);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}