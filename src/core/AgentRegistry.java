package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import objects.Entrance;
import objects.Exit;
import objects.ForkLift;
import objects.Gate;
import objects.TempStorage;
import objects.Truck;
import objects.UnloadingBay;
import objects.WorkSite;

public class AgentRegistry {
	// TODO: a better way to maintain the code?
	
	private List<UnloadingBay> unloadingBays = new ArrayList<UnloadingBay>();

	private List<WorkSite> worksites = new ArrayList<WorkSite>();
	
	private List<Truck> trucks = new ArrayList<Truck>();
	
	private List<Entrance> entrances = new ArrayList<Entrance>();
	
	private List<Exit> exits = new ArrayList<Exit>();
	
	private List<TempStorage> tempStorages = new ArrayList<TempStorage>();
	
	private List<ForkLift> forklifts = new ArrayList<ForkLift>();
	
	private <T extends Agent> boolean add(T new_agent, Collection<T> store) {
		return store.add(new_agent);
	}
	
	private <T extends Agent> void addAll(Collection<T> new_agents, Collection<T> store) {
		store.addAll(new_agents);
	}
	
	private <T extends Agent> T find(int id, List<T> store) {
		for (T agent : store) {
			if (agent.id == id) return agent;
		}
		return null;
	}
	
	public boolean addUnloadingBay(UnloadingBay bay) {
		return add(bay, unloadingBays);
	}
	
	public void addUnloadingBays(List<UnloadingBay> new_bays) {
		addAll(new_bays, unloadingBays);
	}
	
	public UnloadingBay nextEmptyBay() {
		for (UnloadingBay bay : unloadingBays) {
			if (!bay.isOccupied()) {
				return bay;
			}
		}
		// return null if no empty bay is available
		return null;
	}
	
	public UnloadingBay nextOccupiedBay() {
		for (UnloadingBay bay : unloadingBays) {
			if (bay.isOccupied()) {
				return bay;
			}
		}
		// return null if no empty bay is available
		return null;
	}

	public boolean addWorkSite(WorkSite site) {
		return add(site, worksites);
	}
	
	public void addWorkSites(List<WorkSite> new_sites) {
		addAll(new_sites, worksites);
	}

	public WorkSite findWorkSite(int id) {
		return find(id, worksites);
	}
	
	public boolean addTruck(Truck truck) {
		return add(truck, trucks);
	}
	
	public Truck findTruck(int id) {
		return find(id, trucks);
	}
	
	public boolean addEntrance(Entrance entr) {
		return add(entr, entrances);
	}
	
	public void addEntrances(List<Entrance> new_entrs) {
		addAll(new_entrs, entrances);
	}
	
	public Gate findEntrance(int id) {
		return find(id, entrances);
	}
	
	public boolean addExit(Exit exit) {
		return add(exit, exits);
	}
	
	public void addExits(List<Exit> new_exits) {
		addAll(new_exits, exits);
	}
	
	public Gate findExit(int id) {
		return find(id, exits);
	}

	public boolean addTempStorage(TempStorage store) {
		return add(store, tempStorages);
	}
	
	public void addTempStorages(List<TempStorage> new_stores) {
		addAll(new_stores, tempStorages);
	}

	public boolean addForkLift(ForkLift new_forklift) {
		return add(new_forklift, forklifts);
	}
	
	public ForkLift findForkLift(int id) {
		return find(id, forklifts);
	}
}
