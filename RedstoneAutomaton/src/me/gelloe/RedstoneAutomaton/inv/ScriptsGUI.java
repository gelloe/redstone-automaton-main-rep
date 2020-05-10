package me.gelloe.RedstoneAutomaton.inv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.gelloe.RedstoneAutomaton.Automaton;
import me.gelloe.RedstoneAutomaton.Main;
import me.gelloe.RedstoneAutomaton.scripts.Script;
import me.gelloe.RedstoneAutomaton.util.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.wesjd.anvilgui.AnvilGUI;

public class ScriptsGUI implements AutomatonGUI {

	private Inventory i;
	private Automaton a;
	private static ItemStack BACK = ItemBuilder.glowing(Material.ARROW, ChatColor.DARK_RED + "Back");
	private static ItemStack CREATE_NEW = ItemBuilder.renamed(Material.LIME_WOOL,
			ChatColor.GREEN + "Create new script");
	private static ItemStack BLANK_SPACE = ItemBuilder.renamed(Material.GRAY_STAINED_GLASS_PANE, "");

	public ScriptsGUI(Automaton a) {
		this.a = a;
		i = Bukkit.createInventory(this, 0 + 5 * 9, "Scripts...");
		i.setItem(1 * 9 - 1, CREATE_NEW);
		i.setItem(2 * 9 - 1, BLANK_SPACE);
		i.setItem(3 * 9 - 1, BLANK_SPACE);
		i.setItem(4 * 9 - 1, BLANK_SPACE);
		i.setItem(5 * 9 - 1, BACK);
		update();
	}

	@Override
	public Inventory getInventory() {
		update();
		return i;
	}
	
	public void update() {
		for (Script s : Script.scripts.keySet()) {
			if (Script.scripts.get(s).equals("global") || Script.scripts.get(s).equals(a.getID())) {
				ItemStack paper = ItemBuilder.renamed(Material.PAPER, ChatColor.BLUE + s.fileLocation.getName());
				ItemMeta m = paper.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				String global = Script.scripts.get(s).equals("global") ? "Global" : a.getID();
				lore.add(ChatColor.RESET.toString() + ChatColor.DARK_GRAY + global);
				m.setLore(lore);
				paper.setItemMeta(m);
				i.addItem(paper);
			}
		}
	}

	@Override
	public void fireClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null)
			return;
		ItemStack i = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		if (i.equals(CREATE_NEW)) {
			// Inventories.openInv(p, new NameScriptGUI(a).getInventory());
			AnvilGUI.Builder gui = new AnvilGUI.Builder();
			gui.plugin(Main.getPlugin(Main.class));
			gui.item(new ItemStack(Material.PAPER));
			gui.text("Enter name here");
			gui.title("New script");
			gui.onComplete((player, string) -> {
				try {
					File dataFolder = Main.getPlugin(Main.class).getDataFolder();
					File scriptsFolder = new File(dataFolder, "\\scripts");
					if (!dataFolder.exists())
						dataFolder.mkdir();
					if (!scriptsFolder.exists())
						scriptsFolder.mkdir();
					File f = new File(scriptsFolder, string + ".txt");
					if (!f.exists())
						f.createNewFile();
					else 
						return AnvilGUI.Response.text("Script name is unavailable");
					Script.scripts.put(new Script(f), "global");
				} catch (IOException e1) {
					return AnvilGUI.Response.text("Script name is unavailable");
				}
				return AnvilGUI.Response.close();
			});
			gui.onClose(player -> {
				Inventories.openInv(player, getInventory());
			});
			gui.open(p);

		} else if (i.equals(BACK)) {
			Inventories.openInv((Player) e.getWhoClicked(), new MainGUI(this.a).getInventory());
		} else if (i.getType().equals(Material.PAPER)) {
			
		}
		e.setCancelled(true);
	}

	@Override
	public Automaton getAutomaton() {
		return a;
	}

}
