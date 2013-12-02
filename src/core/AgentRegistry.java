package core;

import java.util.ArrayList;

import objects.Gate;
import objects.TempStorage;
import objects.Truck;
import objects.UnloadingBay;
import objects.WorkSite;

public class AgentRegistry {
	
	private ArrayList<UnloadingBay> unloadingBays = new ArrayList<UnloadingBay>();
	
	private ArrayList<WorkSite> worksites = new ArrayList<WorkSite>();
	
	private ArrayList<Truck> trucks = new ArrayList<Truck>();
	
	private ArrayList<Gate> gates = new ArrayList<Gate>();
	
	private ArrayList<TempStorage> tempStorages = new ArrayList<TempStorage>();
	
	public boolean addUnloadingBay(UnloadingBay bay) {
		if (unloadingBays.contains(bay)) return false;
		return unloadingBays.add(bay);
	}
	
	public void addUnloadingBays(UnloadingBay[] new_bays) {
		for (UnloadingBay bay : new_bays) {
			addUnloadingBay(bay);
		}
	}
	
	public boolean addWorkSite(WorkSite site) {
		if (worksites.contains(site)) return false;
		return worksites.add(site);
	}
	
	public void addWorkSites(WorkSite[] new_sites) {
		for (WorkSite site : new_sites) {
			addWorkSite(site);
		}
	}
	
	public boolean addTruck(Truck truck) {
		if (trucks.contains(truck)) return false; 
		return trucks.add(truck);
	}
	
	public boolean addGate(Gate gate) {
		if (gates.contains(gate)) return false;
		return gates.add(gate);
	}
	
	public void addGates(Gate[] new_gates) {
		for (Gate gate : new_gates) {
			addGate(gate);
		}
	}

	public boolean addTempStorage(TempStorage store) {
		if (tempStorages.contains(store)) return false;
		return tempStorages.add(store);
	}
	
	public void addTempStorages(TempStorage[] new_stores) {
		for (TempStorage store : new_stores) {
			addTempStorage(store);
		}
	}

	public UnloadingBay nextEmpyBay() {
		for (UnloadingBay bay : unloadingBays) {
			if (!bay.occupied()) return bay;
		}
		return null;
	}
	
}
