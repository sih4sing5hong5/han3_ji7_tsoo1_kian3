package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.RectangularArea;

public abstract class 平移拼合模組 extends 二元搜尋貼合模組
{
	public 平移拼合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public boolean 條件成立變大()
	{
		return true;
	}

	@Override
	public void 調整後處理()
	{
		return;
	}
}
