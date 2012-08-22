package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

public class 水平拼合模組 extends 平移拼合模組
{
	protected RectangularArea leftPiece;
	protected RectangularArea rightPiece;

	public 水平拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初使化(RectangularArea[] rectangularAreas)
	{
		leftPiece = rectangularAreas[0];
		rightPiece = rectangularAreas[1];
		RectangularArea greaterPiece = null, smallerPiece = null;
		if (leftPiece.getBounds2D().getHeight() > rightPiece.getBounds2D()
				.getHeight())
		{
			greaterPiece = leftPiece;
			smallerPiece = rightPiece;
		}
		else
		{
			greaterPiece = rightPiece;
			smallerPiece = leftPiece;
		}
		double value = smallerPiece.getBounds2D().getHeight()
				/ greaterPiece.getBounds2D().getHeight();
		if (value > 0.0)
		{
			AffineTransform shrinkTransform = 調整工具.getAffineTransform(1.0,
					value);
			調整工具.shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);
		}
		return;
	}

	@Override
	public double 上限初始值()
	{
		return leftPiece.getBounds2D().getWidth();
	}

	@Override
	public boolean 搜尋判斷條件()
	{
		return 調整工具.areIntersected(leftPiece, rightPiece);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		rightPiece.moveToOrigin();
		rightPiece.moveBy(middleValue, 0);
		return;
	}

	@Override
	public double 活字寬度()
	{
		return 調整工具.computePieceRadius(rightPiece);
	}

	@Override
	public double 接觸邊長()
	{
		return rightPiece.getBounds2D().getHeight();
	}

	@Override
	public double 活字相斥值()
	{
		return 調整工具.nonsuitableToClose(leftPiece, rightPiece, 接觸邊長());
	}

	@Override
	public RectangularArea[] 取得調整後活字物件()
	{
		RectangularArea[] rectangularAreas = new RectangularArea[2];
		rectangularAreas[0] = leftPiece;
		rectangularAreas[1] = rightPiece;
		return rectangularAreas;
	}
}
