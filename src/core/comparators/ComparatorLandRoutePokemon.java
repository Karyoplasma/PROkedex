package core.comparators;

import java.util.Comparator;

import core.LandSpawn;

public class ComparatorLandRoutePokemon implements Comparator<LandSpawn>{

	@Override
	public int compare(LandSpawn o1, LandSpawn o2) {
		if (o1.getPokemon().length() > o2.getPokemon().length()) {
			return -1;
		}
		if (o1.getPokemon().length() < o2.getPokemon().length()) {
			return 1;
		}
		return 0;
	}

}
