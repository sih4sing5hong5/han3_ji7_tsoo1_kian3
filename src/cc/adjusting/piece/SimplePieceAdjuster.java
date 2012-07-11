/**
 * 
 */
package cc.adjusting.piece;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class SimplePieceAdjuster implements ChineseCharacterTypeAdjuster
{
	/**
	 * (non-Javadoc)
	 * 
	 * @see cc.adjusting.ChineseCharacterTypeAdjuster#adjustWen(cc.moveable_type.
	 *      ChineseCharacterMovableTypeWen)
	 */
	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		RectangularArea rectangularArea = pieceMovableTypeWen.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		rectangularArea.transform(affineTransform);
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cc.adjusting.ChineseCharacterTypeAdjuster#adjustTzu(cc.moveable_type.
	 *      ChineseCharacterMovableTypeTzu)
	 */
	@Override
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) tzu;
		RectangularArea rectangularArea = pieceMovableTypeTzu.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		rectangularArea.transform(affineTransform);
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
					.getChildren()[i];
			Rectangle2D childTerritory = child.getPiece().getTerritory();
			childTerritory.setRect(
					childTerritory.getX() * affineTransform.getScaleX(),
					childTerritory.getY() * affineTransform.getScaleY(),
					childTerritory.getWidth() * affineTransform.getScaleX(),
					childTerritory.getHeight() * affineTransform.getScaleY());
			pieceMovableTypeTzu.getChildren()[i].adjust(this);
		}
	}

	/**
	 * 比較rectangularArea目前大小和預期大小，算出縮放矩陣
	 * 
	 * @param rectangularArea
	 *            計算縮放的目標
	 * @return 相對應的縮放矩陣
	 */
	private AffineTransform getAffineTransform(RectangularArea rectangularArea)
	{
		Rectangle2D territory = rectangularArea.getTerritory();
		Rectangle2D bounds = rectangularArea.getBounds2D();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		System.out.println("widh=" + territory.getWidth() + " "
				+ territory.getHeight());
		return affineTransform;
	}
}
