package util;

import sim.util.MutableDouble2D;

/**
 * This class represents the state of an agent which is representable by the special Euclidean group SE(2).
 *
 * The version here can have its value changed
 */

public class MutableSE2
{
  public MutableSE2()
  {
  }

  public MutableSE2(double x, double y, double theta_)
  {
    position.x = x;
    position.y = y;
    theta = theta_;
  }

  public void set(double x, double y, double theta_)
  {
    position.x = x;
    position.y = y;
    theta = theta_;
  }
  
  public void set(MutableSE2 newSE2)
  {
    position.x = newSE2.position.x;
    position.y = newSE2.position.y;
    theta = newSE2.theta;
  }


  public MutableDouble2D getPosition()
  {
    return position;
  }
  
  public void setPosition(MutableDouble2D position_)
  {
    position = position_;
  }

  public double getOrientation()
  {
    return theta;
  }

  public void setOrientation(double theta_)
  {
    theta = theta_;
  }

  // Apply the transformation here to 
  public MutableSE2 postMultiply(MutableSE2 b)
  {
    MutableSE2 newSE2 = new MutableSE2();
    newSE2.position.x = Math.cos(theta) * b.position.x - Math.sin(theta) * b.position.y + position.x;
    newSE2.position.y = Math.sin(theta) * b.position.x + Math.cos(theta) * b.position.y + position.y;
    newSE2.theta = theta + b.theta;

    return newSE2;
  }
  
  public String toString() {
	  return "(x: " + position.x + ", y: " + position.y + ", theta: " + theta + ")";
  }

  private MutableDouble2D position = new MutableDouble2D();
  
  private double theta = 0;
}
