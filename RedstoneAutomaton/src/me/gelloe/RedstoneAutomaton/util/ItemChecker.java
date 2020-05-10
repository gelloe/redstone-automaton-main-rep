package me.gelloe.RedstoneAutomaton.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemChecker {
	
	private static Material[] PICKAXES = {Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE};
	private static Material[] AXES = {Material.WOODEN_AXE, Material.STONE_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.DIAMOND_AXE};
	private static Material[] SHOVELS = {Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.DIAMOND_SHOVEL};
	private static Material[] HOES = {Material.WOODEN_HOE, Material.STONE_HOE, Material.GOLDEN_HOE, Material.IRON_HOE, Material.DIAMOND_HOE};
	
	public static boolean isPick(ItemStack is) {
		for (Material m : PICKAXES) {
			if (is.getType().equals(m)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAxe(ItemStack is) {
		for (Material m : AXES) {
			if (is.getType().equals(m)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isShovel(ItemStack is) {
		for (Material m : SHOVELS) {
			if (is.getType().equals(m)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoe(ItemStack is) {
		for (Material m : HOES) {
			if (is.getType().equals(m)) {
				return true;
			}
		}
		return false;
	}

}
