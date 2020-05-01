package me.gelloe.RedstoneAutomaton;

import org.bukkit.GameMode;
import org.bukkit.Material;
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

	private static int c = 0;

	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (item.equals(Items.RedstoneAutomaton())) {
				if (p.getGameMode() == GameMode.CREATIVE) {
					c++;
					if (c == 2) {
						spawnAutomaton(e, false);
						c = 0;
					}
				} else {
					spawnAutomaton(e, false);
				}
			}
			if (item.equals(Items.DisabledRedstoneAutomaton())
					&& e.getClickedBlock().getType().equals(Material.SMITHING_TABLE)) {
				item.setAmount(item.getAmount() - 1);
				inv.setItem(inv.getHeldItemSlot(), Items.RedstoneAutomaton());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (item.getType() == Material.GOLDEN_AXE) {
				for (Automaton A : Automaton.automaton)
					A.move();
			}
			if (item.equals(Items.RedstoneAutomaton())) {
				spawnAutomaton(e, true);
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

	public static void spawnAutomaton(PlayerInteractEvent e, boolean nearPlayer) {
		if (nearPlayer) {
			new Automaton(e.getPlayer().getLocation(), Direction.getDirection(e.getPlayer().getFacing()),
					e.getPlayer(), Serial.generate());
		} else {
			new Automaton(e.getClickedBlock().getRelative(e.getBlockFace()).getLocation(),
					Direction.getDirection(e.getPlayer().getFacing()), e.getPlayer(), Serial.generate());
		}
	}

}
