package core;

import util.MutableSE2;

public class Vehicle extends PhysicalAgent {
	
	private static final long serialVersionUID = -4093060929846122507L;
	
	private MutableSE2 destAndOrient;
	
	private MutableSE2 posAndOrient;
	
	private double speed; 
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(String name, int id) {
		super(name, id);
	}
	

}
