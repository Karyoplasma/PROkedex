package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpawnChancePredictor {

	private int[] tiersMorning, tiersSurfMorning, tiersDay, tiersSurfDay, tiersNight, tiersSurfNight;
	private boolean isSurfSpawn, isLandSpawn;
	private String target;
	private Collection<Spawn> route;
	
	
	public SpawnChancePredictor(Collection<Spawn> route, String target) {
		this.tiersMorning = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersSurfMorning = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersDay = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersSurfDay = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersNight = new int[] {0,0,0,0,0,0,0,0,0};
		this.tiersSurfNight = new int[] {0,0,0,0,0,0,0,0,0};
		this.target = target;
		this.route = route;
		this.isLandSpawn = false;
		this.isSurfSpawn = false;
		this.analyzeRoute();
	}
	
	private void analyzeRoute() {
		// set flags for surf spawn and/or land spawn
		for (Spawn s : route) {
			if (s.getPokemon().equals(target)) {
				if (s instanceof SurfSpawn) {
					this.isSurfSpawn = true;
				} else {
					this.isLandSpawn = true;
				}
			}
		}
		
		//fill lists
		List<Spawn> landSpawnsMorning = new ArrayList<Spawn>(); 
		List<Spawn> surfSpawnsMorning = new ArrayList<Spawn>();
		List<Spawn> landSpawnsDay = new ArrayList<Spawn>(); 
		List<Spawn> surfSpawnsDay = new ArrayList<Spawn>(); 
		List<Spawn> landSpawnsNight = new ArrayList<Spawn>(); 
		List<Spawn> surfSpawnsNight = new ArrayList<Spawn>(); 
		for (Spawn s: route) {
			if (s instanceof SurfSpawn && this.isSurfSpawn) {
				if (s.isFishOnly()) {
					continue;
				} else {
					if (s.getTime()[0] == 1) {
						surfSpawnsMorning.add(s);
					}
					if (s.getTime()[1] == 1) {
						surfSpawnsDay.add(s);
					}
					if (s.getTime()[2] == 1) {
						surfSpawnsNight.add(s);
					}
				}
			} else {
				if (this.isLandSpawn) {
					if (s.getTime()[0] == 1) {
						landSpawnsMorning.add(s);
					}
					if (s.getTime()[1] == 1) {
						landSpawnsDay.add(s);
					}
					if (s.getTime()[2] == 1) {
						landSpawnsNight.add(s);
					}
				}				
			}
		}
		
		//setup tier arrays
		for (Spawn s : landSpawnsMorning) {
			this.tiersMorning[s.getTier() - 1]++;
		}
		for (Spawn s : surfSpawnsMorning) {
			this.tiersSurfMorning[s.getTier() - 1]++;
		}
		for (Spawn s : landSpawnsDay) {
			this.tiersDay[s.getTier() - 1]++;
		}
		for (Spawn s : surfSpawnsDay) {
			this.tiersSurfDay[s.getTier() - 1]++;
		}
		for (Spawn s : landSpawnsNight) {
			this.tiersNight[s.getTier() - 1]++;
		}
		for (Spawn s : surfSpawnsNight) {
			this.tiersSurfNight[s.getTier() - 1]++;
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
				if (this.tiersSurfMorning[i] != 0) {
					totalWeightingSurf += this.getTierWeighting(i + 1) * this.tiersSurfMorning[i];
				}
			}
			for (Spawn s : this.route) {
				if (s instanceof SurfSpawn && s.getPokemon().equals(this.target) && totalWeightingSurf > 0) {
					if (s.isFishOnly()) {
						ret = -1f;
						continue;
					}
					ret = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingSurf) * 100;
				}
			}
		}
		if (this.isLandSpawn) {
			for (int i = 0; i < 9; i++) {
				if (this.tiersMorning[i] != 0) {
					totalWeightingLand += this.getTierWeighting(i + 1) * this.tiersMorning[i];
				}
			}
				for (Spawn s : this.route) {
					if (!(s instanceof SurfSpawn) && s.getPokemon().equals(this.target) && totalWeightingLand > 0) {
						float temp = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingLand) * 100;
						if (temp < ret) {
							ret = temp;
						}
					}
			}
		}
		if (ret > 100f) {
			return -1f;
		}
		return ret;
	}
	
	
	public float[] getRouteScoresSurf() {
		int totalWeightingSurfMorning = 0;
		int totalWeightingSurfDay = 0;
		int totalWeightingSurfNight = 0;
		float[] ret = new float[] {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, 1f};
		
		if (this.isSurfSpawn) {
			for (int i = 0; i < 9; i++) {
				if (this.tiersSurfMorning[i] != 0) {
					totalWeightingSurfMorning += this.getTierWeighting(i + 1) * this.tiersSurfMorning[i];
				}
				if (this.tiersSurfDay[i] != 0) {
					totalWeightingSurfDay += this.getTierWeighting(i + 1) * this.tiersSurfDay[i];
				}
				if (this.tiersSurfNight[i] != 0) {
					totalWeightingSurfNight += this.getTierWeighting(i + 1) * this.tiersSurfNight[i];
				}
			}
			for (Spawn s : this.route) {
				if (s instanceof SurfSpawn && s.getPokemon().equals(this.target)) {
					if (s.isFishOnly()) {
						continue;
					}
					if (s.getTime()[0] == 1) {
						ret[0] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingSurfMorning) * 100;
					} else {
						ret[0] = 0f;
					}
					if (s.getTime()[1] == 1) {
						ret[1] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingSurfDay) * 100;
					} else {
						ret[1] = 0f;
					}
					if (s.getTime()[2] == 1) {
						ret[2] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingSurfNight) * 100;
					} else {
						ret[2] = 0f;
					}
				}
			}
		}
		this.sanityCheck(ret);
		return ret;
	}
	
	public float[] getRouteScoresLand() {
		int totalWeightingLandMorning = 0;
		int totalWeightingLandDay = 0;
		int totalWeightingLandNight = 0;
		float[] ret = new float[] {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, 0f};
		if (this.isLandSpawn) {
			for (int i = 0; i < 9; i++) {
				if (this.tiersMorning[i] != 0) {
					totalWeightingLandMorning += this.getTierWeighting(i + 1) * this.tiersMorning[i];
				}
				if (this.tiersDay[i] != 0) {
					totalWeightingLandDay += this.getTierWeighting(i + 1) * this.tiersDay[i];
				}
				if (this.tiersMorning[i] != 0) {
					totalWeightingLandNight += this.getTierWeighting(i + 1) * this.tiersNight[i];
				}
			}
			for (Spawn s : this.route) {
				if (!(s instanceof SurfSpawn) && s.getPokemon().equals(this.target)) {
					if (s.getTime()[0] == 1) {
						ret[0] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingLandMorning) * 100;
					} else {
						ret[0] = 0f;
					}
					if (s.getTime()[1] == 1) {
						ret[1] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingLandDay) * 100;
					} else {
						ret[1] = 0f;
					}
					if (s.getTime()[2] == 1) {
						ret[2] = ((float)this.getTierWeighting(s.getTier()) / (float) totalWeightingLandNight) * 100;
					} else {
						ret[2] = 0f;
					}
				}
			}			
		}
		this.sanityCheck(ret);
		return ret;
	}
	
	private void sanityCheck(float[] ret) {
		for (int i = 0; i < 3; i++) {
			if (ret[i] > 100f) {
				ret[i] = 0f;
			}
		}
	}


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.route.size());
		buffer.append("Land Spawn: " + this.isLandSpawn + "; [");
		for (int i : tiersMorning) {
			buffer.append(i).append(",");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append("]; Surf Spawn: " + this.isSurfSpawn + "; [");
		for (int i : tiersSurfMorning) {
			buffer.append(i).append(",");
		}
		buffer.setLength(buffer.length() - 1);
		buffer.append("] -->" );
		return buffer.toString();
	}
}
