package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

public class 左下接合模組 extends 二元搜尋貼合模組
{
	protected RectangularArea outsidePiece;
	protected RectangularArea insidePiece;
	protected RectangularArea rectangularArea;

	public 左下接合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初使化(RectangularArea[] rectangularAreas)
	{
		outsidePiece = rectangularAreas[0];
		insidePiece = 調整工具.getPieceWithSquareTerritory(rectangularAreas[1]);
		return;
	}

	@Override
	public double 上限初始值()
	{
		return insidePiece.getBounds2D().getHeight();
	}

	@Override
	public void 變形處理(double middleValue)
	{
		rectangularArea = new RectangularArea(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(middleValue
				/ insidePiece.getBounds2D().getHeight());
		調整工具.shrinkPieceByFixingStroke(rectangularArea, affineTransform);
		rectangularArea.moveBy(outsidePiece.getBounds2D().getWidth()
				- rectangularArea.getBounds2D().getWidth(), 0);
		return;
	}

	@Override
	public boolean 搜尋判斷條件()
	{
		return 調整工具.areIntersected(outsidePiece, rectangularArea);
	}

	@Override
	public boolean 條件成立變大()
	{
		return false;
	}

	@Override
	public void 最後處理()
	{
		insidePiece = rectangularArea;
	}

	@Override
	public double 活字寬度()
	{
		return 調整工具.computePieceRadius(outsidePiece);
	}

	@Override
	public double 接觸邊長()
	{
		return rectangularArea.getBounds2D().getWidth()
				+ rectangularArea.getBounds2D().getHeight();
	}

	@Override
	public double 活字相斥值()
	{
		return 調整工具.nonsuitableToClose(outsidePiece, rectangularArea, 接觸邊長());
	}

	@Override
	public RectangularArea[] 取得調整後活字物件()
	{
		RectangularArea[] rectangularAreas = new RectangularArea[2];
		rectangularAreas[0] = outsidePiece;
		rectangularAreas[1] = insidePiece;
		return rectangularAreas;
	}
}
