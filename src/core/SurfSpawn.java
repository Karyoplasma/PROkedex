package core;

import enums.Tier;

public class SurfSpawn extends Spawn {
	
	private boolean fishing, fishingOnly;
	private String requiredRod;
	
	public SurfSpawn(String map, String pokemon, int dexID, int[] daytime, int minLvl, int maxLvl, String item, 
			boolean memberOnly, Tier tier, boolean fishing, boolean fishingOnly, String requiredRod) {
		super(map, pokemon, dexID, daytime, minLvl, maxLvl, item, memberOnly, tier);
		this.fishing = fishing;
		this.fishingOnly = fishingOnly;
		this.requiredRod = requiredRod;
	}

	public boolean isFishing() {
		return fishing;
	}

	public boolean isFishingOnly() {
		return fishingOnly;
	}

	public String getRequiredRod() {
		return requiredRod;
	}

}
