package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.活字單元;

/**
 * 適用於「⿱」垂直拼合部件，如「⿱將水」為「漿」，只要是垂直拼合，皆用此型態。先將兩活字高度調整相同，再進行合併。
 * 
 * @author Ihc
 */
public class 水平拼合模組 extends 平移拼合模組
{
	/** 左邊的物件活字 */
	protected 活字單元 leftPiece;
	/** 右邊的物件活字 */
	protected 活字單元 rightPiece;

	/**
	 * 建立水平拼合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 水平拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(活字單元[] rectangularAreas)
	{
		leftPiece = rectangularAreas[0];
		rightPiece = rectangularAreas[1];
		活字單元 greaterPiece = null, smallerPiece = null;
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
	public boolean 活字是否太接近()
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
	public 活字單元[] 取得調整後活字物件()
	{
		活字單元[] rectangularAreas = new 平面幾何[2];
		rectangularAreas[0] = leftPiece;
		rectangularAreas[1] = rightPiece;
		return rectangularAreas;
	}
}
