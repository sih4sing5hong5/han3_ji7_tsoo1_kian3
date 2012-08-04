package cc.moveable_type.image;

import java.awt.Point;

/**
 * 圖片活字的介面。<code>ImageMoveableType</code>
 * 在列印時才讀取字體，處理時不考慮部件的差異，皆視為同樣大小，每個部件當做矩形，記錄預計長寬（<code>region</code>）及位置（
 * <code>position</code>），目前長寬（<code>scaler</code> ）是給合體活字儲存紀錄的。
 * 
 * @author Ihc
 */
public interface ImageMoveableType
{
	/**
	 * 取得預計長寬
	 * 
	 * @return 預計長寬
	 */
	public Point getRegion();

	/**
	 * 設定預計長寬
	 * 
	 * @param region
	 *            預計長寬
	 */
	public void setRegion(Point region);

	/**
	 * 取得預計位置
	 * 
	 * @return 預計位置
	 */
	public Point getPosition();

	/**
	 * 設定預計位置
	 * 
	 * @param position
	 *            預計位置
	 */
	public void setPosition(Point position);

	/**
	 * 取得目前長寬
	 * 
	 * @return 目前長寬
	 */
	public Point getScaler();

	/**
	 * 設定目前長寬
	 * 
	 * @param scaler
	 *            目前長寬
	 */
	public void setScaler(Point scaler);
}
