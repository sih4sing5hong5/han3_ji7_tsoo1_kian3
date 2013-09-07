package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.活字單元;

/**
 * 適用於「⿴」包圍拼合部件，如「⿴冖几」為「冗」，只要是此類拼合，皆用此型態。先將兩活字寬度調整相同，再調依情況縮小下部寬度，進行合併。
 * 
 * @author Ihc
 */
public class 上蓋拼合模組 extends 垂直拼合模組
{
	/** 下面物件活字要縮小的比例 */
	protected double insideShrinkRate;

	/**
	 * 建立上蓋拼合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 上蓋拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(活字單元[] rectangularAreas)
	{
		upPiece = rectangularAreas[0];
		downPiece = rectangularAreas[1];
		活字單元 greaterPiece = null, smallerPiece = null;
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
