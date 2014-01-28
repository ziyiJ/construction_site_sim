package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

import javax.activity.InvalidActivityException;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;


// FIXME: is there a way to remove the duplicate code in tempstorage and worksites?

public class WorkSite extends Place {
	
	private Queue<Pallet> consumption_line = new LinkedList<Pallet>();
	
	private int queue_capacity = 5;
	
	private void consumeCargo() {
		assert consumption_line.peek() != null;
		
		// remove the consumed cargo from the queue
		Pallet to_be_cosumed = consumption_line.poll();
		// mark it as consumed
		to_be_cosumed.consume(this);
	}
	
	public WorkSite() {
		this(-1);
	}
	
	public WorkSite(int id) {
		super("WorkSite", id);
		
		// set initial state of this worksite
		queue_capacity = 5;
		consumption_line.clear();
	}
	
	// set new queue capacity always clears the queue
	public void setQueueCapacity(int new_capacity) {
		queue_capacity = new_capacity;
		consumption_line.clear();
	}
	
	public void takeInCargo(ForkLift vehicle) throws InvalidActivityException {
		if (vehicle == null) {
			throw new IllegalArgumentException(toString() + " cannot take null ForkLift!");
		}
		
		if (consumption_line.size() < queue_capacity) {
			consumption_line.add(vehicle.unloadCargo());
		}
		else {
			throw new InvalidActivityException(toString() + " queue capacity breached!");
		}
	}
	
	// check if our queue is full
	public boolean canTakeInCargo() {
		return (consumption_line.size() < queue_capacity)? true : false;
	}
	
	public void step(SimState state) {
		// as long as we still have cargo, keep on consuming!
		if (!consumption_line.isEmpty())
			consumeCargo();
	}
	
	public Color getColor() {
		return Color.red;
	}
	
	// add a simple counter display to the site
	// TODO: is there a fancier way to do it?
	public String toString() {
		return super.toString() + " [" + consumption_line.size() + "/" + queue_capacity + "]";
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(1.5);
		super.draw(object, graphics, info);
	}

	private static final long serialVersionUID = -2999232895886277903L;

}
