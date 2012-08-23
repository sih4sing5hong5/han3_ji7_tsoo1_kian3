package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 適用於外部部件在左上的活字接合，如「⿴尸古」為「居」。在接合時，都固定外部活字，並將內部活字固定在右下縮放。
 * 
 * @author Ihc
 */
public class 左上接合模組 extends 縮放接合模組
{
	/**
	 * 建立左上接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 左上接合模組(MergePieceAdjuster 調整工具)
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
		temporaryPiece.moveBy(outsidePiece.getBounds2D().getWidth()
				- temporaryPiece.getBounds2D().getWidth(), outsidePiece
				.getBounds2D().getHeight()
				- temporaryPiece.getBounds2D().getHeight());
		return;
	}
}
