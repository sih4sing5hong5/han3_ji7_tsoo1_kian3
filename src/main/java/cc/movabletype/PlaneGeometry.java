package cc.movabletype;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import cc.stroketool.PathTravel;
import cc.stroketool.Point2DWithVector;
import cc.layouttools.FindLowestPoint;
import cc.stroketool.ControlPointVisit;

/**
 * 活字型態。把<code>AreaTool</code>和預設位置大小整合起來，使用時比較方便。把<code>Area</code>
 * 的預計大小，改由繼承的方式重新撰寫<code>RectangularArea</code>，並將物件活字取名為<code>Piece-</code>。
 * 修改Area相關工具，使得Piece-也有相對應工具，並在逐步模組化。
 * 
 * @author Ihc
 */
public class PlaneGeometry extends Area // implements 活字單元
{
	/**
	 * 活字預計位置及大小
	 */
	private Rectangle2D.Double 目標;

	/**
	 * 建立一個空的活字。
	 */
	public PlaneGeometry()
	{
		目標 = new Rectangle2D.Double();
	}

	/**
	 * 建立一個空的活字，並用<code>Rectangle2D</code>指定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */
	@Deprecated
	public PlaneGeometry(Rectangle2D 目標)
	{
		super();
		設目標範圍(目標);
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字。
	 * 
	 * @param s
	 *            字塊的形狀
	 */
	public PlaneGeometry(Shape s)
	{
		super(s);
		設目標範圍(字範圍());
	}

	/**
	 * 建立一個和<code>RectangularArea</code>相同的活字。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 */
	public PlaneGeometry(PlaneGeometry rectangularArea)
	{
		super(rectangularArea);
		設目標範圍(rectangularArea.目標範圍());
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字， 並用<code>Rectangle2D</code>指定目標位置及大小。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 * @param 目標
	 *            預計位置及大小
	 */
	public PlaneGeometry(PlaneGeometry rectangularArea, Rectangle2D 目標)
	{
		super(rectangularArea);
		設目標範圍(目標);
	}

	/**
	 * 取得活字預計位置及大小
	 * 
	 * @return 圖形預計位置及大小
	 */

	public Rectangle2D.Double 目標範圍()
	{
		return 目標;
	}

	public Rectangle2D.Double 字範圍()
	{
		Rectangle.Double 範圍 = new Rectangle.Double();
		範圍.setRect(super.getBounds2D());
		return 範圍;
	}

	/**
	 * 設定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */

	public void 設目標範圍(Rectangle2D 目標)
	{
		this.目標 = new Rectangle2D.Double(目標.getX(), 目標.getY(), 目標.getWidth(),
				目標.getHeight());
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

	public void 設目標範圍所在(double x, double y)
	{
		目標.setRect(x, y, 目標.getWidth(), 目標.getHeight());
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

	public void 設目標範圍大細(double width, double height)
	{
		目標.setRect(目標.getX(), 目標.getY(), width, height);
		return;
	}

	public void 圖形重設()
	{
		super.reset();
		return;
	}

	/**
	 * 將物件重設，並將底下活字物件組合起來
	 * 
	 * @param 活字物件
	 *            合體活字底下的活字物件
	 */
	public void 重設並組合活字(PlaneGeometry[] 活字物件)
	{
		圖形重設();
		for (PlaneGeometry 活字物件單元 : 活字物件)
		{
			合併活字(活字物件單元);
		}
		return;
	}

	/**
	 * 把活字座標移動指定距離
	 * 
	 * @param x
	 *            要移動的水平距離
	 * @param y
	 *            要移動的垂直距離
	 */

	public void 徙(double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		this.縮放(affineTransform);
		return;
	}

	/**
	 * 把活字座標移回原點
	 */

	public void 徙轉原點()
	{
		Rectangle2D rectangle2d = this.字範圍();
		徙(-rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	public void 縮放(AffineTransform 縮放矩陣)
	{
		super.transform(縮放矩陣);
		return;
	}

	public void 合併活字(PlaneGeometry 活字物件)
	{
		add(活字物件);
	}

	public void 減去活字(PlaneGeometry 活字物件)
	{
		subtract(活字物件);
	}

	public void 畫佇(Graphics2D 布)
	{
		布.draw(this);
		return;
	}

	public Point2DWithVector 揣上低的點()
	{
		FindLowestPoint 記錄 = new FindLowestPoint();
		PathTravel pathTravel = new PathTravel(new ControlPointVisit(記錄));
		pathTravel.travelOn(new GeneralPath(this));
		return 記錄.取得最低點();
	}
}
