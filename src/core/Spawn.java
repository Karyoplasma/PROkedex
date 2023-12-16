package core;

import enums.Tier;

public class Spawn {
	private String map, pokemon, item;
	private int dexID, minLvl, maxLvl;
	private int[] daytime;
	private boolean memberOnly;
	private Tier tier;

	public Spawn(String map, String pokemon, int dexID, int[] daytime, int minLvl, int maxLvl, String item,
			boolean memberOnly, Tier tier) {
		this.map = map;
		this.pokemon = pokemon;
		this.item = item;
		this.dexID = dexID;
		this.minLvl = minLvl;
		this.maxLvl = maxLvl;
		this.daytime = daytime;
		this.memberOnly = memberOnly;
		this.tier = tier;
	}

	public Spawn(Spawn spawn) {
		this.map = spawn.map;
		this.pokemon = spawn.pokemon;
		this.item = spawn.item;
		this.dexID = spawn.dexID;
		this.minLvl = spawn.minLvl;
		this.maxLvl = spawn.maxLvl;
		this.daytime = spawn.daytime.clone();
		this.memberOnly = spawn.memberOnly;
		this.tier = spawn.tier;
	}

	public String getMap() {
		return map;
	}

	public String getPokemon() {
		return pokemon;
	}

	public String getItem() {
		return item;
	}

	public int getDexID() {
		return dexID;
	}

	public int getMinLvl() {
		return minLvl;
	}

	public int getMaxLvl() {
		return maxLvl;
	}

	public int[] getDaytime() {
		return daytime;
	}

	public boolean isMemberOnly() {
		return memberOnly;
	}

	public Tier getTier() {
		return tier;
	}

	public String getDaytimeString() {
		int spawnTime = 0;
		spawnTime += this.daytime[2];
		spawnTime += 2 * this.daytime[1];
		spawnTime += 4 * this.daytime[0];
		switch (spawnTime) {
		case 1:
			return "N";
		case 2:
			return "D";
		case 3:
			return "D/N";
		case 4:
			return "M";
		case 5:
			return "M/N";
		case 6:
			return "M/D";
		case 7:
			return "M/D/N";
		default:
			return "No spawn time";
		}
	}

	public String getArea() {
		if (this instanceof SurfSpawn) {
			if (((SurfSpawn) this).isFishingOnly()) {
				return "Fish";
			}
			return "Surf";
		}
		return "Land";
	}

	public String getMS() {
		if (this.memberOnly) {
			return "Yes";
		}
		return "No";
	}

	public String getLevel() {
		return String.format("%d-%d", this.minLvl, this.maxLvl);
	}

	public String getItemData() {
		if (this.item == null) {
			return "-";
		}
		return this.item.isEmpty() ? "-" : this.item;
	}

	public void setDaytime(int[] daytime) {
		this.daytime = daytime;
	}

}
