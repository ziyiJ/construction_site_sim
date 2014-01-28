package core;

import sim.util.Double2D;

public class Vehicle extends PhysicalAgent {
	
	private static final long serialVersionUID = -4093060929846122507L;
	
	protected Double2D destination;
	protected double destOrientation;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(String name, int id) {
		super(name, id);
	}
	
	public void setDestination(Double2D dest)
	{
		destination = dest;
	}
	
	public void setDestination(PhysicalAgent dest) {
		destination = dest.getPosition();
		destOrientation = dest.orientation;
	}
	
	// return true if we have arrived
	public boolean moveStep() {
		// TODO: take orientation into account
		
		// Work out the distance to the destination
		Double2D delta = destination.subtract(getPosition());
//		System.out.println(this.toString() + "has " + delta.length() + " to go."); // Debug
	    
	    // If this is below a small value, we have arrived, return true
	    if (delta.length() < 4) return true;
	    
	    // FIXME: a better model allow different speed for different vehicle class
	    // update the speed of vehicle
	    velocity.setTo(delta.x * 0.004, delta.y * 0.004);;
	    
	    // FIXME: avoidance?
	    
	    // FIXME: orientation?
//		orientation += angularRate;
	    // update the vehicle location
		Double2D location = _siteState.getArea().getObjectLocation(this);
		_siteState.getArea().setObjectLocation(this, location.add(new Double2D(velocity)));
	    
		return false;
	}
	
}
