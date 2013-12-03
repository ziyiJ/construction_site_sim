package core;

import java.io.IOException;

import objects.Gate;
import objects.TempStorage;
import objects.Truck;
import objects.UnloadingBay;
import objects.WorkSite;

public class ScenarioGenerator {

	public static void populatePlaces(ConstructionSiteState site) throws IOException {
		System.out.println("Generating Site ...");

		// unloading bays
		site.agentReg.addUnloadingBays(ConstructionSiteState.siteLayout.genUnloadingBays(site));
		
		// work sites
		site.agentReg.addWorkSites(ConstructionSiteState.siteLayout.genWorkSites(site));

		// temp storages
		site.agentReg.addTempStorages(ConstructionSiteState.siteLayout.genTempStorages(site));
		
		// site entrance
		site.agentReg.addEntrances(ConstructionSiteState.siteLayout.genSiteEntrances(site));
		
		// site exit
		site.agentReg.addExits(ConstructionSiteState.siteLayout.genSiteExits(site));
	}

	public static void populateVehicles(ConstructionSiteState site) {
		System.out.println("Generating Vhehicles ...");
		while(ConstructionSiteState.deliverySchedule.hasNext()) {
			Truck tmp = ConstructionSiteState.deliverySchedule.nextTruck(site);
			tmp.schuleArriving();
		}
	}
}
