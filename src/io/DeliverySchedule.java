package io;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import core.ConstructionSiteState;

import objects.Truck;

public class DeliverySchedule {
	private File deliverySchedulPath; 
	
	private Truck next_truck;
	
	private Queue<String[]> deliverySchedule = new LinkedList<String[]>();
	
	private static final int schedule_fields = 5;
	
	private void readSchedule() {
		try {
			deliverySchedule.addAll(CSVFile.readCSV(deliverySchedulPath, schedule_fields));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public DeliverySchedule(String path) {
		deliverySchedulPath = new File(path).getAbsoluteFile();
		readSchedule();
	}
	
	// TODO: there is a problem, this will not repeat, the truck can only be exhausted once.
	public boolean hasNext() {
		return (deliverySchedule.peek() != null) ? true : false;
	}
	
	public Truck nextTruck(ConstructionSiteState site) {
		if (!hasNext()) return null;

		String[] line = deliverySchedule.poll();
		try {
			next_truck = new Truck(Integer.parseInt(line[0]), line[1], line[2]);
			next_truck.setEntrance(site.agentReg.findEntrance(Integer.parseInt(line[3])));
			next_truck.setExit(site.agentReg.findExit(Integer.parseInt(line[4])));
			next_truck.setSiteState(site);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		
		return next_truck;
	}
	
}
