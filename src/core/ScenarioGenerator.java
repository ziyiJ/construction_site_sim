package core;

import objects.ForkLift;
import objects.Truck;
import sim.util.Double2D;

public class ScenarioGenerator {

	public static void populatePlaces(ConstructionSiteState site) {
		System.out.println("Generating Site ...");

		// unloading bays
		site.agentReg.addUnloadingBays(ConstructionSiteState.siteLayout.genUnloadingBays(site));
		
		// work sites
		site.agentReg.addWorksites(ConstructionSiteState.siteLayout.genWorkSites(site));

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
		
		// forklifts
		for(int i = 0; i != site.getForkLiftNum(); ++i) {
			ForkLift tmp = new ForkLift(i+1);
			//FIXME: this approach to set simstate is very prone to null pointer exception!!
			tmp.setSiteState(site);
			Double2D tmp_pos = new Double2D(Math.random()*site.getSiteWidth(), 
					Math.random()*site.getSiteHeigh());
			tmp.setPositionAndOrientation(tmp_pos, 0.0);
		}
	}
}
