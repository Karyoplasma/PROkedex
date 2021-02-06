package core.comparators;

import java.util.Comparator;

import core.SurfSpawn;

public class ComparatorTierSurfSpawn implements Comparator<SurfSpawn>{

	@Override
	public int compare(SurfSpawn o1, SurfSpawn o2) {
		if (o1.getTier() < o2.getTier()) {
			return -1;
		}
		if (o1.getTier() > o2.getTier()) {
			return 1;
		}
		return 0;
	}

}
