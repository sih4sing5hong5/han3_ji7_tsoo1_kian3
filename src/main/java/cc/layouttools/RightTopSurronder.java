package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於右上的包圍部件。從右上方包住，像是「气」、「『可』的『丁』」、「『或』的戈」和「『武』的右上角」等等。
 *
 * @author Ihc
 */
public class RightTopSurronder extends ObjMoveableTypeSurronder
{
	/**
	 * 建立右上包圍工具
	 *
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public RightTopSurronder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("气");
		支援包圍部件.add("⺄");
		支援包圍部件.add("飞");
		// TODO　/*氣可武或…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前後用的調整模組 */
		RightTopAsmmod 模組 = new RightTopAsmmod(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組);

		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
