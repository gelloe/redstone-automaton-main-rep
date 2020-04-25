package me.gelloe.RedstoneAutomaton;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	public static ItemStack RedstoneAutomaton() {
		ItemStack i = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		meta.setDisplayName("Redstone Automaton");
		lore.add("A complex piece of ancient Villager");
		lore.add("technology, forged at a smithing table.");
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack DisabledRedstoneAutomaton() {
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();

		i.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		meta.setDisplayName("Disabled Redstone Automaton");
		lore.add("An ancient Villager artifact whose ");
		lore.add("full power must be unleashed at an");
		lore.add("working smithing table");
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ShapedRecipe DisabledRedstoneAutomatonRecipe() {
		ShapedRecipe r = new ShapedRecipe(DisabledRedstoneAutomaton());
		r.shape("gpg","grg","gmg");
		r.setIngredient('g', Material.GOLD_INGOT);
		r.setIngredient('p', Material.PISTON);
		r.setIngredient('r', Material.REDSTONE);
		r.setIngredient('m', Material.MINECART);
		return r;
	}

}