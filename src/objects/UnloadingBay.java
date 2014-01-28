package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Comparator;

import sim.portrayal.DrawInfo2D;
import core.Place;

public class UnloadingBay extends Place {
	
	public static Comparator<UnloadingBay> UnloadingBayComparator 
	= new Comparator<UnloadingBay>() {

		@Override
		public int compare(UnloadingBay o1, UnloadingBay o2) {
			if (o1.isOccupied() == o2.isOccupied())
				return (o1.getID() - o2.getID());
			else 
				return (o1.isOccupied())? -1 : 1;
		}
	};
	
	private Truck occupying_truck;

	public UnloadingBay() {
		this(-1);
	}
	
	public UnloadingBay(int id) {
		super("UnloadingBay", id);
	}
	
	public UnloadingBay(int id, boolean initial_state) {
		this(id);
		isOccupied = initial_state;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	// Note this will return a null pointer if it is not yet occupied!!!!
	public Truck whoIsOccupying() {
		return occupying_truck;
	}
	
	// Interaction with a truck
	public boolean occupy(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException(toString() + " cannot be occupied by a null truck!");
		}

		// report fail if is already occupied
		if (isOccupied) return false;
		
		isOccupied = true;
		occupying_truck = truck;
		return true;
	}
	
	public void clear(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException(toString() + " cannot be cleared by a null truck!");
		}
		if (truck != occupying_truck) {
			throw new IllegalArgumentException(toString() + " cannot be cleared by a different truck!");
		}

		isOccupied = false;
		// TODO: is set to null the best way to do it?
		occupying_truck = null;
	}
	
	public Color getColor() {
		return Color.green;
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(1);
		super.draw(object, graphics, info);
	}
	
	private boolean isOccupied = false;
	
	private static final long serialVersionUID = -8032859847481197708L;

}
