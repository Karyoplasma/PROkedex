package core.comparators;

import java.util.Comparator;

import core.LandSpawn;

public class ComparatorLandRouteStringLength implements Comparator<LandSpawn>{

	@Override
	public int compare(LandSpawn o1, LandSpawn o2) {
		if (o1.getRoute().length() > o2.getRoute().length()) {
			return -1;
		}
		if (o1.getRoute().length() < o2.getRoute().length()) {
			return 1;
		}
		return 0;
	}

}
