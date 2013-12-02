package start;

import java.awt.Color;

import javax.swing.JFrame;

import core.ConstructionSiteState;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.continuous.ContinuousPortrayal2D;

public class StarterWithUI extends GUIState {

	public static void main(String[] args) {
		StarterWithUI vid = new StarterWithUI(args);
		Console c = new Console(vid);
		c.setVisible(true);
	}

	public StarterWithUI(String[] args) {
		super(new ConstructionSiteState(System.currentTimeMillis(), args));
	}

	public StarterWithUI(SimState state) {
		super(state);
	}

	public static String getName() {
		return "Construction site simulator";
	}

	public void start() {
		super.start();
		setupPortrayals();
	}

	public void load(SimState state) {
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals() {
		ConstructionSiteState site = (ConstructionSiteState) state;
		
		// tell the portrayals what to portray and how to portray them
		sitePortrayal.setField(site.getArea());
		sitePortrayal.setFrame(Color.blue);

		// reschedule the displayer
		display.reset();
		display.setBackdrop(Color.white);
		// redraw the display
		display.repaint();
	}

	public void init(Controller c) {
		super.init(c);
		display = new Display2D(600, 600, this);
		display.setClipping(false);
		displayFrame = display.createFrame();
		displayFrame.setTitle("Construction Site Display");
		c.registerFrame(displayFrame); // so the frame appears in the "Display"
										// list
		displayFrame.setVisible(true);
		display.attach(sitePortrayal, "Construction Site");
	}

	public void quit() {
		super.quit();
		if (displayFrame != null)
			displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

	public Display2D display;
	public JFrame displayFrame;
	ContinuousPortrayal2D sitePortrayal = new ContinuousPortrayal2D();

}
