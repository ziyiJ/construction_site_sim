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
	
	private boolean is_exit = false;

	public Gate() {
		this(-1);
	}
	
	public Gate(int id) {
		super("Gate", id);
	}
	
	public Gate(int id, boolean gate_is_exit) {
		this(id);
		is_exit = gate_is_exit;
	}
	
	public boolean checkIn(Truck t) {
		if (queue.contains(t)) return false; 
		t.setPositionAndOrientation(this.getPositionAndOrientation());
		return queue.offer(t);
	}
	
	public Truck letThrough() {
		return queue.poll();
	}
	
	public void step(SimState state) {
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(0.5);
		super.draw(object, graphics, info);
	}

}
