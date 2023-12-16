package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import comparator.DistanceComparator;
import enums.RequestType;

public class SpellChecker {

	private Set<String> pokemonNames, mapNames, itemNames;

	public SpellChecker() {
		this.pokemonNames = this.recheckPokemonNames();
		this.mapNames = this.recheckMapNames();
		this.itemNames = this.recheckItemNames();

	}

	public HashSet<String> getDictionary(RequestType type) {
		HashSet<String> clone = new HashSet<>();
		switch (type) {
		case POKEMON:
			clone.addAll(pokemonNames);
			break;
		case MAP:
			clone.addAll(mapNames);
			break;
		case ITEM:
			clone.addAll(itemNames);
			break;
		default:

		}
		return clone;
	}

	public RequestType getSetForString(String search) {
		if (this.pokemonNames.contains(search)) {
			return RequestType.POKEMON;
		}
		if (this.mapNames.contains(search)) {
			return RequestType.MAP;
		}
		if (this.itemNames.contains(search)) {
			return RequestType.ITEM;
		}
		return null;
	}

	public int calculateLevenshteinDistance(String request, String target) {
		request = request.toLowerCase();
		target = target.toLowerCase();

		int[][] dp = new int[request.length() + 1][target.length() + 1];

		for (int i = 0; i <= request.length(); i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= target.length(); j++) {
			dp[0][j] = j;
		}
		for (int i = 1; i <= request.length(); i++) {
			for (int j = 1; j <= target.length(); j++) {
				int cost = (request.charAt(i - 1) == target.charAt(j - 1)) ? 0 : 1;

				dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
				if (i > 1 && j > 1 && request.charAt(i - 1) == target.charAt(j - 2)
						&& request.charAt(i - 2) == target.charAt(j - 1)) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + cost);
				}
			}
		}
		return dp[request.length()][target.length()];
	}

	public List<DistanceResult> compareWithLevenshteinDistance(String input, Set<String> dictionary) {
		List<DistanceResult> results = new ArrayList<DistanceResult>();

		for (String word : dictionary) {
			int distance = this.calculateLevenshteinDistance(input, word);
			results.add(new DistanceResult(word, distance));
		}
		Collections.sort(results, new DistanceComparator());
		return results;
	}

	public List<DistanceResult> getResultsWithSmallerDistanceThan(List<DistanceResult> results, int targetDistance) {
		List<DistanceResult> wordsWithDistance = new ArrayList<DistanceResult>();

		for (DistanceResult result : results) {
			if (result.getDistance() < targetDistance) {
				wordsWithDistance.add(result);
			} else {
				break;
			}
		}

		return wordsWithDistance;
	}

	private Set<String> recheckPokemonNames() {
		Paths.get("resources/pokemon.txt");
		return this.readFile(Paths.get("resources/pokemon.txt"));
	}

	private Set<String> recheckMapNames() {
		Paths.get("resources/map.txt");
		return this.readFile(Paths.get("resources/maps.txt"));
	}

	private Set<String> recheckItemNames() {
		Paths.get("resources/item.txt");
		return this.readFile(Paths.get("resources/items.txt"));
	}

	private Set<String> readFile(Path path) {
		try {
			return Files.lines(path).collect(Collectors.toCollection(HashSet::new));
		} catch (IOException e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
}
