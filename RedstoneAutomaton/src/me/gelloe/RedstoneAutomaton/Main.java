package me.gelloe.RedstoneAutomaton;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import me.gelloe.RedstoneAutomaton.commands.CommandManager;
import me.gelloe.RedstoneAutomaton.inv.Inventories;
import me.gelloe.RedstoneAutomaton.scripts.Script;
import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static NamespacedKey key = NamespacedKey.minecraft("redstone_automaton");
	
				
	public void onEnable() {
		ConfigurationHandler.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnAutomaton(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Inventories(), this);
		ConfigurationHandler.handleLoad();
		try {
			Bukkit.addRecipe(Items.DisabledRedstoneAutomatonRecipe());
		} catch (IllegalStateException e) {
			Bukkit.getLogger().info(ChatColor.YELLOW + "Recipe already loaded");
		}
		this.getCommand("raut").setExecutor(new CommandManager());
		Script.loadScripts();
	}
	
	public void onDisable() {
		ConfigurationHandler.handleSave();
		ConfigurationHandler.saveFile();
	}

}
