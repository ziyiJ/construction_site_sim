package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import core.Vehicle;

public class ForkLift extends Vehicle{
	
	private Pallet cargo;
	
	private static enum FORKLIFT_STATUS {
		MOVING_TO_BAY, MOVING_TO_SITE, AT_BAY, AT_SITE, IDLE
	}
	
	private FORKLIFT_STATUS status;
	
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
