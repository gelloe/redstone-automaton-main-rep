package me.gelloe.RedstoneAutomaton.scripts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import me.gelloe.RedstoneAutomaton.Main;

public class Script {
	public static HashMap<Script, String> scripts = new HashMap<Script, String>();
	
	public File fileLocation;
	
	public Script(File f) {
		this.fileLocation = f;
	}
	
	public Script(String f) throws IOException {
		this.fileLocation = new File(Main.getPlugin(Main.class).getDataFolder(), "\\scripts\\" + f + ".txt");
		if (!fileLocation.exists()) {
			fileLocation.createNewFile();
		}
	}
	
	public static void loadScripts() {
		File dataFolder = Main.getPlugin(Main.class).getDataFolder();
		File scriptsFolder = new File(dataFolder, "\\scripts");
		
		if (!dataFolder.exists())
			dataFolder.mkdir();
		if (!scriptsFolder.exists())
			scriptsFolder.mkdir();
		for (File f : scriptsFolder.listFiles()) {
			if (f.isFile())
				scripts.put(new Script(f), "global");
			else if (f.isDirectory()) {
				for (File f1 : f.listFiles()) {
					scripts.put(new Script(f1), f1.getName());
				}
			}
		}
	}
}
