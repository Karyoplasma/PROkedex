package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpawnChancePredictor {

	private int[] tiers;
	private int[] tiersSurf;
	private boolean isSurfSpawn;
	private boolean isLandSpawn;
	private String target;
	private Collection<Spawn> route;
	
	
	public SpawnChancePredictor(Collection<Spawn> route, String target) {
		this.tiers = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersSurf = new int[] {0,0,0,0,0,0,0,0,0};
		this.target = target;
		this.route = route;
		this.isLandSpawn = false;
		this.isSurfSpawn = false;
		List<Spawn> landSpawnsTemp = new ArrayList<Spawn>(); 
		List<Spawn> surfSpawnsTemp = new ArrayList<Spawn>(); 
		for (Spawn s : route) {
			if (s.getPokemon().equals(target)) {
				if (s instanceof SurfSpawn) {
					this.isSurfSpawn = true;
				} else {
					this.isLandSpawn = true;
				}
			}
			if (s instanceof SurfSpawn) {
				surfSpawnsTemp.add(s);
			} else {
				landSpawnsTemp.add(s);
			}
		}
		for (Spawn s : landSpawnsTemp) {
			this.tiers[s.getTier() - 1]++;
		}
		for (Spawn s : surfSpawnsTemp) {
			this.tiersSurf[s.getTier() - 1]++;
		}
	}
	
	private int getTierWeighting(int tier) {
		switch (tier) {
			case 1:
				return 10000;
			case 2:
				return 3400;
			case 3:
				return 2700;
			case 4:
				return 1900;
			case 5:
				return 1225;
			case 6:
				return 625;
			case 7:
				return 300;
			case 8:
				return 250;
			case 9:
				return 175;
			default: return 0;
		}
	}
	
	public float getRouteScore() {
		int totalWeightingSurf = 0;
		int totalWeightingLand = 0;
		float ret = Float.MAX_VALUE;
		if (this.isSurfSpawn) {
			for (int i = 0; i < 9; i++) {
				if (this.tiersSurf[i] != 0) {
					totalWeightingSurf += this.getTierWeighting(i + 1) * this.tiersSurf[i];
				}
			}
			for (Spawn s : this.route) {
				if (s instanceof SurfSpawn && s.getPokemon().equals(this.target)) {
					ret = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingSurf) * 100;
				}
			}
		}
		if (this.isLandSpawn) {
			for (int i = 0; i < 9; i++) {
				if (this.tiers[i] != 0) {
					totalWeightingLand += this.getTierWeighting(i + 1) * this.tiers[i];
				}
			}
				for (Spawn s : this.route) {
					if (!(s instanceof SurfSpawn) && s.getPokemon().equals(this.target)) {
						float temp = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingLand) * 100;
						if (temp < ret) {
							ret = temp;
						}
					}
			}
		}
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Land Spawn: " + this.isLandSpawn + "; [");
		for (int i : tiers) {
			buffer.append(i).append(",");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append("]; Surf Spawn: " + this.isSurfSpawn + "; [");
		for (int i : tiersSurf) {
			buffer.append(i).append(",");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append("] -->" );
		return buffer.toString();
	}
}
