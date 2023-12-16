package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellcheckListCreator {
	
	private SpellcheckListCreator() {
		
	}
	
	public static void updateLists() {
		List<Spawn> allSpawns = new ArrayList<Spawn>();
		allSpawns.addAll(SpawnDumpReader.getAllLandSpawns());
		allSpawns.addAll(SpawnDumpReader.getAllSurfSpawns());
		updatePokemonNames(allSpawns);
		updateMapNames(allSpawns);
		updateItemNames(allSpawns);
	}

	private static void updateItemNames(List<Spawn> allSpawns) {
		Set<String> itemNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getItem() == null || spawn.getItem().isEmpty()) {
				continue;
			}
			itemNames.add(spawn.getItem().trim());
		}
		Path filePath = Paths.get("resources/items.txt");
		writeListFile(itemNames, filePath);
	}
	
	
	private static void updateMapNames(List<Spawn> allSpawns) {
		Set<String> mapNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getMap() == null || spawn.getMap().isEmpty()) {
				continue;
			}
			mapNames.add(spawn.getMap().trim());
		}
		Path filePath = Paths.get("resources/maps.txt");
		writeListFile(mapNames, filePath);
		
	}

	private static void updatePokemonNames(List<Spawn> allSpawns) {
		Set<String> pokemonNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getPokemon() == null || spawn.getPokemon().isEmpty()) {
				continue;
			}
			pokemonNames.add(spawn.getPokemon().trim());
		}
		Path filePath = Paths.get("resources/pokemon.txt");
		writeListFile(pokemonNames, filePath);
		
	}
	
	private static void writeListFile(Set<String> content, Path filePath) {
		 try {
	            Files.write(filePath, content);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void main(String[] args) {
		SpellcheckListCreator.updateLists();
	}
	
}
