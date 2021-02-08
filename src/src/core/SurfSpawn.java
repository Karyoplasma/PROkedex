package core;

public class SurfSpawn extends LandSpawn {
	private String rod;
	private boolean fishOnly, surfOnly;
	
	public SurfSpawn(String route, String pokemon, int tier, boolean ms, String item, int minLvl, int maxLvl, String rod, boolean fishOnly, boolean surfOnly, int[] time) {
		super(route, pokemon, tier, ms, item, minLvl, maxLvl, time);
		this.rod = rod;
		this.fishOnly = fishOnly;
		this.surfOnly = surfOnly;
	}

	public String getRod() {
		return rod;
	}

	public boolean isFishOnly() {
		return fishOnly;
	}

	public boolean isSurfOnly() {
		return surfOnly;
	}

}
