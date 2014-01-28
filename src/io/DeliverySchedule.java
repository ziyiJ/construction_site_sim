package io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import core.ConstructionSiteState;
import objects.Pallet;
import objects.Truck;

public class DeliverySchedule {
	private File deliverySchedulPath; 
	
	private Truck next_truck;
	
	private ArrayList<String[]> deliverySchedule = new ArrayList<String[]>();
	
	private int schedule_index = 0;
	
	private void readSchedule() {
		try {
			deliverySchedule.addAll(CSVFile.readCSV(deliverySchedulPath));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public DeliverySchedule(String path) {
		deliverySchedulPath = new File(path).getAbsoluteFile();
		readSchedule();
		schedule_index = 0;
	}
	
	// FIXME: there is a problem, this will not repeat, the truck can only be exhausted once.
	public boolean hasNext() {
		if (deliverySchedule.size() != schedule_index+1) {
			return true;
		}
		else {
			schedule_index = 0;
			return false;
		}
	}
	
	public Truck nextTruck(ConstructionSiteState site) {
		if (!hasNext()) return null;

		String[] line = deliverySchedule.get(schedule_index);
		schedule_index += 1;
		try {
			// anything other than contents of pallets
			next_truck = new Truck(Integer.parseInt(line[0]), line[1], line[2]);
			next_truck.setEntrance(site.agentReg.findEntrance(Integer.parseInt(line[3])));
			next_truck.setExit(site.agentReg.findExit(Integer.parseInt(line[4])));
			next_truck.setSiteState(site);
			
			// constructing and loading pallets
			int pallet_num = Integer.parseInt(line[7]);
			for(int i = 0; i != pallet_num; ++i) {
				Pallet tmp = new Pallet(Integer.parseInt(line[7 + 3*i + 1]),
						line[7 + 3*i + 3]);
				tmp.setDestination(site.agentReg.findWorkSite(Integer.parseInt(line[7 + 3*i + 2])));
				
				next_truck.load(tmp);
			}
			next_truck.displayTrunk();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		
		return next_truck;
	}
	
}
