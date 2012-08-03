package cc.moveable_type.piece;

import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 活字物件介面。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，<code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * 
 * @author Ihc
 */
public interface PieceMovableType
{
	/**
	 * 取得活字物件
	 * 
	 * @return 活字物件
	 */
	public RectangularArea getPiece();

	/**
	 * 設定活字物件
	 * 
	 * @param rectangularArea
	 *            活字物件
	 */
//	public void setPiece(RectangularArea rectangularArea);
}
