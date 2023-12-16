package core;

import java.util.ArrayList;
import java.util.List;

public class SpawnManager {
	private List<Spawn> spawns;

    public SpawnManager(List<Spawn> spawns) {
    	this.spawns = spawns;
    }

    public List<Spawn> filterByPokemon(String pokemon) {
    	List<Spawn> filtered = new ArrayList<Spawn>();
        for (Spawn spawn : this.spawns) {
            if (spawn.getPokemon().equalsIgnoreCase(pokemon)) {
                filtered.add(spawn);
            }
        }
        return filtered;
    }

    public List<Spawn> filterByMap(String map) {
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
            if (spawn.getMap().equalsIgnoreCase(map)) {
                filtered.add(spawn);
            }
        }
    	return filtered;
    }

    public List<Spawn> filterMemberOnly() {
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
            if (!spawn.isMemberOnly()) {
                filtered.add(spawn);
            }
        }
    	return filtered;
    }
    
    public List<Spawn> filterFishingOnly() {
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
     		if (!(spawn instanceof SurfSpawn)) {
     			filtered.add(spawn);
    			continue;
    		}
            if (!((SurfSpawn) spawn).isFishingOnly()) {
                filtered.add(spawn);
            }
        }
    	return filtered;
    }
    
    public List<Spawn> filterByDaytime(int daytimeIndex){
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
    		if (spawn.getDaytime()[daytimeIndex] == 1) {
    			filtered.add(spawn);
    		}
    	}
    	return filtered;
    }
    
    public List<Spawn> filterBySpawnType(boolean isSurfSpawn){
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
    		if (!((spawn instanceof SurfSpawn) ^ isSurfSpawn)) {
    			filtered.add(spawn);
    		}
    	}
    	return filtered;
    }
    
    public List<Spawn> filterByItem(String item) {
    	List<Spawn> filtered = new ArrayList<Spawn>();
    	for (Spawn spawn : this.spawns) {
    		if (spawn.getItem().equalsIgnoreCase(item)) {
    			filtered.add(spawn);
    		}
    	}
    	return filtered;
	}
    
    public List<Spawn> getSpawns() {
		return this.spawns;
	}
}
