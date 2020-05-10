package me.gelloe.RedstoneAutomaton.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	public static void addGlow(ItemMeta m) {
		m.addEnchant(Enchantment.DURABILITY, 3, true);
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
	
	public static ItemStack glowing(Material i, String name) {
		ItemStack s = renamed(i, name);
		ItemMeta m = s.getItemMeta();
		addGlow(m);
		s.setItemMeta(m);
		return s;
	}
	
	public static ItemStack renamed(Material i, String name) {
		ItemStack s = new ItemStack(i);
		ItemMeta m = s.getItemMeta();
		m.setDisplayName(ChatColor.RESET  + "" + ChatColor.YELLOW + name);
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		s.setItemMeta(m);
		return s;
	}

}
