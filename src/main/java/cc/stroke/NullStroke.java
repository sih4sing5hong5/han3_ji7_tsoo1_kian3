package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;

/**
 * 保留原本圖形的筆劃工具。
 * 
 * @author Ihc
 */
public class NullStroke implements Stroke
{
	@Override
	public Shape createStrokedShape(Shape p)
	{
		return new Area(p);
	}

}
