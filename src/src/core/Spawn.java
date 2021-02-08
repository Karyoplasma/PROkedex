package core;

public interface Spawn {

	public int getTier();
	
	public int getRouteLength();
	
	public int getPokemonLength();

	public String getRoute();

	public int[] getTime();

	public boolean isFishOnly();

	public boolean isSurfOnly();

	public String getRod();

	public boolean isMs();

	public int getMinLvl();

	public String getItem();

	public int getMaxLvl();

	public String getPokemon();
}
