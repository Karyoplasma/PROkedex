package core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DumpFileReader {
	Map<String, Collection<LandSpawn>> landSpawnsRoute, landSpawnsPokemon;
	Map<String, Collection<SurfSpawn>> surfSpawnsPokemon;
	Map<String, Collection<SurfSpawn>> surfSpawnsRoute;

	public DumpFileReader() {
		this.landSpawnsRoute = new HashMap<String, Collection<LandSpawn>>();
		this.landSpawnsPokemon = new HashMap<String, Collection<LandSpawn>>();
		this.surfSpawnsRoute = new HashMap<String, Collection<SurfSpawn>>();
		this.surfSpawnsPokemon = new HashMap<String, Collection<SurfSpawn>>();
	}

	private void processLandSpawns() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray ja = (JSONArray) parser.parse(new FileReader("resources/land_spawns.json"));

		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			String map = (String) obj.get("Map");
			String pokemon = (String) obj.get("Pokemon");
			int tier = Integer.parseInt(Long.toString((long) obj.get("Tier")));
			int maxLvl = Integer.parseInt(Long.toString((long) obj.get("MaxLVL")));
			int minLvl = Integer.parseInt(Long.toString((long) obj.get("MinLVL")));
			boolean ms = (boolean) obj.get("MemberOnly");
			String item = (String) obj.get("Item");
			JSONArray temp = (JSONArray) obj.get("Daytime");
			int[] time = new int[] {Integer.parseInt(Long.toString((long) temp.get(0))), Integer.parseInt(Long.toString((long) temp.get(1))), Integer.parseInt(Long.toString((long) temp.get(2)))};
			LandSpawn s = new LandSpawn(map, pokemon, tier, ms, item, minLvl, maxLvl, time);
			if (this.landSpawnsRoute.containsKey(map)) {
				this.landSpawnsRoute.get(map).add(s);
			} else {
				ArrayList<LandSpawn> put = new ArrayList<LandSpawn>();
				put.add(s);
				this.landSpawnsRoute.put(map, put);
				
			}
			if (this.landSpawnsPokemon.containsKey(pokemon)) {
				this.landSpawnsPokemon.get(pokemon).add(s);
			} else {
				ArrayList<LandSpawn> put = new ArrayList<LandSpawn>();
				put.add(s);
				this.landSpawnsPokemon.put(pokemon, put);				
			}
		}
	}
	
	private void processSurfSpawns() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray ja = (JSONArray) parser.parse(new FileReader("resources/surf_spawns.json"));

		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			String map = (String) obj.get("Map");
			String pokemon = (String) obj.get("Pokemon");
			int tier = Integer.parseInt(Long.toString((long) obj.get("Tier")));
			int maxLvl = Integer.parseInt(Long.toString((long) obj.get("MaxLVL")));
			int minLvl = Integer.parseInt(Long.toString((long) obj.get("MinLVL")));
			boolean ms = (boolean) obj.get("MemberOnly");
			String item = (String) obj.get("Item");
			JSONArray temp = (JSONArray) obj.get("Daytime");
			int[] time = new int[] {Integer.parseInt(Long.toString((long) temp.get(0))), Integer.parseInt(Long.toString((long) temp.get(1))), Integer.parseInt(Long.toString((long) temp.get(2)))};
			String rod;
			boolean surfOnly;
			boolean fishOnly;
			if ((long) obj.get("Fishing") == 0L) {
				rod = null;
				surfOnly = true;
			} else {
				rod = (String) obj.get("RequiredRod");
				surfOnly = false;
			}
			if ((long) obj.get("FishingOnly") == 1L) {
				fishOnly = true;
			} else {
				fishOnly = false;
			}
			SurfSpawn s = new SurfSpawn(map, pokemon, tier, ms, item, minLvl, maxLvl, rod, fishOnly, surfOnly, time);
			if (this.surfSpawnsRoute.containsKey(map)) {
				this.surfSpawnsRoute.get(map).add(s);
			} else {
				ArrayList<SurfSpawn> put = new ArrayList<SurfSpawn>();
				put.add(s);
				this.surfSpawnsRoute.put(map, put);
				
			}
			if (this.surfSpawnsPokemon.containsKey(pokemon)) {
				this.surfSpawnsPokemon.get(pokemon).add(s);
			} else {
				ArrayList<SurfSpawn> put = new ArrayList<SurfSpawn>();
				put.add(s);
				this.surfSpawnsPokemon.put(pokemon, put);
				
			}
		}
	}
	
	public void readSpawns() throws FileNotFoundException, IOException, ParseException {
		this.processLandSpawns();
		this.processSurfSpawns();
	}

	public Map<String, Collection<LandSpawn>> getLandSpawnsRoute() {
		return landSpawnsRoute;
	}

	public Map<String, Collection<LandSpawn>> getLandSpawnsPokemon() {
		return landSpawnsPokemon;
	}

	public Map<String, Collection<SurfSpawn>> getSurfSpawnsPokemon() {
		return surfSpawnsPokemon;
	}

	public Map<String, Collection<SurfSpawn>> getSurfSpawnsRoute() {
		return surfSpawnsRoute;
	}
	
}
