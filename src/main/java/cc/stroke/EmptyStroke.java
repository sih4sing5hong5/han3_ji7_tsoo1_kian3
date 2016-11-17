package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;

/**
 * 空白的物件活字的筆劃工具。
 * 
 * @author Ihc
 */
public class EmptyStroke implements Stroke
{
	@Override
	public Shape createStrokedShape(Shape p)
	{
		return new Area();
	}

}
