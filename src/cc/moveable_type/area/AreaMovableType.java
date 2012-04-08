package cc.moveable_type.area;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Ihc
 * 
 */
public interface AreaMovableType
{
	public Area getArea();

	public void setArea(Area area);

	public Rectangle2D getBound();

	public void setBound(Rectangle2D bound);
}
