package cc.layouttools;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.slf4j.Logger;

import cc.log.IDSGenLogToolpack;
import cc.movabletype.SeprateMovabletype;

/**
 * 區塊活字調整工具。調整<code>PieceMovableType</code>，因為把活字的資訊全部集中在同一個物件上（
 * <code>Piece</code>， <code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>SimplePiece</code>是在設定時兩兩配對後定框，調整時更改部件大小，但無法物件難實作距離貼近或拉開。
 * 
 * @author Ihc
 */
abstract public class SimplePieceAdjuster implements
		ChineseCharacterTypeAdjuster
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;

	/**
	 * 建立物件活字調整工具
	 * 
	 * @param chineseCharacterTypeBolder
	 *            物件活字加寬工具
	 * @param precision
	 *            合併、調整的精細度
	 */
	public SimplePieceAdjuster()
	{
		記錄工具 = new IDSGenLogToolpack().記錄工具(getClass());
	}

	// @Override
	// public void adjustWen(
	// ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	// {
	// PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen)
	// chineseCharacterMovableTypeWen;
	// SeprateMovabletype rectangularArea = pieceMovableTypeWen.getPiece();
	// AffineTransform affineTransform = getAffineTransform(rectangularArea);
	// shrinkPieceByFixingStroke(rectangularArea, affineTransform);
	// return;
	// }
	//
	// @Override
	// public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	// {
	// PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) tzu;
	// SeprateMovabletype rectangularArea = pieceMovableTypeTzu.getPiece();
	// AffineTransform affineTransform = getAffineTransform(rectangularArea);
	// rectangularArea.縮放(affineTransform);
	// for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
	// {
	// PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
	// .getChildren()[i];
	// Rectangle2D childTerritory = child.getPiece().目標範圍();
	// childTerritory.setRect(
	// childTerritory.getX() * affineTransform.getScaleX(),
	// childTerritory.getY() * affineTransform.getScaleY(),
	// childTerritory.getWidth() * affineTransform.getScaleX(),
	// childTerritory.getHeight() * affineTransform.getScaleY());
	// pieceMovableTypeTzu.getChildren()[i].adjust(this);
	// }
	// }

	/**
	 * 比較<code>RectangularArea</code>目前大小（<code>getBounds2D()</code>）和預期大小（
	 * <code>getTerritory()</code>），算出縮放矩陣
	 * 
	 * @param rectangularArea
	 *            計算縮放的目標
	 * @return 相對應的縮放矩陣
	 */
	protected AffineTransform getAffineTransform(SeprateMovabletype rectangularArea)
	{
		Rectangle2D territory = rectangularArea.目標範圍();
		Rectangle2D bounds = rectangularArea.這馬字範圍();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		return affineTransform;
	}
}
