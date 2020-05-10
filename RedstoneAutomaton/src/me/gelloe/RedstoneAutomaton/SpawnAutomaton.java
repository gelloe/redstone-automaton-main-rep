package me.gelloe.RedstoneAutomaton;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.gelloe.RedstoneAutomaton.util.ConfigurationHandler;
import me.gelloe.RedstoneAutomaton.util.Serial;

public class SpawnAutomaton implements Listener {

	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		ItemStack item = inv.getItemInMainHand();
		Action a = e.getAction();
		
		if (a == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType().equals(Material.SMITHING_TABLE)) {
				if (item.equals(Items.DisabledRedstoneAutomaton())) {
					item.setAmount(0);
					p.getWorld().dropItemNaturally(e.getClickedBlock().getRelative(BlockFace.UP).getLocation(), Items.RedstoneAutomaton());
					p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1.0f);
				}
			}
			if (item.equals(Items.RedstoneAutomaton()))
				spawnAutomaton(e, false);
		} else if (a == Action.RIGHT_CLICK_AIR) {
			if (item.equals(Items.RedstoneAutomaton()))
				spawnAutomaton(e, true);
		}
	}

	@EventHandler
	public void PlayerInteractEntityEvent(PlayerInteractAtEntityEvent e) {
		for (Automaton a : Automaton.automaton)
			if (a.getMinecart() == e.getRightClicked())
				a.showInv(e.getPlayer());
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

	public static void spawnAutomaton(PlayerInteractEvent e, boolean nearPlayer) {
		
		Player p = e.getPlayer();
		Location l = p.getLocation();
		Direction d = Direction.getDirection(p.getFacing());
		Block b = e.getClickedBlock();
		BlockFace bf = e.getBlockFace();
		
		if (nearPlayer)
			new Automaton(l, d, p, Serial.generate());
		else
			new Automaton(b.getRelative(bf).getLocation(), d, p, Serial.generate());
	}

}
