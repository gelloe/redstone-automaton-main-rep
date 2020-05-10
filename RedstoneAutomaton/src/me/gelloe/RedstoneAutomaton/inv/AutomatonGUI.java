package me.gelloe.RedstoneAutomaton.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.gelloe.RedstoneAutomaton.Automaton;

public interface AutomatonGUI extends InventoryHolder {
	public Inventory getInventory();
	public void fireClick(InventoryClickEvent e);
	public Automaton getAutomaton();
}
