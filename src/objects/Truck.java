package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.joda.time.LocalDateTime;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import util.TimeConvert;
import core.ConstructionSiteState;
import core.Vehicle;

public class Truck extends Vehicle {

	private static final long serialVersionUID = -1875286739196648019L;

	private static enum TRUCK_STATUS {
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
	
	// TODO: truck handling type;
	// TODO: truck type;
	
	// TODO: currently there is no size limit on truck, do we need to?
	private Queue<Pallet> trunk = new LinkedList<Pallet>();
	
	private int trunk_capacity = 5;

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
	
	// loading the truck all in once
	public void load(ArrayList<Pallet> goods) {
		if (goods == null) {
			throw new IllegalArgumentException(toString() + " cannot load null goods!");
		}
		
		for (Pallet pallet : goods) {
			load(pallet);
		}
	}
	
	public void load(Pallet good) {
		if (good == null) {
			throw new IllegalArgumentException(toString() + " cannot load null good!");
		}
		trunk.offer(good);
	}
	
	public void displayTrunk() {
		System.out.println(toString() + " contents:");
		for (Pallet cargo : trunk) {
			System.out.println(cargo);
		}
	}
	
	// unloading the truck one by one
	public boolean unload(ForkLift lift) {
		if (lift == null) {
			throw new IllegalArgumentException(toString() + " cannot take null ForkLift!");
		}
		
		if (!trunk.isEmpty()) {
			lift.loadCargo(trunk.poll());
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setSiteState(ConstructionSiteState site) {
		super.setSiteState(site);
		sim_arriving_time = TimeConvert.CivilT2SimT(_siteState.startTime(), arriving_time);
	}

	@Override
	// add a simple counter display to the site
	// TODO: is there a fancier way to do it?
	public String toString() {
		return super.toString() + "-" + nick_name + " [" + consumption_line.size() + "/" + queue_capacity + "]";
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
	
	private void routineAtBay() {
		// only start moving to the exit if the truck is empty
		if (trunk.isEmpty()) {
			setDestination(exit);
			bay.clear(this);
			status = TRUCK_STATUS.MOVING_TO_EXIT;
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
			routineAtBay();
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
