package cc.adjusting.piece;

import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

public abstract class 二元搜尋貼合模組
{
	/** 使用此模組的調整工具，並使用其自身合併相關函式 */
	protected MergePieceAdjuster 調整工具;

	public 二元搜尋貼合模組(MergePieceAdjuster 調整工具)
	{
		this.調整工具 = 調整工具;
	}

	public abstract void 初使化(RectangularArea[] rectangularAreas);

	public double 下限初始值()
	{
		return 0.0;
	}

	public abstract double 上限初始值();

	public double 取得精確度()
	{
		return 調整工具.getPrecision();
	}

	public abstract boolean 搜尋判斷條件();

	public abstract boolean 條件成立變大();

	public abstract void 變形處理(double middleValue);

	public abstract void 調整後處理();

	public abstract double 活字寬度();

	public abstract double 接觸邊長();

	public abstract double 活字相斥值();

	public abstract RectangularArea[] 取得調整後活字物件();
}
