package cc.moveable_type.rectangular_area;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;

/**
 * 用來計算shape相關資訊的型態
 * 
 * @author Ihc
 */
public class ShapeInformation
{

	/**
	 * 目前累積的大致周長
	 */
	private double approximativeCircumference;
	/**
	 * 目前累積的大致面積
	 */
	private double approximativeRegion;

	/**
	 * 建立一個物件
	 * 
	 * @param shape
	 *            要計算資訊的shape
	 */
	public ShapeInformation(Shape shape)
	{
		approximativeCircumference = 0.0;
		approximativeRegion = 0.0;
		Area area = new Area(shape);// 保證他的順逆時針
		double[] coords = new double[6];
		SimplePolygon simplePolygon = new SimplePolygon();
		for (PathIterator pp = area.getPathIterator(null); !pp.isDone(); pp
				.next())
		{
			int type = pp.currentSegment(coords);
			switch (type)
			{
			case PathIterator.SEG_CUBICTO:
				simplePolygon.addPoint(coords[0], coords[1]);
				simplePolygon.addPoint(coords[2], coords[3]);
				simplePolygon.addPoint(coords[4], coords[5]);
				break;
			case PathIterator.SEG_QUADTO:
				simplePolygon.addPoint(coords[0], coords[1]);
				simplePolygon.addPoint(coords[2], coords[3]);
				break;
			case PathIterator.SEG_LINETO:
				simplePolygon.addPoint(coords[0], coords[1]);
				break;
			case PathIterator.SEG_MOVETO:
				simplePolygon.addPoint(coords[0], coords[1]);
				break;
			case PathIterator.SEG_CLOSE:
				approximativeCircumference += simplePolygon.getCircumference();
				approximativeRegion += simplePolygon.getRegionSize();
				simplePolygon.clear();
				break;
			}
		}
	}

	/**
	 * 取得目前累積的大致周長
	 * 
	 * @return 目前累積的大致周長
	 */
	public double getApproximativeCircumference()
	{
		return approximativeCircumference;
	}

	/**
	 * 取得目前累積的大致面積
	 * 
	 * @return 目前累積的大致面積
	 */
	public double getApproximativeRegion()
	{
		return approximativeRegion;
	}
}
