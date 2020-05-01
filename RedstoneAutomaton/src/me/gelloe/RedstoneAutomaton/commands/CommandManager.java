package me.gelloe.RedstoneAutomaton.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args[0].toLowerCase()) {
		case "save":
			break;
		case "reload":
			break;
		}
		return true;
	}

}
