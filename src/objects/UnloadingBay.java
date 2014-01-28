package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import core.Place;

public class UnloadingBay extends Place {
	
	private Truck occupying_truck = null;
	private Truck designated_truck = null;
	
	// note there is a chance the bay is designated but not yet occupied. 
	public static enum BAY_STATUS {
		EMPTY, DESIGNATED, OCCUPIED 
	}
	private BAY_STATUS status = BAY_STATUS.EMPTY;

	public UnloadingBay() {
		this(-1);
	}
	
	public UnloadingBay(int id) {
		super("UnloadingBay", id);
	}
	
	public UnloadingBay(int id, BAY_STATUS initial_state) {
		this(id);
		status = initial_state;
	}
	
	public BAY_STATUS getStatus() {
		return status;
	}
	
	// Note this will return a null pointer if it is not yet occupied!!!!
	public Truck whoIsOccupying() {
		return occupying_truck;
	}
	
	public Truck whoIsDesignated() {
		return designated_truck;
	}
	
	// Assign the bay for a truck to use
	public boolean designate(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException(toString() + " cannot be designated to a null truck!");
		}
		
		// fail to designate either with an assigned designated_truck or status is not empty
		if (designated_truck != null || status != BAY_STATUS.EMPTY) return false;
		
		status = BAY_STATUS.DESIGNATED;
		designated_truck = truck;
		return true;
	}
	
	// let the truck occupied this bay
	public boolean occupy(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException(toString() + " cannot be occupied by a null truck!");
		}

		// report fail if is already occupied
		if (status == BAY_STATUS.OCCUPIED) return false;
		// report fail if incoming truck is different to assigned.
		if (status == BAY_STATUS.DESIGNATED && designated_truck != truck) return false;
		
		status = BAY_STATUS.OCCUPIED;
		occupying_truck = truck;
		return true;
	}
	
	// clear the bay
	public void clear(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException(toString() + " cannot be cleared by a null truck!");
		}

		// only the occupying truck can clear the bay
		if (truck != occupying_truck) {
			throw new IllegalArgumentException(toString() + " cannot be cleared by a different truck!");
		}

		status = BAY_STATUS.EMPTY;
		occupying_truck = null;
		designated_truck = null;
	}
	
	public Color getColor() {
		return Color.green;
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(1);
		super.draw(object, graphics, info);
	}
	
	
	private static final long serialVersionUID = -8032859847481197708L;

}
