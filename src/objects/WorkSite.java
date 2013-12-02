package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;

public class WorkSite extends Place {
	
	public WorkSite() {
		this(-1);
	}
	
	public WorkSite(int id) {
		super("WorkSite", id);
	}
	
	public void step(SimState state) {
	}
	
	public Color getColor() {
		return Color.red;
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(1.5);
		super.draw(object, graphics, info);
	}

	private static final long serialVersionUID = -2999232895886277903L;

}
