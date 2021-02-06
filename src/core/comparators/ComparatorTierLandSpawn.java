package core.comparators;

import java.util.Comparator;

import core.LandSpawn;

public class ComparatorTierLandSpawn implements Comparator<LandSpawn>{

	@Override
	public int compare(LandSpawn o1, LandSpawn o2) {
		if (o1.getTier() < o2.getTier()) {
			return -1;
		}
		if (o1.getTier() > o2.getTier()) {
			return 1;
		}
		return 0;
	}

}
