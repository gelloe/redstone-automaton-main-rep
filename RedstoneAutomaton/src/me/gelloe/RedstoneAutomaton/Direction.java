package me.gelloe.RedstoneAutomaton;

import org.bukkit.block.BlockFace;

public enum Direction {

	POSITIVE_X, NEGATIVE_X, POSITIVE_Y, NEGATIVE_Y, POSITIVE_Z, NEGATIVE_Z;

	public static Direction getDirection(BlockFace b) {
		switch (b) {
			case EAST:
				return POSITIVE_X;
			case WEST:
				return NEGATIVE_X;
			case NORTH:
				return NEGATIVE_Z;
			case SOUTH:
				return POSITIVE_Z;
			case UP:
				return POSITIVE_Y;
			case DOWN:
				return NEGATIVE_Y;
			default:
				return NEGATIVE_Z;
		}
	}
	
	public static float getPitch(Direction d) {
		switch(d) {
			case POSITIVE_X:
				return 0;
			case NEGATIVE_X:
				return 180;
			case POSITIVE_Y:
				return 90;
			case NEGATIVE_Y:
				return 90;
			case POSITIVE_Z:
				return 90;
			case NEGATIVE_Z:
				return -90;
			default:
				return -90;
		}
	}
	
	public static float getYaw(Direction d) {
		switch(d) {
			case POSITIVE_X:
				return 0;
			case NEGATIVE_X:
				return 0;
			case POSITIVE_Y:
				return -90;
			case NEGATIVE_Y:
				return 90;
			case POSITIVE_Z:
				return 0;
			case NEGATIVE_Z:
				return 0;
			default:
				return 0;
		}
	}

	public static Direction getDirection(String d) {
		String direction = d.toUpperCase();
			switch(direction) {
			case "POSITIVE_X":
				return Direction.POSITIVE_X;
			case "NEGATIVE_X":
				return Direction.NEGATIVE_X;
			case "POSITIVE_Y":
				return Direction.POSITIVE_Y;
			case "NEGATIVE_Y":
				return Direction.NEGATIVE_Y;
			case "POSITIVE_Z":
				return Direction.POSITIVE_Z;
			case "NEGATIVE_Z":
				return Direction.NEGATIVE_Z;
		}
		return null;
	}


}
