package core;

public class Place extends PhysicalAgent {
	
	public Place() {
		super();
	}
	
	public Place(String name, int id) {
		super(name, id);
	}
	
	public void startWorking() {
		stopper = _siteState.schedule.scheduleRepeating(this);
	}
	
	// Start working at a specified rate
	public void startWorking(double working_interval) {
		stopper = _siteState.schedule.scheduleRepeating(this, working_interval);
	}
	
	public void stopWorking() {
		stopper.stop();
	}

	private static final long serialVersionUID = 672720478911944181L;
}
