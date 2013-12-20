package core;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.continuous.Continuous2D;
import sim.portrayal.SimplePortrayal2D;

public class Agent extends SimplePortrayal2D implements Steppable {
	public Agent() {
		this("", -1);
	}

	public Agent(String name_, int id_) {
		name = name_;
		id = id_;
	}
	
	public void setID(int id_) {
		id = id_;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name + "-" +Integer.toString(id);
	}
	
	public void setSiteState(ConstructionSiteState site) {
		_siteState = site;
	}
	
	public void step(SimState state) {
	}
	
    /** Ends the Element.  Stops it and removes it from the field. */
	public void end(ConstructionSiteState site) {
		if (stopper != null)
			stopper.stop();
		Continuous2D area = site.getArea();
		area.remove(this);
	}
	
	protected ConstructionSiteState _siteState;

	// The ID of the agent
	protected int id = -1;

	// The name of the agent
	protected String name;
	
    protected Stoppable stopper;

	private static final long serialVersionUID = -5105695998369725761L;
}
