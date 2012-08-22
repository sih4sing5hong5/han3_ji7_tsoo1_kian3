package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

public class 上蓋拼合模組 extends 垂直拼合模組
{
	protected double insideShrinkRate;

	public 上蓋拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初使化(RectangularArea[] rectangularAreas)
	{
		upPiece = rectangularAreas[0];
		downPiece = rectangularAreas[1];
		RectangularArea greaterPiece = null, smallerPiece = null;
		if (upPiece.getBounds2D().getWidth() > downPiece.getBounds2D()
				.getWidth())
		{
			greaterPiece = upPiece;
			smallerPiece = downPiece;
		}
		else
		{
			greaterPiece = downPiece;
			smallerPiece = upPiece;
		}
		double value = smallerPiece.getBounds2D().getWidth()
				/ greaterPiece.getBounds2D().getWidth();
		if (value > 0.0)
		{
			AffineTransform shrinkTransform = 調整工具.getAffineTransform(value,
					1.0);
			調整工具.shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);
		}

		insideShrinkRate = 1.0; // TODO 要依上部寬度決定
		AffineTransform insideShrinkTransform = 調整工具.getAffineTransform(
				insideShrinkRate, 1.0);
		調整工具.shrinkPieceByFixingStroke(downPiece, insideShrinkTransform);

		return;
	}

	@Override
	public void 變形處理(double middleValue)
	{
		downPiece.moveToOrigin();
		downPiece.moveBy(downPiece.getBounds2D().getWidth()
				* (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
				middleValue);
		return;
	}
}
