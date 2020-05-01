package me.gelloe.RedstoneAutomaton;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import me.gelloe.RedstoneAutomaton.commands.CommandManager;
import me.gelloe.RedstoneAutomaton.inv.Inventories;
import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;

public class Main extends JavaPlugin{
	
	public static NamespacedKey key = NamespacedKey.minecraft("redstone_automaton");
	
				
	public void onEnable() {
		ConfigurationHandler.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnAutomaton(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Inventories(), this);
		ConfigurationHandler.loadAutomatonList();
		Bukkit.addRecipe(Items.DisabledRedstoneAutomatonRecipe());
		this.getCommand("raut").setExecutor(new CommandManager());
	}
	
	public void onDisable() {
		ConfigurationHandler.saveAutomatonList();
		ConfigurationHandler.saveFile();
	}

}
