package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;

public class UnloadingBay extends Place {
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
	
	public void step(SimState state) {
		
	}
	
	public boolean occupied() {
		return isOccupied;
	}
	
	public Color getColor() {
		return Color.green;
	}
	
	// only a truck can set the occupied state
	public boolean occupy(Truck truck) {
		// report fail if is already occupied
		if (isOccupied) return false;
		
		isOccupied = true;
		return true;
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(1);
		super.draw(object, graphics, info);
	}
	
	private boolean isOccupied = false;
	
	private static final long serialVersionUID = -8032859847481197708L;

}
