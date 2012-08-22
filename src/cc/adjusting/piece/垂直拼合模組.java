package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

public class 垂直拼合模組 extends 平移拼合模組
{
	private RectangularArea upPiece;
	private RectangularArea downPiece;

	public 垂直拼合模組(MergePieceAdjuster 調整工具)
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
		return;
	}

	@Override
	public double 上限初始值()
	{
		return upPiece.getBounds2D().getHeight();
	}

	@Override
	public boolean 搜尋判斷條件()
	{
		return 調整工具.areIntersected(upPiece, downPiece);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		downPiece.moveToOrigin();
		downPiece.moveBy(0, middleValue);
		return;
	}

	@Override
	public double 活字寬度()
	{
		return 調整工具.computePieceRadius(downPiece);
	}

	@Override
	public double 接觸邊長()
	{
		return downPiece.getBounds2D().getWidth();
	}

	@Override
	public double 活字相斥值()
	{
		return 調整工具.nonsuitableToClose(upPiece, downPiece, 接觸邊長());
	}

	@Override
	public RectangularArea[] 取得調整後活字物件()
	{
		RectangularArea[] rectangularAreas = new RectangularArea[2];
		rectangularAreas[0] = upPiece;
		rectangularAreas[1] = downPiece;
		return rectangularAreas;
	}
}
