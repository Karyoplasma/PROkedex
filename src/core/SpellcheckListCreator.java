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

	public static String updateLists() {
		StringBuffer buffer = new StringBuffer("Updating spellcheck lists:\n");
		List<Spawn> allSpawns = new ArrayList<Spawn>();
		allSpawns.addAll(SpawnDumpReader.getAllLandSpawns());
		allSpawns.addAll(SpawnDumpReader.getAllSurfSpawns());
		buffer.append(updatePokemonNames(allSpawns));
		buffer.append(updateMapNames(allSpawns));
		buffer.append(updateItemNames(allSpawns));
		return buffer.toString();
	}

	private static String updateItemNames(List<Spawn> allSpawns) {
		Set<String> itemNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getItem() == null || spawn.getItem().isEmpty()) {
				continue;
			}
			itemNames.add(spawn.getItem().trim());
		}
		Path filePath = Paths.get("resources/items.txt");
		return writeListFile(itemNames, filePath);
	}

	private static String updateMapNames(List<Spawn> allSpawns) {
		Set<String> mapNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getMap() == null || spawn.getMap().isEmpty()) {
				continue;
			}
			mapNames.add(spawn.getMap().trim());
		}
		Path filePath = Paths.get("resources/maps.txt");
		return writeListFile(mapNames, filePath);

	}

	private static String updatePokemonNames(List<Spawn> allSpawns) {
		Set<String> pokemonNames = new HashSet<String>();
		for (Spawn spawn : allSpawns) {
			if (spawn.getPokemon() == null || spawn.getPokemon().isEmpty()) {
				continue;
			}
			pokemonNames.add(spawn.getPokemon().trim());
		}
		Path filePath = Paths.get("resources/pokemon.txt");
		return writeListFile(pokemonNames, filePath);

	}

	private static String writeListFile(Set<String> content, Path filePath) {
		try {
			Files.write(filePath, content);
			return "Successfully updated: " + filePath.toString() + "\n";
		} catch (IOException e) {
			e.printStackTrace();
			return String.format("Error when writing to %s:\n%s", filePath.toString(), e.getMessage());
		}
	}
}
