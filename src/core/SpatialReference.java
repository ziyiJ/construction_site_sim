package core;

import sim.util.MutableDouble2D;

import util.MutableSE2;

// A spatial reference has a position and orientation space. Note that
// these guys can, in general, live in a hierarchy with a dependency
// on a parent. Typically, the relative frame is set and the global
// position is returned.

public interface SpatialReference
{
  public void setPositionAndOrientation(MutableSE2 positionAndOrientation);

  public void setPositionAndOrientation(double x, double y, double theta);

  public void setPosition(MutableDouble2D position);

  public void setOrientation(double theta);
}
