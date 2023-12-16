package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepelChecker {
	private final List<Spawn> allSpawns;

	public RepelChecker(List<Spawn> allSpawns) {
		this.allSpawns = allSpawns;
	}

	public Map<String, List<Spawn>> expandResults(List<Spawn> results) {
		Map<String, List<Spawn>> expandedSpawns = new HashMap<String, List<Spawn>>();
		String key = "";
		SpawnManager allSpawnsManager = new SpawnManager(allSpawns);

		for (Spawn spawn : results) {
			key = spawn.getMap().replace(" ", "");
			List<Spawn> routeSpawns = allSpawnsManager.filterByMap(spawn.getMap());
			SpawnManager manager = new SpawnManager(routeSpawns);
			manager = new SpawnManager(manager.filterBySpawnType(spawn instanceof SurfSpawn));
			for (int i = 0; i < 3; i++) {
				if (spawn.getDaytime()[i] == 1) {
					List<Spawn> daytimeSpawn = manager.filterByDaytime(i);
					List<Spawn> temp = new ArrayList<Spawn>();
					int[] daytime = new int[] { 0, 0, 0 };
					daytime[i] = 1;
					for (Spawn s : daytimeSpawn) {
						Spawn clone = new Spawn(s);
						clone.setDaytime(daytime);
						temp.add(clone);
					}
					expandedSpawns.put(key + this.getKeySuffix(i), temp);
				}
			}
		}
		return expandedSpawns;
	}

	public List<Spawn> checkRepel(String targetPokemon, List<Spawn> results) {
		List<Spawn> repelSpawns = new ArrayList<Spawn>();
		Map<String, List<Spawn>> expandedSpawns = this.expandResults(results);
		for (List<Spawn> route : expandedSpawns.values()) {
			int modeMaxLvl = this.getMaxLvlMode(route);
			SpawnManager routeManager = new SpawnManager(route);
			List<Spawn> filtered = routeManager.filterByMinLvl(modeMaxLvl);
			for (Spawn s : filtered) {
				if (s.getPokemon().equals(targetPokemon)) {
					repelSpawns.add(s);
				}
			}
		}
		return this.collapseSpawns(repelSpawns);
	}

	private List<Spawn> collapseSpawns(List<Spawn> repelSpawns) {
		List<Spawn> collapsed = new ArrayList<Spawn>();
		Map<String, Spawn> temp = new HashMap<String, Spawn>();
		for (Spawn s : repelSpawns) {
			if (temp.containsKey(s.getMap())) {
				Spawn current = temp.get(s.getMap());
				int[] newDaytime = this.combineDaytime(current.getDaytime(), s.getDaytime());
				current.setDaytime(newDaytime);
			} else {
				temp.put(s.getMap(), s);
			}
		}
		for (Spawn spawn : temp.values()) {
			collapsed.add(spawn);
		}
		return collapsed;
	}

	private int[] combineDaytime(int[] daytime, int[] daytime2) {
		int[] result = new int[3];

		for (int i = 0; i < 3; i++) {
			result[i] = daytime[i] | daytime2[i];
		}

		return result;
	}

	private int getMaxLvlMode(List<Spawn> route) {
		Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		int maxFrequency = 0;
		for (Spawn spawn : route) {
			frequency.put(spawn.getMaxLvl(), frequency.getOrDefault(spawn.getMaxLvl(), 0) + 1);
			maxFrequency = Math.max(maxFrequency, frequency.get(spawn.getMaxLvl()));
		}
		int mode = -1;
		for (Spawn spawn : route) {
			if (frequency.get(spawn.getMaxLvl()) == maxFrequency) {
				return spawn.getMaxLvl();
			}
		}
		return mode;
	}

	private String getKeySuffix(int i) {
		switch (i) {
		case 0:
			return "Morning";
		case 1:
			return "Day";
		case 2:
			return "Night";
		default:
			return null;
		}
	}
}
