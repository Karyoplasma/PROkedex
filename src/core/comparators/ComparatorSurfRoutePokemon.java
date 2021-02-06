package core.comparators;

import java.util.Comparator;

import core.SurfSpawn;

public class ComparatorSurfRoutePokemon implements Comparator<SurfSpawn>{

	@Override
	public int compare(SurfSpawn o1, SurfSpawn o2) {
		if (o1.getPokemon().length() > o2.getPokemon().length()) {
			return -1;
		}
		if (o1.getPokemon().length() < o2.getPokemon().length()) {
			return 1;
		}
		return 0;
	}

}
