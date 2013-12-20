package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class PhysicalAgent extends Agent {
	private static final long serialVersionUID = 2866787838674156110L;
	
	public double scale = 4;
	public Shape shape; 
    public double orientation;
    public MutableDouble2D velocity = new MutableDouble2D();
    public double angularRate;
    
    /** Rotates (using orientation), translates, and scales the shape as requested, then returns the
    modified shape as an Area. */
	public Area getLocatedArea(double translateX, double translateY,
			double scaleX, double scaleY) {
		AffineTransform transform = new AffineTransform();
		transform.translate(translateX, translateY);
		transform.rotate(orientation);
		if (scaleX != 1.0 && scaleY != 1.0)
			transform.scale(scaleX, scaleY);
		Area area = new Area(shape);
		area.transform(transform);
		return area;
	}
    
    /** Rotates (using orientation), and translates the shape as requested, then returns
    the modified shape as an Area.  Translation is done by centering the shape at the
    Element's location in the field. */
    public Area getLocatedArea(ConstructionSiteState site) {
    	Double2D loc = site.getArea().getObjectLocation(this);
    	return getLocatedArea(loc.x, loc.y, 1.0, 1.0);
    }
    
    public void setPositionAndOrientation(Double2D pos, double orient) {
    	_siteState.getArea().setObjectLocation(this, pos);
    	orientation = orient;
    }
    
    public void setPositionAndOrientation(PhysicalAgent dest) {
    	setPositionAndOrientation(dest.getPosition(), dest.getOrientation());
    }
    
    public Double2D getPosition() {
    	return _siteState.getArea().getObjectLocation(this);
    }
    
    public double getOrientation() {
    	return orientation;
    }
    
	public PhysicalAgent() {
		super();
		shape = new Rectangle.Double(-3,-3,5,5);
	}
	
	public PhysicalAgent(String name, int id) {
		super(name, id);
		shape = new Rectangle.Double(-3,-3,5,5);
	}
	
	public static Portrayal drawSelf() {
		RectanglePortrayal2D image = new RectanglePortrayal2D();
		image.paint = new Color(255, 0, 0);
		image.scale = 4;
		return image;
	}
	
	public Color getColor() {
		return Color.blue;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setScale(double s) {
		scale = s;
	}
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		
		float orig_x = (float) info.draw.x;
		float orig_y = (float) info.draw.y;
		float height = (float) (getScale() * info.draw.height);
		float width = (float) (getScale() * info.draw.width);
		
		Area draw_area = getLocatedArea(orig_x, orig_y, height, width);
		
		graphics.setColor(getColor());
		graphics.drawString(toString(), orig_x - 4 * height, orig_y - 4 * width);
		graphics.draw(draw_area);
		graphics.fill(draw_area);
	}

	// TODO: How to describe object dimensions?
}
