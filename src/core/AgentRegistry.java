package core;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<Gate> entrances = new ArrayList<Gate>();
	
	private List<Gate> exits = new ArrayList<Gate>();
	
	private List<TempStorage> tempStorages = new ArrayList<TempStorage>();
	
	private <T extends Agent> boolean add(T new_agent, List<T> store) {
		return store.add(new_agent);
	}
	
	private <T extends Agent> void addAll(T[] new_agents, List<T> store) {
		for (T agent : new_agents) {
			add(agent, store);
		}
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
	
	public void addUnloadingBays(UnloadingBay[] new_bays) {
		addAll(new_bays, unloadingBays);
	}
	
	public boolean addWorkSite(WorkSite site) {
		return add(site, worksites);
	}
	
	public void addWorkSites(WorkSite[] new_sites) {
		addAll(new_sites, worksites);
	}
	
	public boolean addTruck(Truck truck) {
		return add(truck, trucks);
	}
	
	public Truck findTruck(int id) {
		return find(id, trucks);
	}
	
	public boolean addEntrance(Gate entr) {
		return add(entr, entrances);
	}
	
	public void addEntrances(Gate[] new_entrs) {
		addAll(new_entrs, entrances);
	}
	
	public Gate findEntrance(int id) {
		return find(id, entrances);
	}
	
	public boolean addExit(Gate exit) {
		return add(exit, exits);
	}
	
	public void addExits(Gate[] new_exits) {
		addAll(new_exits, exits);
	}
	
	public Gate findExit(int id) {
		return find(id, exits);
	}

	public boolean addTempStorage(TempStorage store) {
		return add(store, tempStorages);
	}
	
	public void addTempStorages(TempStorage[] new_stores) {
		addAll(new_stores, tempStorages);
	}

	public UnloadingBay nextEmpyBay() {
		for (UnloadingBay bay : unloadingBays) {
			if (!bay.occupied()) return bay;
		}
		return null;
	}
	
}
