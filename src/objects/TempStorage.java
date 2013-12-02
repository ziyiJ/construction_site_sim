package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import core.Place;

public class TempStorage extends Place {

	private static final long serialVersionUID = 2200694385850267987L;

	public TempStorage() {
		this(-1);
	}
	
	public TempStorage(int id) {
		super("TempStorage", id);
	}
	
	public void step(SimState state) {
	}
	
	public Color getColor() {
		return new Color(200, 100, 10);
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		setScale(2);
		super.draw(object, graphics, info);
	}
}
