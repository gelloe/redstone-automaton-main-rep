package me.gelloe.RedstoneAutomaton.inv;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.gelloe.RedstoneAutomaton.Automaton;

public class ScriptsGUI implements AutomatonGUI{
	
	Inventory i;
	Automaton a;
	
	public ScriptsGUI(Automaton a) {
		this.a = a;
		i = Bukkit.createInventory(this, 0 + 5 * 9, "Scripts...");
	}

	@Override
	public Inventory getInventory() {
		return i;
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void fireClick(InventoryClickEvent e) {
		
	}

	@Override
	public Automaton getAutomaton() {
		return a;
	}

}
