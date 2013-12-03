package core;

import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class Vehicle extends PhysicalAgent {
	
	private static final long serialVersionUID = -4093060929846122507L;
	
	private MutableDouble2D destination;
	private double destOrientation;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(String name, int id) {
		super(name, id);
	}
	
	public void setDestination(MutableDouble2D dest)
	{
		destination = dest;
	}
	
	// return true if we have arrived
	public boolean moveStep() {
		// TODO: take orientation into account
		
		// Work out the distance to the destination
		double dist = destination.distance(_siteState.getArea().getObjectLocation(this));
	    
	    // If this is below a small value, we have arrived, return true
	    if (dist < 4) return true;
	    
	    // TODO: a better model?
	    // update the speed of vehicle
	    velocity.multiply(destination, 0.02);
	    
	    // TODO: avoidance?
	    
//		orientation += angularRate;
	    // update the vehicle location
		Double2D location = _siteState.getArea().getObjectLocation(this);
		_siteState.getArea().setObjectLocation(this, location.add(new Double2D(velocity)));
	    
		return false;
	}
	
}
