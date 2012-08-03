package cc.adjusting.bolder;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;

/**
 * 什麼都不產生的筆劃工具。
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
