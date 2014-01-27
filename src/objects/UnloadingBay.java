package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Comparator;

import sim.engine.SimState;
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
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	// FIXME: only the agentRegistry can alter the bay state, is this the best way?
//	public boolean occupy(AgentRegistry reg) {
	public boolean occupy(Truck truck) {
		// report fail if is already occupied
		if (isOccupied) return false;
		
		isOccupied = true;
		return true;
	}
	
	// FIXME: only a truck can clear the bay state
	public void clear(Truck truck) {
		isOccupied = false;
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
