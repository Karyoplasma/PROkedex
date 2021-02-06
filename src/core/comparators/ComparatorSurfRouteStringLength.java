package core.comparators;

import java.util.Comparator;

import core.SurfSpawn;

public class ComparatorSurfRouteStringLength  implements Comparator<SurfSpawn>{

	@Override
	public int compare(SurfSpawn o1, SurfSpawn o2) {
		if (o1.getRoute().length() > o2.getRoute().length()) {
			return -1;
		}
		if (o1.getRoute().length() < o2.getRoute().length()) {
			return 1;
		}
		return 0;
	}

}
