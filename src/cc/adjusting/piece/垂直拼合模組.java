package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.活字單元;

/**
 * 適用於「⿱」垂直拼合部件，如「⿱將水」為「漿」，只要是垂直拼合，皆用此型態。先將兩活字寬度調整相同，再進行合併。
 * 
 * @author Ihc
 */
public class 垂直拼合模組 extends 平移拼合模組
{
	/** 上面的物件活字 */
	protected 活字單元 upPiece;
	/** 下面的物件活字 */
	protected 活字單元 downPiece;

	/**
	 * 建立垂直拼合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 垂直拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(活字單元[] rectangularAreas)
	{
		upPiece = rectangularAreas[0];
		downPiece = rectangularAreas[1];
		活字單元 greaterPiece = null, smallerPiece = null;
		if (upPiece.字範圍().getWidth() > downPiece.字範圍()
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
		double value = smallerPiece.字範圍().getWidth()
				/ greaterPiece.字範圍().getWidth();
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
		return upPiece.字範圍().getHeight();
	}

	@Override
	public boolean 活字是否太接近()
	{
		return 調整工具.areIntersected(upPiece, downPiece);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		downPiece.徙轉原點();
		downPiece.徙(0, middleValue);
		return;
	}

	@Override
	public double 活字寬度()
	{
		double 下跤闊度 = 調整工具.computePieceRadius(downPiece);
		if (!Double.isNaN(下跤闊度))
			return 下跤闊度;
		double 頂懸闊度 = 調整工具.computePieceRadius(upPiece);
		if (!Double.isNaN(頂懸闊度))
			return 頂懸闊度;
		return 0.0;
	}

	@Override
	public double 接觸邊長()
	{
		return downPiece.字範圍().getWidth();
	}

	@Override
	public double 活字相斥值()
	{
		return 調整工具.nonsuitableToClose(upPiece, downPiece, 接觸邊長());
	}

	@Override
	public 活字單元[] 取得調整後活字物件()
	{
		活字單元[] rectangularAreas = new 分離活字[2];
		rectangularAreas[0] = upPiece;
		rectangularAreas[1] = downPiece;
		return rectangularAreas;
	}
}
