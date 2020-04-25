package me.gelloe.RedstoneAutomaton.util;

import java.util.Random;

public class Serial {
	
	

	public static String generate() {
		String string = "";
		String permittedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@#$%";
		Random r = new Random();
		for (int i = 0; i < 12; i++) {
			string = string.concat(permittedCharacters.charAt(r.nextInt(permittedCharacters.length())) + "");
		}
		return string;
	}
}
