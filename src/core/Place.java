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
	
	public void stopWorking() {
		stopper.stop();
	}

	private static final long serialVersionUID = 672720478911944181L;
}
