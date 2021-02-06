package core;

public class LandSpawn {
	private String route, pokemon, item;
	private int tier, maxLvl, minLvl;
	private boolean ms;
	private int[] time;
	
	public LandSpawn(String route, String pokemon, int tier, boolean ms, String item, int minLvl, int maxLvl, int[] time) {
		this.route = route;
		this.pokemon = pokemon;
		this.tier = tier;
		this.ms = ms;
		this.item = item;
		this.maxLvl = maxLvl;
		this.minLvl = minLvl;
		this.time = time;
	}

	public int[] getTime() {
		return time;
	}
	public int getMaxLvl() {
		return maxLvl;
	}

	public int getMinLvl() {
		return minLvl;
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

	public int getTier() {
		return tier;
	}

	public boolean isMs() {
		return ms;
	}
	
	@Override
	public String toString() {
		return String.format("%s\t%s\tTier: %d\tMS: %s\t%s", route, pokemon, tier, ms ? "Yes" : "No", (item == null) ? "-" : item);
	}
}
