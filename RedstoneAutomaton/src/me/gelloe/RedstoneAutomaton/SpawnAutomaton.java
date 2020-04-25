package me.gelloe.RedstoneAutomaton;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;

import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;

public class SpawnAutomaton implements Listener{
	
	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent e) {	
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if (item.getType() == Material.MINECART && item.getItemMeta().getDisplayName().equals("Redstone Automaton")) {
				Location l = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation();
				new Automaton(l, Direction.getDirection(e.getPlayer().getFacing()), e.getPlayer());
			}
			
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if (item.getType() == Material.GOLDEN_AXE) {
				for (Automaton A : Automaton.automaton)
					A.move();
			}
			
		}
	}
	
	@EventHandler
	public void PlayerInteractEntityEvent(PlayerInteractAtEntityEvent e) {
		for (Automaton a : Automaton.automaton) {
			if (a.getMinecart() == e.getRightClicked()) {
				a.showInv(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void VehicleDestroyEvent(VehicleDestroyEvent e) {
		for (Automaton a : Automaton.automaton) {
			if (e.getVehicle().equals(a.getMinecart())) {
				ConfigurationHandler.removeAutomaton(a.getPlayer().getUniqueId().toString(), a.getID());
				Automaton.automaton.remove(a);
				return;
			}
		}
		
	}

}
