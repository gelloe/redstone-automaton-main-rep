package me.gelloe.RedstoneAutomaton.inv;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.util.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class UpgradesGUI implements AutomatonGUI {

	public Automaton a;
	public Inventory i;
	short u;

	private static ItemStack upgrades = ItemBuilder.renamed(Material.CHEST, ChatColor.YELLOW + "Current upgrades: ");

	public UpgradesGUI(Automaton a) {
		this.a = a;
		i = Bukkit.createInventory(this, 3 * 9, "Upgrades");
		u = a.getUpgrades();
		update();
	}

	public void update() {
		ItemMeta m = upgrades.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(u > 0 ? ChatColor.RESET.toString() + ChatColor.GRAY + "Upgrade 1: Unlocked"
				: ChatColor.RESET.toString() + ChatColor.DARK_GRAY + "Upgrade 1: Locked (10 gold)");
		lore.add(u > 1 ? ChatColor.RESET.toString() + ChatColor.GRAY + "Upgrade 2: Unlocked"
				: ChatColor.RESET.toString() + ChatColor.DARK_GRAY + "Upgrade 2: Locked (20 gold)");
		lore.add(u > 2 ? ChatColor.RESET.toString() + ChatColor.GRAY + "Upgrade 3: Unlocked"
				: ChatColor.RESET.toString() + ChatColor.DARK_GRAY + "Upgrade 3: Locked (40 gold)");
		lore.add(u > 3 ? ChatColor.RESET.toString() + ChatColor.GRAY + "Upgrade 4: Unlocked"
				: ChatColor.RESET.toString() + ChatColor.DARK_GRAY + "Upgrade 4: Locked (120 gold)");
		lore.add(u > 4 ? ChatColor.RESET.toString() + ChatColor.GRAY + "Upgrade 5: Unlocked"
				: ChatColor.RESET.toString() + ChatColor.DARK_GRAY + "Upgrade 5: Locked (360 gold)");
		m.setLore(lore);
		upgrades.setItemMeta(m);

		i.setItem(1 * 9 + 4, upgrades);
	}

	@Override
	public Inventory getInventory() {
		return i;
	}

	@Override
	public void fireClick(InventoryClickEvent e) {
		if (e.getClick() != ClickType.LEFT)
			return;
		if (!e.getCurrentItem().equals(upgrades))
			return;
		Inventory inv = e.getWhoClicked().getInventory();
		ItemStack g = new ItemStack(Material.GOLD_INGOT);
		int amount;
		switch (u) {
		case 0:
			amount = 10;
			break;
		case 1:
			amount = 20;
			break;
		case 2:
			amount = 40;
			break;
		case 3:
			amount = 120;
			break;
		case 4:
			amount = 360;
			break;
		default:
			amount = 1000000;
		}
		if (inv.containsAtLeast(g, amount)) {
			removeItems(inv, amount);
			u++;
			a.setUpgrades(u);
			update();
			a.updateInventory();
		}
		e.setCancelled(true);
	}

	private static void removeItems(Inventory e, int amount) {
		for (ItemStack i : e.getContents()) {
			if (i == null)
				continue;
			if (i.getType() == Material.GOLD_INGOT) {
				if (i.getAmount() > amount) {
					i.setAmount(i.getAmount() - amount);
					return;
				} else if (i.getAmount() == amount) {
					i.setAmount(0);
					return;
				} else if (i.getAmount() < amount) {
					amount -= i.getAmount();
					i.setAmount(0);
				}
			}
		}

	}

	@Override
	public Automaton getAutomaton() {
		return a;
	}

}
