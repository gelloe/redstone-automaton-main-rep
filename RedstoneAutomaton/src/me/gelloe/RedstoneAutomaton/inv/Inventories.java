package me.gelloe.RedstoneAutomaton.inv;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import me.gelloe.RedstoneAutomaton.Main;

public class Inventories implements Listener {

	@EventHandler
	public static void InventoryClickEvent(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getHolder() instanceof AutomatonGUI) {
			if (e.getClick().isLeftClick()) {
				AutomatonGUI g = (AutomatonGUI) e.getClickedInventory().getHolder();
				g.fireClick(e);
			} else {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public static void InventoryDragEvent(InventoryDragEvent e) {
		if (e.getInventory().getHolder() instanceof AutomatonGUI)
			e.setCancelled(true);
	}

	public static void openInv(Player p, Inventory i) {
		new BukkitRunnable() {
			@Override
			public void run() {
				p.openInventory(i);
			}
		}.runTask(Main.getPlugin(Main.class));
	}

	public static void closeInv(Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				p.closeInventory();
			}
		}.runTask(Main.getPlugin(Main.class));
	}

}
