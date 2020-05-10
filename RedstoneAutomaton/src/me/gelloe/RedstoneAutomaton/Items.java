package me.gelloe.RedstoneAutomaton;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

import me.gelloe.RedstoneAutomaton.util.ItemBuilder;

public class Items {

	public static ItemStack RedstoneAutomaton() {
		ItemStack i = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
		KnowledgeBookMeta meta = (KnowledgeBookMeta) i.getItemMeta();
		meta.addRecipe(Main.key);
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.GOLD + "Redstone Automaton");
		ItemBuilder.addGlow(meta);
		lore.add(ChatColor.RESET + ChatColor.ITALIC.toString() + ChatColor.YELLOW + "A complex piece of ancient Villager");
		lore.add(ChatColor.RESET + ChatColor.ITALIC.toString() + ChatColor.YELLOW + "technology, forged at a smithing table.");
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack DisabledRedstoneAutomaton() {
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.GOLD + "Disabled Redstone Automaton");
		lore.add(ChatColor.RESET + ChatColor.ITALIC.toString() + ChatColor.YELLOW + "An ancient Villager artifact whose");
		lore.add(ChatColor.RESET + ChatColor.ITALIC.toString() + ChatColor.YELLOW + "full power must be unleashed at an");
		lore.add(ChatColor.RESET + ChatColor.ITALIC.toString() + ChatColor.YELLOW + "working smithing table");
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ShapedRecipe DisabledRedstoneAutomatonRecipe() {
		ShapedRecipe r = new ShapedRecipe(Main.key, DisabledRedstoneAutomaton());
		r.shape("gpg","grg","gmg");
		r.setIngredient('g', Material.GOLD_INGOT);
		r.setIngredient('p', Material.PISTON);
		r.setIngredient('r', Material.REDSTONE);
		r.setIngredient('m', Material.MINECART);
		return r;
	}

}
