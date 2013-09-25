package cc.moveable_type.rectangular_area;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * 活字型態。把<code>AreaTool</code>和預設位置大小整合起來，使用時比較方便。把<code>Area</code>
 * 的預計大小，改由繼承的方式重新撰寫<code>RectangularArea</code>，並將物件活字取名為<code>Piece-</code>。
 * 修改Area相關工具，使得Piece-也有相對應工具，並在逐步模組化。
 * 
 * @author Ihc
 */
public interface 活字單元// extends Shape
{
	/**
	 * 取得活字預計位置及大小
	 * 
	 * @return 活字預計位置及大小
	 */
	public Rectangle2D.Double 目標範圍();

	/**
	 * 取得活字圖形實際位置及大小
	 * 
	 * @return 活字圖形實際位置及大小
	 */
	public Rectangle2D.Double 字範圍();

	public void 設字範圍(Rectangle2D 目標);
	/**
	 * 設定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */
	public void 設目標範圍(Rectangle2D 目標);

	/**
	 * 設定活字預計位置
	 * 
	 * @param x
	 *            水平位置
	 * @param y
	 *            垂直位置
	 */
	public void 設目標範圍所在(double x, double y);

	/**
	 * 設定活字預計大小
	 * 
	 * @param width
	 *            寬度
	 * @param height
	 *            高度
	 */
	public void 設目標範圍大細(double width, double height);

	/** 共活字的圖形清掉 */
	public void 圖形重設();

	/**
	 * 將物件重設，並將底下活字物件組合起來
	 * 
	 * @param 活字物件
	 *            合體活字底下的活字物件
	 */
	public void 重設並組合活字(活字單元[] 活字物件);

	/**
	 * 把活字座標移動指定距離
	 * 
	 * @param x
	 *            要移動的水平距離
	 * @param y
	 *            要移動的垂直距離
	 */
	public void 徙(double x, double y);

	/**
	 * 把活字的座標移回原點
	 */
	public void 徙轉原點();

	public void 縮放(AffineTransform 縮放矩陣);

	public void 合併活字(活字單元 活字物件);

	@Deprecated
	public void 減去活字(活字單元 活字物件);
	
	public void 畫佇(Graphics2D 布);

	public Point2DWithVector 揣上低的點();
	
	@Deprecated
	public 平面幾何 目前的字體();
}
