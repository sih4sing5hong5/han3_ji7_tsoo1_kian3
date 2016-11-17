package cc.stroke;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;

/**
 * 實心的基本筆劃工具。
 * 
 * @author Ihc
 */
public class SolidBasicStroke implements Stroke
{
	/** 所採用的基本筆劃工具 */
	private BasicStroke basicStroke;

	/**
	 * 建立實心的基本筆劃工具。
	 * 
	 * @param width
	 *            筆劃加寬寬度
	 */
	public SolidBasicStroke(double width)
	{
		basicStroke = new BasicStroke((float) width);
	}

	@Override
	public Shape createStrokedShape(Shape p)
	{
		Area area = new Area(p);
		area.add(new Area(basicStroke.createStrokedShape(p)));
		return area;
	}

}
