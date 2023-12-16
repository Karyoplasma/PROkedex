package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enums.Tier;

public class SpawnDumpReader {

	private SpawnDumpReader() {
		
	}
	
	public static List<Spawn> getAllLandSpawns(){
		List<Spawn> spawns = new ArrayList<Spawn>();
		File landSpawnDumpfile = new File("resources/land_spawns.csv");
		if (!landSpawnDumpfile.exists()) {
			return null;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(landSpawnDumpfile));
			String in = reader.readLine();
			while ((in = reader.readLine()) != null) {
				String[] inSplit = in.split(",");
				int[] daytime = new int[] {Integer.parseInt(inSplit[3]),Integer.parseInt(inSplit[4]),Integer.parseInt(inSplit[5])};
				boolean memberOnly = false;
				if (Integer.parseInt(inSplit[9]) == 1) {
					memberOnly = true;
				}
				spawns.add(new Spawn(inSplit[0], inSplit[1], Integer.parseInt(inSplit[2]), daytime , Integer.parseInt(inSplit[6]), Integer.parseInt(inSplit[7]), inSplit[8], memberOnly, getTier(inSplit[10])));
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return spawns;
	}
	
	public static List<SurfSpawn> getAllSurfSpawns(){
		List<SurfSpawn> spawns = new ArrayList<SurfSpawn>();
		File landSpawnDumpfile = new File("resources/surf_spawns.csv");
		if (!landSpawnDumpfile.exists()) {
			return null;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(landSpawnDumpfile));
			String in = reader.readLine();
			while ((in = reader.readLine()) != null) {
				String[] inSplit = in.split(",");
				int[] daytime = new int[] {Integer.parseInt(inSplit[3]),Integer.parseInt(inSplit[4]),Integer.parseInt(inSplit[5])};
				boolean memberOnly = false;
				if (Integer.parseInt(inSplit[9]) == 1) {
					memberOnly = true;
				}
				boolean fishing = false;
				if (Integer.parseInt(inSplit[11]) == 1) {
					fishing = true;
				}
				boolean fishingOnly = false;
				if (Integer.parseInt(inSplit[12]) == 1) {
					fishingOnly = true;
				}
				String fishingRod = "";
				if (inSplit.length == 14) {
					fishingRod = inSplit[13];
				}
				spawns.add(new SurfSpawn(inSplit[0], inSplit[1], Integer.parseInt(inSplit[2]), daytime , Integer.parseInt(inSplit[6]), Integer.parseInt(inSplit[7]), inSplit[8], memberOnly, getTier(inSplit[10]), fishing, fishingOnly, fishingRod));
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return spawns;
	}
	
	private static Tier getTier(String tierString) {
		if (tierString.equalsIgnoreCase("Common")) {
			return Tier.COMMON;
		}
		if (tierString.equalsIgnoreCase("Uncommon")) {
			return Tier.UNCOMMON;
		}
		if (tierString.equalsIgnoreCase("Rare")) {
			return Tier.RARE;
		}
		return null;
	}
}
