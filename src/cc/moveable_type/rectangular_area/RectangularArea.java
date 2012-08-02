/**
 * 
 */
package cc.moveable_type.rectangular_area;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * 活字型態。把AreaTool和預設位置大小整合起來，使用時比較方便。
 * 
 * @author Ihc
 * 
 */
public class RectangularArea extends Area
{
	private Rectangle2D.Double territory;

	/**
	 * 建立一個空的活字。
	 */
	public RectangularArea()
	{
		territory = new Rectangle2D.Double();
	}

	/**
	 * 建立一個空的活字，並用<code>Rectangle2D</code>指定活字預計位置及大小
	 * 
	 * @param territory
	 *            預計位置及大小
	 */
	public RectangularArea(Rectangle2D territory)
	{
		super();
		setTerritory(territory);
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字。
	 * 
	 * @param s
	 *            字塊的形狀
	 */
	public RectangularArea(Shape s)
	{
		super(s);
		setTerritory(getBounds2D());
	}

	/**
	 * 建立一個和<code>RectangularArea</code>相同的活字。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 */
	public RectangularArea(RectangularArea rectangularArea)
	{
		super(rectangularArea);
		setTerritory(rectangularArea.getTerritory());
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字， 並用<code>Rectangle2D</code>指定目標位置及大小。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 * @param territory
	 *            預計位置及大小
	 */
	public RectangularArea(RectangularArea rectangularArea,
			Rectangle2D territory)
	{
		super(rectangularArea);
		setTerritory(territory);
	}

	/**
	 * 把活字座標移回原點
	 */
	public void moveToOrigin()
	{
		Rectangle2D rectangle2d = this.getBounds2D();
		moveTo(-rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	/**
	 * 把活字座標移至指點座標
	 * 
	 * @param x
	 *            要移動的水平距離
	 * @param y
	 *            要移動的垂直距離
	 */

	public void moveTo(double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		this.transform(affineTransform);
		return;
	}

	/**
	 * 設定活字預計位置及大小
	 * 
	 * @param territory
	 *            預計位置及大小
	 */

	public void setTerritory(Rectangle2D territory)
	{
		this.territory = new Rectangle2D.Double(territory.getX(),
				territory.getY(), territory.getWidth(), territory.getHeight());
		return;
	}

	/**
	 * 設定活字預計位置
	 * 
	 * @param x
	 *            水平位置
	 * @param y
	 *            垂直位置
	 */

	public void setTerritoryPosition(double x, double y)
	{
		territory.setRect(x, y, territory.getWidth(), territory.getHeight());
		return;
	}

	/**
	 * 設定活字預計大小
	 * 
	 * @param width
	 *            寬度
	 * @param height
	 *            高度
	 */

	public void setTerritoryDimension(double width, double height)
	{
		territory.setRect(territory.getX(), territory.getY(), width, height);
		return;
	}

	/**
	 * 取得活字預計位置及大小
	 * 
	 * @return 圖形預計位置及大小
	 */
	public Rectangle2D.Double getTerritory()
	{
		return territory;
	}
}
