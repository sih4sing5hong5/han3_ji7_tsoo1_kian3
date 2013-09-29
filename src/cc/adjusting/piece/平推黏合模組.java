package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.分離活字;

/**
 * 適用於靠平推合併的模組，若是內部活字是靠拉長一個方向的長度來當參數調整，就可適用此型態。
 * 
 * @author Ihc
 */
public abstract class 平推黏合模組 extends 縮放接合模組
{
	/**
	 * 建立平推黏合模組
	 * 
	 * @param 調整工具 使用此模組的調整工具
	 *            ，並使用其自身合併相關函式
	 */
	public 平推黏合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(分離活字[] rectangularAreas)
	{
		outsidePiece = rectangularAreas[0];
		insidePiece = rectangularAreas[1];
		return;
	}
}
