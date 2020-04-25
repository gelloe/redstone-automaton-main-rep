package me.gelloe.RedstoneAutomaton;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;

public class Main extends JavaPlugin{
	
				
	public void onEnable() {
		ConfigurationHandler.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnAutomaton(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Inventories(), this);
		ConfigurationHandler.loadAutomatonList();
		Bukkit.addRecipe(Items.DisabledRedstoneAutomatonRecipe());
	}
	
	public void onDisable() {
		ConfigurationHandler.saveFile();
		ConfigurationHandler.saveAutomatonList();
	}

}
