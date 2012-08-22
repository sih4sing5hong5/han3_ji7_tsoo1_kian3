package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

public class 左右上三邊接合模組 extends 縮放接合模組
{
	public 左右上三邊接合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		rectangularArea = new RectangularArea(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(middleValue
				/ insidePiece.getBounds2D().getHeight());
		調整工具.shrinkPieceByFixingStroke(rectangularArea, affineTransform);
		rectangularArea.moveBy(outsidePiece.getBounds2D().getCenterX()
				- rectangularArea.getBounds2D().getCenterX(), outsidePiece
				.getBounds2D().getHeight()
				- rectangularArea.getBounds2D().getHeight());
		return;
	}
}
