package cc.moveable_type.area;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Ihc
 * 
 */
public class AreaTool
{
	static public void moveToOrigin(Area area)
	{
		Rectangle2D rectangle2d = area.getBounds2D();
		moveTo(area, -rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	static public void moveTo(Area area, double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		area.transform(affineTransform);
		return;
	}

	static public void adjustSize(Rectangle2D rectangle2d, int width, int height)
	{
		rectangle2d.setRect(rectangle2d.getX(), rectangle2d.getY(), width,
				height);
		return;
	}
}
