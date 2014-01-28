package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.ConstructionSiteState;
import core.Vehicle;

public class ForkLift extends Vehicle{
	
	private Pallet cargo;
	
	private static enum FORKLIFT_STATUS {
		MOVING_TO_BAY, MOVING_TO_SITE, MOVING_TO_TEMP_STORE, AT_BAY, AT_SITE, AT_TEMP_STORE, IDLE
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
			dest_site = cargo.getDestination();
			// Move to site only if the site queue is empty
			if (dest_site.canTakeInCargo()) {
				setDestination(dest_site);
				status = FORKLIFT_STATUS.MOVING_TO_SITE;
			}
			else {
				dest_temp_store = _siteState.agentReg.getUsableTempStorages();
				// FIXME: What if we cannot find a usable temp store?
//				if (dest_temp_store == null) 
				setDestination(dest_temp_store);
			}
		}
		else {
			status = FORKLIFT_STATUS.IDLE;
		}
	}
	
	private void routineAtSite() {
		if (dest_site.canTakeInCargo()) {
			dest_site.takeInCargo(this);
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
				System.out.println(this.toString() + " Arriving " + dest_bay + " @ "+ _siteState.currentTime());
				status = FORKLIFT_STATUS.AT_BAY;
			}
			break;

		case MOVING_TO_SITE:
			if (moveStep()) {
				System.out.println(this.toString() + " Arriving " + dest_site + " @ "+ _siteState.currentTime());
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
