package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import org.joda.time.LocalDateTime;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import util.TimeConvert;
import core.ConstructionSiteState;
import core.Vehicle;

public class Truck extends Vehicle {

	private static final long serialVersionUID = -1875286739196648019L;

	private enum TRUCK_STATUS {
		ARRIVING, QUEUING_AT_ENTRANCE, MOVING_TO_BAY, AT_BAY, MOVING_TO_EXIT, QUEUING_AT_EXIT, LEFT
	}

	private TRUCK_STATUS status;
	
	private Gate entrance;

	private Gate exit;
	
	private UnloadingBay bay;
	
	private boolean canGo = false;

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
	
	public void setEntrance(Gate gate) {
		entrance = gate;
	}

	public void setExit(Gate gate) {
		exit = gate;
	}
	
	public void setBay(UnloadingBay dest) {
		bay = dest;
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
	
	public void stop(Gate gate) {
		canGo = false;
	}
	
	public void throughGate(Gate gate) {
		canGo = true;
	}
	
	private void routineArriving() {
		System.out.println(this.toString() + " Arriving at " + _siteState.currentTime());
		entrance.checkIn(this);
		stopper = _siteState.schedule.scheduleRepeating(this);

		status = TRUCK_STATUS.QUEUING_AT_ENTRANCE;
	}
	
	private void routineQueuingAtEntrance() {
		System.out.println(this.toString() + "  Queuing at  " + entrance.toString());
		if (canGo) {
			System.out.println(this.toString() + " On route to " + bay.toString());
			bay.occupy(this);
			status = TRUCK_STATUS.MOVING_TO_BAY;
		}
	}

	@Override
	public void step(SimState state) {
		switch (status) {
		case ARRIVING:
			routineArriving();
			break;

		case QUEUING_AT_ENTRANCE:
			routineQueuingAtEntrance();
			break;

		case MOVING_TO_BAY:
			if (moveStep()) {
				System.out.println(this.toString() + " Arrived @ "+ _siteState.currentTime());
				status = TRUCK_STATUS.AT_BAY;
			}
			break;

		case AT_BAY:
			setDestination(exit);
			bay.clear(this);
			status = TRUCK_STATUS.MOVING_TO_EXIT;
			break;

		case MOVING_TO_EXIT:
			if (moveStep()) {
				System.out.println(this.toString() + " Arrived @ "+ _siteState.currentTime());
				status = TRUCK_STATUS.QUEUING_AT_EXIT;
			}
			break;

		case QUEUING_AT_EXIT:
			stopper.stop();
			_siteState.getArea().remove(this);
			break;

		case LEFT:
			
			break;

		default:
			break;
		}
	}
	
	public void init() {
	}

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(0.4);
		super.draw(object, graphics, info);
	}

	public Color getColor() {
		return Color.orange;
	}
}
