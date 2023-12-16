package comparator;

import java.util.Comparator;

import core.Spawn;

public class SpawnRarityComparator implements Comparator<Spawn> {

	@Override
	public int compare(Spawn o1, Spawn o2) {
		return o1.getTier().compareTo(o2.getTier());
	}

}
