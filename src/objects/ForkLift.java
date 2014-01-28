package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.activity.InvalidActivityException;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.ConstructionSiteState;
import core.Vehicle;

public class ForkLift extends Vehicle{
	
	private Pallet cargo;
	
	private static enum FORKLIFT_STATUS {
		MOVING_TO_BAY, MOVING_TO_SITE, AT_BAY, AT_SITE, IDLE
	}
	
	private FORKLIFT_STATUS status;
	
	private UnloadingBay dest_bay;
	private WorkSite dest_site;
	private TempStorage dest_temp_store;
	
	public ForkLift() {
	}
	
	public ForkLift(int id) {
		super("ForkLift", id);
		this.status = FORKLIFT_STATUS.IDLE;
	}
	
	public Pallet unloadCargo() {
		if (cargo == null) {
			throw new NullPointerException(toString() + " has null cargo, CHECK!");
		}
		return cargo;
	}
	
	public void loadCargo(Pallet new_cargo) {
		if (new_cargo == null) {
			throw new IllegalArgumentException(name + " cannot take null as cargo!");
		}
		
		cargo = new_cargo;
	}
	
	private void routineIdle() {
		UnloadingBay dest = _siteState.agentReg.nextOccupiedBay();
		if (dest != null) {
			setDestination(dest);
			dest_bay = dest;
			status = FORKLIFT_STATUS.MOVING_TO_BAY;
		}
	}
	
	private void routineAtBay() {
		Truck truck = dest_bay.whoIsOccupying();
		
		// if we can get a cargo from the truck, start moving to dest
		// FIXME: there is a chance the truck will be null, find a better solution!!
		if(truck != null && truck.unload(this)) {
			// check if the site queue is full
			dest_site = cargo.getDestination();
			setDestination(dest_site);
			status = FORKLIFT_STATUS.MOVING_TO_SITE;
		}
		else {
			status = FORKLIFT_STATUS.IDLE;
		}
	}
	
	// FIXME: a prettier way?
	private void routineAtSite() {
		if (dest_site.canTakeInCargo()) {
			try {
				dest_site.takeInCargo(this);
			}
			catch (InvalidActivityException e) {
				return;
			}
			status = FORKLIFT_STATUS.IDLE;
		}
	}
	
	@Override
	public void setSiteState(ConstructionSiteState state) {
		super.setSiteState(state);
		// kick it moving
		this._siteState.schedule.scheduleRepeating(this);
	}
	
	@Override
	public void step(SimState state) {
		switch (status) {
		case MOVING_TO_BAY:
			if (moveStep()) {
				System.out.println(this.toString() + " Arrived @ "+ _siteState.currentTime());
				status = FORKLIFT_STATUS.AT_BAY;
			}
			break;

		case MOVING_TO_SITE:
			if (moveStep()) {
				System.out.println(this.toString() + " Arrived @ "+ _siteState.currentTime());
				status = FORKLIFT_STATUS.AT_SITE;
			}
			break;

		case AT_BAY:
			routineAtBay();
			break;

		case AT_SITE:
			routineAtSite();
			break;

		case IDLE:
			routineIdle();
			break;

		default:
			break;
		}
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(0.2);
		super.draw(object, graphics, info);
	}

	public Color getColor() {
		return Color.magenta;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9150381447004088811L;

}
