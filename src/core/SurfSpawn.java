package core;

public class SurfSpawn {
	private String route, pokemon, item, rod;
	private int tier, maxLvl, minLvl;
	private boolean ms, fishOnly, surfOnly;
	private int[] time;
	
	public SurfSpawn(String route, String pokemon, int tier, boolean ms, String item, int minLvl, int maxLvl, String rod, boolean fishOnly, boolean surfOnly, int[] time) {
		this.route = route;
		this.pokemon = pokemon;
		this.tier = tier;
		this.ms = ms;
		this.item = item;
		this.minLvl = minLvl;
		this.maxLvl = maxLvl;
		this.rod = rod;
		this.fishOnly = fishOnly;
		this.surfOnly = surfOnly;
		this.time = time;
	}

	public String getRoute() {
		return route;
	}

	public String getPokemon() {
		return pokemon;
	}

	public String getItem() {
		return item;
	}

	public String getRod() {
		return rod;
	}

	public int getTier() {
		return tier;
	}

	public int getMaxLvl() {
		return maxLvl;
	}

	public int getMinLvl() {
		return minLvl;
	}

	public boolean isMs() {
		return ms;
	}

	public boolean isFishOnly() {
		return fishOnly;
	}

	public boolean isSurfOnly() {
		return surfOnly;
	}

	public int[] getTime() {
		return time;
	}
	
}
