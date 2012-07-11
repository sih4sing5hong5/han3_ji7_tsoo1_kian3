/**
 * 
 */
package cc.moveable_type.rectangular_area;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * 把AreaTool和裡面的bound全部整合起來，末來使用時比較方便
 * 
 * @author Ihc
 * 
 */
public class RectangularArea extends Area
{
	private Rectangle2D.Double wantedTerritory;

	/**
	 * 預設建構子
	 */
	public RectangularArea()
	{
		wantedTerritory= new Rectangle2D.Double();
	}

	/**
	 * @param s
	 *            the Shape from which the area is constructed
	 */
	public RectangularArea(Shape s)
	{
		super(s);
		wantedTerritory = new Rectangle2D.Double(s.getBounds2D().getX(), s
				.getBounds2D().getY(), s.getBounds2D().getWidth(), s
				.getBounds2D().getHeight());
	}

	/**
	 * 把圖形移回原點
	 */
	public void moveToOrigin()
	{
		Rectangle2D rectangle2d = this.getBounds2D();
		moveTo(-rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	/**
	 * 把圖形移至指點座標
	 */

	public void moveTo(double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		this.transform(affineTransform);
		return;
	}

	/**
	 * 調整圖形的預計位置
	 */
	public void setTerritoryPosition(double x, double y)
	{
		wantedTerritory.setRect(x, y, wantedTerritory.getWidth(),
				wantedTerritory.getHeight());
		return;
	}

	/**
	 * 調整圖形的預計大小
	 */
	public void setTerritoryDimension(double width, double height)
	{
		wantedTerritory.setRect(wantedTerritory.getX(), wantedTerritory.getY(),
				width, height);
		return;
	}

	/**
	 * @return 圖形預計區域
	 */
	public Rectangle2D.Double getTerritory()
	{
		return wantedTerritory;
	}
}
