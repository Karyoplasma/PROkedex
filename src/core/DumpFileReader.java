package core;

import java.io.BufferedReader;
import java.io.File;
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
	private Map<String, Collection<Spawn>> spawnsRoute, spawnsPokemon, spawnsItems;
	private List<String> allowedRoutes;
	
	public DumpFileReader() {
		this.spawnsRoute = new HashMap<String, Collection<Spawn>>();
		this.spawnsPokemon = new HashMap<String, Collection<Spawn>>();		
		this.spawnsItems = new HashMap<String, Collection<Spawn>>();
		this.allowedRoutes = new ArrayList<String>();
	}

	
	public void processSpawns() throws FileNotFoundException, IOException, ParseException {
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
			if (this.spawnsRoute.containsKey(map)) {
				this.spawnsRoute.get(map).add(s);
			} else {
				Collection<Spawn> put = new ArrayList<Spawn>();
				put.add(s);
				this.spawnsRoute.put(map, put);
				
			}
			if (this.spawnsPokemon.containsKey(pokemon)) {
				this.spawnsPokemon.get(pokemon).add(s);
			} else {
				ArrayList<Spawn> put = new ArrayList<Spawn>();
				put.add(s);
				this.spawnsPokemon.put(pokemon, put);				
			}
			if (item != null) {
				if (this.spawnsItems.containsKey(item)) {
					this.spawnsItems.get(item).add(s);
				} else {
					ArrayList<Spawn> put = new ArrayList<Spawn>();
					put.add(s);
					this.spawnsItems.put(item, put);
				}
			}
		}
		
		ja = (JSONArray) parser.parse(new FileReader("resources/surf_spawns.json"));

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
			if (this.spawnsRoute.containsKey(map)) {
				this.spawnsRoute.get(map).add(s);
			} else {
				ArrayList<Spawn> put = new ArrayList<Spawn>();
				put.add(s);
				this.spawnsRoute.put(map, put);
				
			}
			if (this.spawnsPokemon.containsKey(pokemon)) {
				this.spawnsPokemon.get(pokemon).add(s);
			} else {
				ArrayList<Spawn> put = new ArrayList<Spawn>();
				put.add(s);
				this.spawnsPokemon.put(pokemon, put);
				
			}
			if (item != null) {
				if (this.spawnsItems.containsKey(item)) {
					this.spawnsItems.get(item).add(s);
				} else {
					ArrayList<Spawn> put = new ArrayList<Spawn>();
					put.add(s);
					this.spawnsItems.put(item, put);
				}
			}
		}
	}
	
	public void readPermissableRoutes() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("resources/routeList.txt")));
		String in = reader.readLine();
		while ((in = reader.readLine()) != null) {
			if (in.startsWith(";")) {
				continue;
			}
			this.allowedRoutes.add(in);
		}
		reader.close();
	}
	
	public List<String> getPermissableRoutes(){
		return this.allowedRoutes;
	}
	
	public Map<String, Collection<Spawn>> getSpawnsRoute() {
		return spawnsRoute;
	}

	public Map<String, Collection<Spawn>> getSpawnsPokemon() {
		return spawnsPokemon;
	}	
	
	public Map<String, Collection<Spawn>> getSpawnsItem() {
		return spawnsItems;
	}
	
	public static void main(String[] args) {
		DumpFileReader r = new DumpFileReader();
		try{
			r.processSpawns();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
