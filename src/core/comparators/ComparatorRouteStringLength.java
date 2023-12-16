package core.comparators;

import java.util.Comparator;
import core.Spawn;

@Deprecated
public class ComparatorRouteStringLength implements Comparator<Spawn>{

	@Override
	public int compare(Spawn o1, Spawn o2) {
		if (o1.getRouteLength() > o2.getRouteLength()) {
			return -1;
		}
		if (o1.getRouteLength() < o2.getRouteLength()) {
			return 1;
		}
		return 0;
	}

}
