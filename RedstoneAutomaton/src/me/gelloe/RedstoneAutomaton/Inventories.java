package me.gelloe.RedstoneAutomaton;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventories implements Listener {
	
	private static ItemStack Green_Wool_Icon = Items.glowing(Material.LIME_WOOL, ChatColor.GREEN + "Start script");
	private static ItemStack Yellow_Wool_Icon = Items.glowing(Material.YELLOW_WOOL, "Edit script");
	private static ItemStack Red_Wool_Icon = Items.glowing(Material.RED_WOOL, ChatColor.RED + "Stop script");
	private static ItemStack Scripts_Icon = Items.glowing(Material.PAPER, ChatColor.BLUE + "Scripts... ");
	private static ItemStack Tools_Icon = Items.glowing(Material.DIAMOND_PICKAXE, "Tools...");
	private static ItemStack Inventory_Icon = Items.glowing(Material.CHEST, "Open inventory");
	private static ItemStack Info_Icon = Items.glowing(Material.FURNACE, ChatColor.DARK_GRAY + "Info");
	private static ItemStack Upgrades_Icon = Items.glowing(Material.ANVIL, ChatColor.DARK_GRAY + "Upgrades");
	private static ItemStack Close_Icon = Items.glowing(Material.ARROW, ChatColor.DARK_RED + "Back");
	

	public static Inventory mainGUI(Automaton a) {
		Inventory i = Bukkit.createInventory(null, 0 + 5 * 9, "Redstone Automaton");
		ItemMeta im = Info_Icon.getItemMeta();
		List<String> info = new ArrayList<String>();
		info.add(ChatColor.RESET + "" + ChatColor.GRAY + "Redstone Automaton");
		info.add(ChatColor.RESET + "" + ChatColor.GRAY + "ID: " + a.getID());
		try {
			info.add(ChatColor.RESET + "" + ChatColor.GRAY + "Creator: " + a.getPlayer().getName());
		} catch (NullPointerException e) {
			info.add(ChatColor.RESET + "" + ChatColor.GRAY + "Creator: Unknown");
		}
		info.add(ChatColor.RESET + "" + ChatColor.GRAY + "X: " + a.getLocation().getBlockX());
		info.add(ChatColor.RESET + "" + ChatColor.GRAY + "Y: " + a.getLocation().getBlockY());
		info.add(ChatColor.RESET + "" + ChatColor.GRAY + "Z: " + a.getLocation().getBlockZ());
		im.setLore(info);
		Info_Icon.setItemMeta(im);
		i.setItem(1 + 9 * 1, Green_Wool_Icon);
		i.setItem(1 + 9 * 2, Yellow_Wool_Icon);
		i.setItem(1 + 9 * 3, Red_Wool_Icon);
		i.setItem(6 + 9 * 1, Scripts_Icon);
		i.setItem(7 + 9 * 1 , Info_Icon);
		i.setItem(6 + 9 * 2, Tools_Icon);
		i.setItem(7 + 9 * 2, Upgrades_Icon);
		i.setItem(6 + 9 * 3, Inventory_Icon);
		i.setItem(7 + 9 * 3 , Close_Icon);
		return i;
	}
	

	@EventHandler
	public static void InventoryClickEvent(InventoryClickEvent e) {
		if (e.getClickedInventory().getSize() == 5 * 9) {
			if (e.getCursor().equals(Scripts_Icon)) {
				
			}
			e.setCancelled(true);
		}
	}

}
