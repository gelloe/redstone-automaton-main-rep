package me.gelloe.RedstoneAutomaton.inv;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.util.ItemBuilder;

public class MainGUI implements AutomatonGUI {

	private static ItemStack START_ICON = ItemBuilder.glowing(Material.LIME_WOOL, ChatColor.GREEN + "Start script");
	private static ItemStack EDIT_ICON = ItemBuilder.glowing(Material.YELLOW_WOOL, "Edit script");
	private static ItemStack STOP_ICON = ItemBuilder.glowing(Material.RED_WOOL, ChatColor.RED + "Stop script");
	private static ItemStack SCRIPTS_ICON = ItemBuilder.glowing(Material.PAPER, ChatColor.BLUE + "Scripts... ");
	private static ItemStack TOOLS_ICON = ItemBuilder.glowing(Material.DIAMOND_PICKAXE, "Tools...");
	private static ItemStack INVENTORY_ICON = ItemBuilder.glowing(Material.CHEST, "Open inventory");
	private static ItemStack INFO_ICON = ItemBuilder.glowing(Material.FURNACE, ChatColor.DARK_GRAY + "Info");
	private static ItemStack UPGRADES_ICON = ItemBuilder.glowing(Material.ANVIL, ChatColor.DARK_GRAY + "Upgrades");
	private static ItemStack CLOSE_ICON = ItemBuilder.glowing(Material.ARROW, ChatColor.DARK_RED + "Back");

	public Inventory i;
	public Automaton a;

	public MainGUI(Automaton a) {
		this.a = a;
		i = Bukkit.createInventory(this, 0 + 5 * 9, "Redstone Automaton");
		ItemMeta im = INFO_ICON.getItemMeta();
		List<String> info = new ArrayList<String>();
		info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "Redstone Automaton");
		info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "ID: " + a.getID());
		try {
			info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "Creator: " + a.getPlayer().getName());
		} catch (NullPointerException e) {
			info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "Creator: Unknown");
		}
		info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "X: " + a.getLocation().getBlockX());
		info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "Y: " + a.getLocation().getBlockY());
		info.add(ChatColor.RESET.toString() + ChatColor.GRAY + "Z: " + a.getLocation().getBlockZ());
		im.setLore(info);
		INFO_ICON.setItemMeta(im);
		i.setItem(1 + 9 * 1, START_ICON);
		i.setItem(1 + 9 * 2, EDIT_ICON);
		i.setItem(1 + 9 * 3, STOP_ICON);
		i.setItem(6 + 9 * 1, SCRIPTS_ICON);
		i.setItem(7 + 9 * 1, INFO_ICON);
		i.setItem(6 + 9 * 2, TOOLS_ICON);
		i.setItem(7 + 9 * 2, UPGRADES_ICON);
		i.setItem(6 + 9 * 3, INVENTORY_ICON);
		i.setItem(7 + 9 * 3, CLOSE_ICON);
	}

	@Override
	public Inventory getInventory() {
		return i;
	}

	@Override
	public void fireClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getCurrentItem();
		if (i == null) {
			e.setCancelled(true);
			return;
		}
		if (i.equals(START_ICON)) {
			// TODO: Start script
		} else if (i.equals((EDIT_ICON))) {
			// TODO: Script editor
		} else if (i.equals((STOP_ICON))) {
			// TODO: Stop script
		} else if (i.equals((SCRIPTS_ICON))) {
			Inventories.openInv(p, new ScriptsGUI(getAutomaton()).getInventory());
		} else if (i.equals((TOOLS_ICON))) {
			Inventories.openInv(p, new ToolsGUI(getAutomaton()).getInventory());
		} else if (i.equals((INVENTORY_ICON))) {
			Automaton a = ((AutomatonGUI) e.getInventory().getHolder()).getAutomaton();
			Inventory ai = a.getInventory();
			Inventories.openInv(p, ai);
		} else if (i.equals((UPGRADES_ICON))) {
			Inventories.openInv(p, new UpgradesGUI(getAutomaton()).getInventory());
		} else if (i.equals((CLOSE_ICON))) {
			Inventories.closeInv(p);
		}
		e.setCancelled(true);
	}

	@Override
	public Automaton getAutomaton() {
		return a;
	}

}
