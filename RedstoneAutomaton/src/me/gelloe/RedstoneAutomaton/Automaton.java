package me.gelloe.RedstoneAutomaton;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.gelloe.RedstoneAutomaton.util.Serial;

public class Automaton {

	public static ArrayList<Automaton> automaton = new ArrayList<Automaton>();

	private Location l;
	private Direction d;
	private Inventory i = Bukkit.createInventory(null, 9, "Basic Inventory");
	private Minecart m;
	private Player p;
	private String serial_id;

	public Automaton(Location l, Direction d, Player p, String id) {
		setLocation(l);
		setDirection(d);
		m = createMinecart();
		setID(id);
		setPlayer(p);
		automaton.add(this);
		update();
	}

	public Automaton(Location l, Direction d, Player p) {
		new Automaton(l, d, p, Serial.generate());
	}

	public Location getLocation() {
		return l;
	}

	public void setLocation(Location l) {
		this.l = l;
	}

	public Direction getDirection() {
		return d;
	}

	public void setDirection(Direction d) {
		this.d = d;
	}

	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public String getID() {
		return serial_id;
	}

	public void setID(String serial_id) {
		this.serial_id = serial_id;
	}

	public Minecart getMinecart() {
		return m;
	}

	public void destroyMinecart() {
		getMinecart().remove();
	}

	public Minecart createMinecart() {
		Minecart dummyMinecart = (Minecart) l.getWorld()
				.spawnEntity(
						new Location(l.getWorld(), l.getBlockX() + 0.5, l.getY(), l.getZ() + 0.5,
								Direction.getPitch(getDirection()), Direction.getYaw(getDirection())),
						EntityType.MINECART);
		dummyMinecart.setGravity(false);
		BlockData piston = Bukkit.createBlockData(Material.PISTON);
		((Directional) piston).setFacing(BlockFace.NORTH);
		dummyMinecart.setDisplayBlockData(piston);
		dummyMinecart.setMaxSpeed(0);
		dummyMinecart.setInvulnerable(true);
		return dummyMinecart;
	}

	public void showInv(Player player) {
		player.openInventory(i);
	}

	public void update() {
		m.teleport(new Location(l.getWorld(), l.getX() + 0.5, l.getY(), l.getZ() + 0.5, Direction.getPitch(d), Direction.getYaw(d)));
	}

	public void move() {
		switch (getDirection()) {
		case POSITIVE_X:
			l.add(1, 0, 0);
			break;
		case NEGATIVE_X:
			l.add(-1, 0, 0);
			break;
		case POSITIVE_Y:
			l.add(0, 1, 0);
			break;
		case NEGATIVE_Y:
			l.add(0, -1, 0);
			break;
		case POSITIVE_Z:
			l.add(0, 0, 1);
			break;
		case NEGATIVE_Z:
			l.add(0, 0, -1);
			break;
		}

		update();
	}

	public void dig() {
		int dx = 0, dy = 0, dz = 0;
		switch (d) {
		case POSITIVE_X:
			dx = 1;
			break;
		case NEGATIVE_X:
			dx = -1;
			break;
		case POSITIVE_Y:
			dy = 1;
			break;
		case NEGATIVE_Y:
			dy = -1;
			break;
		case POSITIVE_Z:
			dz = 1;
			break;
		case NEGATIVE_Z:
			dz = -1;
			break;
		}
		Block b = l.getWorld().getBlockAt(l.getBlockX() + dx, l.getBlockY() + dy, l.getBlockZ() + dz);
		for (ItemStack e : b.getDrops())
			i.addItem(e);
		b.breakNaturally();
	}

}