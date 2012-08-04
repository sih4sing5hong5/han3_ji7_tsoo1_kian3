package cc.moveable_type.area;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * 區塊活字輔助工具，提供一些調整區塊活字常用到的函式。
 * 
 * @author Ihc
 */
public class AreaTool
{
	/**
	 * 將區塊活字位置調整至原點
	 * 
	 * @param area
	 *            區塊活字
	 */
	static public void moveToOrigin(Area area)
	{
		Rectangle2D rectangle2d = area.getBounds2D();
		moveTo(area, -rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	/**
	 * 將區塊活字位置調整至指定位置
	 * 
	 * @param area
	 *            區塊活字
	 * @param x
	 *            指定水平位置
	 * @param y
	 *            指定垂直位置
	 */
	static public void moveTo(Area area, double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		area.transform(affineTransform);
		return;
	}

	/**
	 * 設定區塊活字預計長寬
	 * 
	 * @param rectangle2d
	 *            預計長寬物件
	 * @param width
	 *            指定寬度
	 * @param height
	 *            指定長度
	 */
	static public void adjustSize(Rectangle2D rectangle2d, int width, int height)
	{
		rectangle2d.setRect(rectangle2d.getX(), rectangle2d.getY(), width,
				height);
		return;
	}
}
