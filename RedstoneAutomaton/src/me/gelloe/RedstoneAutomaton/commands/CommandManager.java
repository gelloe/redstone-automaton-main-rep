package me.gelloe.RedstoneAutomaton.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.Items;
import me.gelloe.RedstoneAutomaton.Main;
import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;

public class CommandManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args[0].toLowerCase()) {
		case "save":
			ConfigurationHandler.saveAutYAML();
			break;
		case "rl":
		case "reload":
			ConfigurationHandler.handleSave();
			new BukkitRunnable() {
				@Override
				public void run() {
					ConfigurationHandler.handleLoad();
				}
			}.runTaskLater(Main.getPlugin(Main.class), 10);
			break;
		case "give":
			if (sender instanceof Player)
				((Player) sender).getInventory().addItem(Items.RedstoneAutomaton());
			break;
		case "test":
			for (Automaton a : Automaton.automaton) {
				Bukkit.getLogger().info(a.getPicks().toString());
			}
			
		}
		return true;
	}

}
