package objects;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;

public class Gate extends Place {
	
	private static final long serialVersionUID = -8892842427534780569L;
	
	private Queue<Truck> queue = new LinkedList<Truck>();
	
	public Gate() {
		this(-1);
	}
	
	public Gate(int id) {
		super("Gate", id);
	}
	
	protected Gate(String name, int id) {
		super(name, id);
	}
	
	// when tuck first arrive at the door it must check in
	public boolean checkIn(Truck t) {
		if (t == null) {
			throw new IllegalArgumentException(toString() + " cannot check-in a null truck!");
		}

		if (queue.contains(t)) return false; 
		t.setPositionAndOrientation(this);
		t.stop(this);
		return queue.offer(t);
	}
	
	// open the gate let the next one go
	public void releaseNext(UnloadingBay dest) {
		// First get the first truck from queue
		Truck next_to_go = queue.poll();
		if (next_to_go != null) { // if we have truck in the queue
			// set the signal in the truck that it can go
			next_to_go.throughGate(this);
			// tell the truck where to go
			dest.designate(next_to_go);
			next_to_go.setDestination(dest);
			next_to_go.setBay(dest);
		}
	}
	
	public void step(SimState state) {
//		System.out.println("Gate checking");
		UnloadingBay bay = _siteState.agentReg.nextEmptyBay();
		if (bay != null) // if we do have an empty bay
			releaseNext(bay);
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(0.5);
		super.draw(object, graphics, info);
	}

}
