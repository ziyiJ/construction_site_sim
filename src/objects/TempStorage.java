package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;

public class TempStorage extends Place {

	private static final long serialVersionUID = 2200694385850267987L;
	
	private LinkedList<Pallet> storage_place = new LinkedList<Pallet>();
	
	private int storage_capacity = 5;

	public TempStorage() {
		this(-1);
	}
	
	public TempStorage(int id) {
		super("TempStorage", id);
		
		// set initial state of this temp storage site
		storage_capacity = 5;
		storage_place.clear();
	}
	
	// set new queue capacity always clears the queue
	public void setStorageCapacity(int new_capacity) {
		storage_capacity = new_capacity;
		storage_place.clear();
	}
	
	public void takeInCargo(ForkLift vehicle) {
		if (vehicle == null) {
			throw new IllegalArgumentException(toString() + " cannot take null ForkLift!");
		}
		
		if (storage_place.size() < storage_capacity) {
			storage_place.add(vehicle.unloadCargo());
		}
		else {
			throw new IllegalAccessError(toString() + " storage capacity breached!");
		}
	}
	
	// check if our storage is full
	public boolean canTakeInCargo() {
		return (storage_place.size() < storage_capacity)? true : false;
	}
	
	// tempstorage only give out cargo to a forkliftfortempsotrage type!
	public void giveOutCargo(ForkLiftForTempStorage vehicle) {
		if (vehicle == null) {
			throw new IllegalArgumentException(toString() + " cannot take null ForkLift!");
		}
	}
	
	public void step(SimState state) {
	}

	// add a simple counter display to the site
	// TODO: is there a fancier way to do it?
	public String toString() {
		return super.toString() + " [" + storage_place.size() + "/" + storage_capacity + "]";
	}	

	public Color getColor() {
		return new Color(200, 100, 10);
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(2);
		super.draw(object, graphics, info);
	}
}
