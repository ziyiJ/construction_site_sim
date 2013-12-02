package objects;

import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

import sim.engine.SimState;
import util.TimeConvert;

import core.ConstructionSiteState;
import core.Vehicle;

public class Truck extends Vehicle {

	private static final long serialVersionUID = -1875286739196648019L;

	private enum TRUCK_STATUS {
		ARRIVING, QUEUING_AT_ENTRANCE, MOVING_TO_BAY, AT_BAY, MOVING_TO_EXIT, QUEUING_AT_EXIT, LEFT
	}

	private TRUCK_STATUS status;
	
	private Gate arriving_gate;

	private Gate exiting_gate;

	private String nick_name;

	private LocalDateTime arriving_time;

	private double sim_arriving_time;

	public Truck() {
		this(-1, "", "");
	}

	public Truck(int id, String nick_name, String time_str) {
		super("Truck", id);
		this.nick_name = nick_name;
		this.arriving_time = LocalDateTime.parse(time_str);
		this.status = TRUCK_STATUS.ARRIVING;
	}

	@Override
	public void setSiteState(ConstructionSiteState site) {
		super.setSiteState(site);
		sim_arriving_time = TimeConvert.CivilT2SimT(_siteState.startTime(), arriving_time);
	}

	@Override
	public String toString() {
		return super.toString() + "-" + nick_name;
	}

	public LocalDateTime arrivedAt() {
		return arriving_time;
	}

	public void schuleArriving() {
		_siteState.schedule.scheduleOnce(sim_arriving_time, this);
	}

	@Override
	public void step(SimState state) {
		switch (status) {
		case ARRIVING:
			System.out.println(this.toString() + "Arriving at " + _siteState.currentTime());
			break;

		case QUEUING_AT_ENTRANCE:
			
			break;

		case MOVING_TO_BAY:
			
			break;

		case AT_BAY:
			
			break;

		case MOVING_TO_EXIT:
			
			break;

		case QUEUING_AT_EXIT:
			
			break;

		case LEFT:
			
			break;

		default:
			break;
		}
	}

	public void init() {
	}

}
