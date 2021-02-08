package core.comparators;

import java.util.Comparator;
import core.Spawn;

public class ComparatorTier implements Comparator<Spawn>{

	@Override
	public int compare(Spawn o1, Spawn o2) {
		if (o1.getTier() < o2.getTier()) {
			return -1;
		}
		if (o1.getTier() > o2.getTier()) {
			return 1;
		}
		return 0;
	}

}
