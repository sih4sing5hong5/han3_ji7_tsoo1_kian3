package cc.adjusting.piece;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;

/**
 * 用來計算shape相關資訊的型態
 * 
 * @author Ihc
 * 
 */
public class ShapeInformation
{
	private double approximativeCircumference, approximativeRegion;

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
		Area area = new Area(shape);//保證他的順逆時針
		double[] coords = new double[6];
		SimplePolygon simplePolygon = new SimplePolygon();
		for (PathIterator pp = area.getPathIterator(null); !pp.isDone(); pp
				.next())
		{
			int type = pp.currentSegment(coords);
			switch (type)
			{
			case PathIterator.SEG_CUBICTO:
				// System.out.println(coords[0] + " " + coords[1]);
				// System.out.println(coords[2] + " " + coords[3]);
				// System.out.println(coords[4] + " " + coords[5]);
				simplePolygon.addPoint(coords[0], coords[1]);
				simplePolygon.addPoint(coords[2], coords[3]);
				simplePolygon.addPoint(coords[4], coords[5]);
				// circumference += Point2D.distance(nowx, nowy, coords[0],
				// coords[1]);
				// circumference += Point2D.distance(coords[0], coords[1],
				// coords[2], coords[3]);
				// circumference += Point2D.distance(coords[2], coords[3],
				// coords[4], coords[5]);
				// nowx = coords[4];
				// nowy = coords[5];
				// System.out.println("cc=" + circumference);
				break;
			case PathIterator.SEG_QUADTO:
				// System.out.println(coords[0] + " " + coords[1]);
				// System.out.println(coords[2] + " " + coords[3]);
				simplePolygon.addPoint(coords[0], coords[1]);
				simplePolygon.addPoint(coords[2], coords[3]);
				// circumference += Point2D.distance(nowx, nowy, coords[0],
				// coords[1]);
				// circumference += Point2D.distance(coords[0], coords[1],
				// coords[2], coords[3]);
				// nowx = coords[2];
				// nowy = coords[3];
				// System.out.println("cc=" + circumference);
				break;
			case PathIterator.SEG_LINETO:
				// System.out.println("now=" + nowx + " " + nowy);
				// System.out.println(coords[0] + " " + coords[1]);
				simplePolygon.addPoint(coords[0], coords[1]);
				// circumference += Point2D.distance(nowx, nowy, coords[0],
				// coords[1]);
				// nowx = coords[0];
				// nowy = coords[1];
				// System.out.println("cc=" + circumference);
				break;
			case PathIterator.SEG_MOVETO:
				// System.out.println(coords[0] + " " + coords[1]);
				simplePolygon.addPoint(coords[0], coords[1]);
				// nowx = coords[0];
				// nowy = coords[1];
				break;
			case PathIterator.SEG_CLOSE:
				// System.out.println("AAAAAAAAAAAAAAAAAAAAAAAa");
				approximativeCircumference += simplePolygon.getCircumference();
				approximativeRegion += simplePolygon.getRegionSize();
				simplePolygon.clear();
			}
			// System.out.println("cs=" + simplePolygon.getCircumference());
		}
	}

	/**
	 * @return the approximativeCircumference
	 */
	public double getApproximativeCircumference()
	{
		return approximativeCircumference;
	}

	/**
	 * @return the approximativeRegion
	 */
	public double getApproximativeRegion()
	{
		return approximativeRegion;
	}
}
