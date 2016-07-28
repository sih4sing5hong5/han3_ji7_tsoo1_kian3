package cc.stroketool;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;

/**
 * 用來計算shape相關資訊的型態
 * 
 * @author Ihc
 */
public class ShapeAnalyst
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
	 * 建立一個相關資訊物件
	 * 
	 * @param shape
	 *            要計算資訊的shape
	 */
	public ShapeAnalyst(Shape shape)
	{
		this(new Area(shape));// Area保證他的順逆時針
	}

	/**
	 * 建立一個相關資訊物件
	 * 
	 * @param area
	 *            要計算資訊的area
	 */
	public ShapeAnalyst(Area area)
	{
		approximativeCircumference = 0.0;
		approximativeRegion = 0.0;
		double[] controlPoint = new double[6];
		SimplePolygon simplePolygon = new SimplePolygon();
		int i=0;
		for (PathIterator pathIterator = area.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			int type = pathIterator.currentSegment(controlPoint);
			System.out.println(i+" main=" + type + " " + controlPoint[0] + " "
					+ controlPoint[1] + " " + controlPoint[2] + " "
					+ controlPoint[3] + " " + controlPoint[4] + " "
					+ controlPoint[5]);
			switch (type)
			{
			case PathIterator.SEG_CUBICTO:
				simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				simplePolygon.addPoint(controlPoint[4], controlPoint[5]);
				break;
			case PathIterator.SEG_QUADTO:
				simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				break;
			case PathIterator.SEG_LINETO:
				simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				break;
			case PathIterator.SEG_MOVETO:
				simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				break;
			case PathIterator.SEG_CLOSE:
				simplePolygon.finish();
				approximativeCircumference += simplePolygon.getCircumference();
				approximativeRegion += simplePolygon.getRegionSize();
				simplePolygon.clear();
				System.out.println("closer");
				break;
			}
			i++;
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
