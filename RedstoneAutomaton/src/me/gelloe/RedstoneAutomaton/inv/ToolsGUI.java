package me.gelloe.RedstoneAutomaton.inv;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.util.ItemBuilder;
import me.gelloe.RedstoneAutomaton.util.ItemChecker;
import net.md_5.bungee.api.ChatColor;

public class ToolsGUI implements AutomatonGUI {

	private Inventory i;
	private Automaton a;

	private static ItemStack PICKAXE = ItemBuilder.glowing(Material.IRON_PICKAXE, ChatColor.BLUE + "Pickaxes");
	private static ItemStack AXE = ItemBuilder.glowing(Material.IRON_AXE, ChatColor.BLUE + "Axes");
	private static ItemStack SHOVEL = ItemBuilder.glowing(Material.IRON_SHOVEL, ChatColor.BLUE + "Shovels");
	private static ItemStack HOE = ItemBuilder.glowing(Material.IRON_HOE, ChatColor.BLUE + "Hoes");
	private static ItemStack EMPTY_SLOT = ItemBuilder.renamed(Material.RED_STAINED_GLASS_PANE,
			ChatColor.DARK_RED + "Empty Slot");
	private static ItemStack RETURN = ItemBuilder.glowing(Material.ARROW, ChatColor.DARK_RED + "Back");

	public ToolsGUI(Automaton a) {
		this.a = a;
		i = Bukkit.createInventory(this, 0 + 5 * 9, "Tools...");
		addTools();
		i.setItem(1 + 9 * 0, PICKAXE);
		i.setItem(3 + 9 * 0, AXE);
		i.setItem(5 + 9 * 0, SHOVEL);
		i.setItem(7 + 9 * 0, HOE);
	}

	private void addTools() {
		for (int j = 0; j < 3; j++) {
			if (j < a.getPicks().size()) {
				i.setItem(1 + 9 * (j + 1), a.getPicks().get(j));
			} else {
				i.setItem(1 + 9 * (j + 1), EMPTY_SLOT);
			}
			if (j < a.getAxes().size()) {
				i.setItem(3 + 9 * (j + 1), a.getAxes().get(j));
			} else {
				i.setItem(3 + 9 * (j + 1), EMPTY_SLOT);
			}
			if (j < a.getShovels().size()) {
				i.setItem(5 + 9 * (j + 1), a.getShovels().get(j));
			} else {
				i.setItem(5 + 9 * (j + 1), EMPTY_SLOT);
			}
			if (j < a.getHoes().size()) {
				i.setItem(7 + 9 * (j + 1), a.getHoes().get(j));
			} else {
				i.setItem(7 + 9 * (j + 1), EMPTY_SLOT);
			}
		}
		i.setItem(8 + 4 * 9, RETURN);
	}

	@Override
	public Inventory getInventory() {
		return i;
	}

	@Override
	public void fireClick(InventoryClickEvent e) {
		if (e.getClick().isLeftClick()) {
			if (e.getCursor().getType() == Material.AIR && e.getCurrentItem() != null) {
				int pos = e.getRawSlot() % 9;
				int slot = (e.getRawSlot() - pos - 9) / 9;
				if (slot >= 0) {
					switch (pos) {
					case 1:
						if (ItemChecker.isPick(e.getCurrentItem())) {
							e.getWhoClicked().getInventory().addItem(a.getPicks().get(slot));
							a.getPicks().remove(slot);
							addTools();
						}
						break;
					case 3:
						if (ItemChecker.isAxe(e.getCurrentItem())) {
							e.getWhoClicked().getInventory().addItem(a.getAxes().get(slot));
							a.getAxes().remove(slot);
							addTools();
						}
						break;
					case 5:
						if (ItemChecker.isShovel(e.getCurrentItem())) {
							e.getWhoClicked().getInventory().addItem(a.getShovels().get(slot));
							a.getShovels().remove(slot);
							addTools();
						}
						break;
					case 7:
						if (ItemChecker.isHoe(e.getCurrentItem())) {
							e.getWhoClicked().getInventory().addItem(a.getHoes().get(slot));
							a.getHoes().remove(slot);
							addTools();
						}
						break;
					case 8:
						if (e.getRawSlot() == 44)
							Inventories.openInv((Player) e.getWhoClicked(), new MainGUI(this.a).getInventory());
						break;
					}
				}
			}
			if (e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
				if (e.getCursor() != null && e.getCurrentItem() != null) {
					int pos = e.getRawSlot() % 9;
					ItemStack cursor = new ItemStack(e.getCursor());
					if (pos == 1) {
						if (ItemChecker.isPick(e.getCursor())) {
							a.getPicks().add(cursor);
							addTools();
							e.getCursor().setAmount(0);
						}
					} else if (pos == 3) {
						if (ItemChecker.isAxe(e.getCursor())) {
							a.getAxes().add(cursor);
							addTools();
							e.getCursor().setAmount(0);
						}
					} else if (pos == 5) {
						if (ItemChecker.isShovel(e.getCursor())) {
							a.getShovels().add(cursor);
							addTools();
							e.getCursor().setAmount(0);
						}
					} else if (pos == 7) {
						if (ItemChecker.isHoe(e.getCursor())) {
							a.getHoes().add(cursor);
							addTools();
							e.getCursor().setAmount(0);
						}
					}
				}
			}
			e.setCancelled(true);
		}
	}

	@Override
	public Automaton getAutomaton() {
		return a;
	}

}
