package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 適用於外部部件四面包圍的活字接合，如「⿴囗或」為「國」。在接合時，都固定外部活字，並將內部活字固定在正中央縮放。
 * 
 * @author Ihc
 */
public class 四面接合模組 extends 縮放接合模組
{
	/**
	 * 建立四面接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 四面接合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		temporaryPiece = new RectangularArea(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(middleValue
				/ insidePiece.getBounds2D().getHeight());
		調整工具.shrinkPieceByFixingStroke(temporaryPiece, affineTransform);
		temporaryPiece.moveBy(outsidePiece.getBounds2D().getCenterX()
				- temporaryPiece.getBounds2D().getCenterX(), outsidePiece
				.getBounds2D().getCenterY()
				- temporaryPiece.getBounds2D().getCenterY());
		return;
	}
}
