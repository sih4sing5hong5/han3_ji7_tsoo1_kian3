package cc.moveable_type.area;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.moveable_type.漢字組建活字;

/**
 * 區塊活字的介面。和<code>ImageMoveableType</code>最大的不同是<code>AreaMovableType</code>
 * 在設定時就讀取字體，處理時要考慮部件的差異，<code>area</code>（區塊活字）記錄目前長寬位置及部件形狀，<code>bound</code>
 * 記錄預計長寬位置。
 * 
 * @author Ihc
 */
public interface AreaMovableType extends 漢字組建活字
{
	/**
	 * 取得區塊活字
	 * 
	 * @return 區塊活字
	 */
	public Area getArea();

	/**
	 * 設定區塊活字
	 * 
	 * @param area
	 *            區塊活字
	 */
	public void setArea(Area area);

	/**
	 * 取得預計長寬位置
	 * 
	 * @return 預計長寬位置
	 */
	public Rectangle2D getBound();

	/**
	 * 設定預計長寬位置
	 * 
	 * @param bound
	 *            預計長寬位置
	 */
	public void setBound(Rectangle2D bound);
}
