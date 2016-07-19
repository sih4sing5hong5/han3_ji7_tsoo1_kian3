package cc.stroketool;

import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * 用來計算多邊形的資訊。其中頂點的順序按照<code>Area</code>為主，順時鐘代表圖形實心，逆時鐘代表圖形挖空。
 * 
 * @author Ihc
 */
public class SimplePolygon
{
	/**
	 * 依序記錄每個頂點位置
	 */
	private Vector<Point2D> apex = new Vector<Point2D>();
	/**
	 * 目前頂點間的長度（不包含頭尾）
	 */
	private double circumference = 0.0;
	/**
	 * 目前多邊形的面積（不包含行列式的最後一行）
	 */
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
	 * 把第一個加到最後，讓行列式和距離算完。
	 */
	public void finish()
	{
		if (apex.size() != 0 && !apex.firstElement().equals(apex.lastElement()))
		{
			addPoint(apex.firstElement());
		}
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
