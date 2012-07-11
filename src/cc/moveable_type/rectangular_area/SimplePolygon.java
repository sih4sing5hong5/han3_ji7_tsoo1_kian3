/**
 * 
 */
package cc.moveable_type.rectangular_area;

import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * @author Ihc
 * 
 */
public class SimplePolygon
{
	private Vector<Point2D> apex = new Vector<Point2D>();
	private double circumference = 0.0;
	private double regionSize = 0.0;

	/**
	 * 把裡面頂點資料都清掉
	 */
	public void clear()
	{
		apex.clear();
		circumference = 0.0;
		regionSize = 0.0;
		return;
	}

	/**
	 * 增加一個頂點
	 * 
	 * @param point
	 *            要新增的頂點
	 */
	public void addPoint(Point2D point)
	{
		apex.add(point);
		if (apex.size() > 1)
		{
			Point2D previous = apex.elementAt(apex.size() - 2), last = apex
					.lastElement();
			circumference += previous.distance(last);
			regionSize += previous.getX() * last.getY() - previous.getY()
					* last.getX();
		}
		System.out.println("XD=" + circumference);
		return;
	}

	/**
	 * 增加一個頂點
	 * 
	 * @param x
	 *            新增頂點的x值
	 * @param y
	 *            新增頂點的y值
	 */
	public void addPoint(double x, double y)
	{
		addPoint(new Point2D.Double(x, y));
		return;
	}

	/**
	 * 算出目前多邊形的周長
	 * 
	 * @return 多邊形的周長
	 */
	public double getCircumference()
	{
		if (apex.size() == 0)
			return 0.0;
		return circumference + apex.firstElement().distance(apex.lastElement());
	}

	/**
	 * 算出目前多邊形的面積
	 * 
	 * @return 多邊形的面積
	 */
	public double getRegionSize()
	{
		if (apex.size() == 0)
			return 0.0;
		Point2D first = apex.firstElement(), last = apex.lastElement();
		return -0.5
				* (regionSize + last.getX() * first.getY() - last.getY()
						* first.getX());
	}
}
