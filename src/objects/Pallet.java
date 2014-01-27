package objects;

import sim.engine.SimState;
import core.PhysicalAgent;

// TODO: a better inheritance hierarchy?
public class Pallet extends PhysicalAgent {
	
	private enum PALLET_STATUS {
		ON_ROUTE, WAITING, CONSUMED
	}
	
	private PALLET_STATUS status;
	
	private String content;
	
	private WorkSite destination = new WorkSite();
	
	public Pallet(int id, String content) {
		super("Pallet", id);
		this.content = content;
		this.status = PALLET_STATUS.ON_ROUTE;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + content + ")";
	}
	
	public void setDestination(WorkSite new_dest) {
		if (new_dest == null) {
			throw new IllegalArgumentException(toString() + " cannot take null destination!");
		}

		destination = new_dest;
	}
	
	public WorkSite getDestination() {
		if (destination == null) {
			throw new NullPointerException(toString() + " has null destination, CHECK!");
		}

		return destination;
	}
	
	public boolean isConsumed() {
		return (status == PALLET_STATUS.CONSUMED)? true : false;
	}
	
	public void consume(WorkSite ws) {
		if (ws == destination)
			status = PALLET_STATUS.CONSUMED;
	}
	
	public void step(SimState state) {
		// cannot think what a pallet needs to do when been moved :)
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8011300473224814787L;

}
