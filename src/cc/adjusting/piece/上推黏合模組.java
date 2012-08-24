package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

public class 上推黏合模組 extends 平推黏合模組
{
	/**
	 * 建立上推黏合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 上推黏合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public double 下限初始值()
	{
		return insidePiece.getBounds2D().getHeight();
	}

	@Override
	public double 上限初始值()
	{
		return outsidePiece.getBounds2D().getHeight();
	}

	@Override
	public boolean 活字是否太接近()
	{
		return super.活字是否太接近()
				|| outsidePiece.getBounds2D().getMinY() > temporaryPiece
						.getBounds2D().getMinY();
	}

	@Override
	public void 變形處理(double middleValue)
	{
		temporaryPiece = new RectangularArea(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(1.0,
				middleValue / insidePiece.getBounds2D().getHeight());
		調整工具.shrinkPieceByFixingStroke(temporaryPiece, affineTransform);
		temporaryPiece.moveBy(insidePiece.getBounds2D().getMaxX()
				- temporaryPiece.getBounds2D().getMaxX(), insidePiece
				.getBounds2D().getMaxY()
				- temporaryPiece.getBounds2D().getMaxY());
		return;
	}
}
