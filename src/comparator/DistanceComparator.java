package comparator;

import java.util.Comparator;

import core.DistanceResult;

public class DistanceComparator implements Comparator<DistanceResult>{

	@Override
	public int compare(DistanceResult o1, DistanceResult o2) {
		return o1.getDistance() > o2.getDistance() ? 1 :  o1.getDistance() < o2.getDistance() ? -1 : 0;
	}

}
