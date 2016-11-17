package cc.layouttools;

import java.awt.geom.AffineTransform;

import cc.movabletype.SeprateMovabletype;
/**
 * 讓第二個活字往左延伸的模組，碰到第一個活字或是邊界即停止。
 * 
 * @author Ihc
 */
public class PasteLeftwardSticking extends PasteFlatpushSticking
{
	/**
	 * 建立左推黏合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public PasteLeftwardSticking(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public double 下限初始值()
	{
		return insidePiece.這馬字範圍().getWidth();
	}

	@Override
	public double 上限初始值()
	{
		return outsidePiece.這馬字範圍().getWidth();
	}

	@Override
	public boolean 活字是否太接近()
	{
		return super.活字是否太接近()
				|| outsidePiece.這馬字範圍().getMinX() > temporaryPiece
						.這馬字範圍().getMinX();
	}

	@Override
	public void 變形處理(double middleValue)
	{
		temporaryPiece = new SeprateMovabletype(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(middleValue
				/ insidePiece.這馬字範圍().getWidth(), 1.0);
		temporaryPiece.縮放(affineTransform);
		temporaryPiece.徙(insidePiece.這馬字範圍().getMaxX()
				- temporaryPiece.這馬字範圍().getMaxX(), insidePiece
				.這馬字範圍().getMaxY()
				- temporaryPiece.這馬字範圍().getMaxY());
		return;
	}
}
