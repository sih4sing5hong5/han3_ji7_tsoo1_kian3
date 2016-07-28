package cc.layouttools;

import java.awt.geom.AffineTransform;

import cc.movabletype.SeprateMovabletype;

/**
 * 適用於「⿴」包圍拼合部件，如「⿴冖几」為「冗」，只要是此類拼合，皆用此型態。先將兩活字寬度調整相同，再調依情況縮小下部寬度，進行合併。
 * 
 * @author Ihc
 */
public class UpHatAsmmod extends VerticalAsmmod
{
	/** 下面物件活字要縮小的比例 */
	protected double insideShrinkRate;

	/**
	 * 建立上蓋拼合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public UpHatAsmmod(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(SeprateMovabletype[] rectangularAreas)
	{
		upPiece = rectangularAreas[0];
		downPiece = rectangularAreas[1];
		SeprateMovabletype greaterPiece = null, smallerPiece = null;
		if (upPiece.這馬字範圍().getWidth() > downPiece.這馬字範圍()
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
		double value = smallerPiece.這馬字範圍().getWidth()
				/ greaterPiece.這馬字範圍().getWidth();
		if (value > 0.0)
		{
			AffineTransform shrinkTransform = 調整工具.getAffineTransform(value,
					1.0);
			greaterPiece.縮放(shrinkTransform);
		}

		insideShrinkRate = 1.0; // TODO 要依上部寬度決定
		AffineTransform insideShrinkTransform = 調整工具.getAffineTransform(
				insideShrinkRate, 1.0);
		downPiece.縮放(insideShrinkTransform);

		return;
	}

	@Override
	public void 變形處理(double middleValue)
	{
		downPiece.徙轉原點();
		downPiece.徙(downPiece.這馬字範圍().getWidth()
				* (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
				middleValue);
		return;
	}
}
