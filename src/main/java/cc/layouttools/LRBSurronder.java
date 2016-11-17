package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於左右下的包圍部件。從左右下三邊包住，像是「凵」等等。
 * 
 * @author Ihc
 */
public class LRBSurronder extends ObjMoveableTypeSurronder
{
	/**
	 * 建立左右下三邊包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public LRBSurronder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("凵");
		// TODO　/*凶幽豳…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前先用的調整模組 */
		LRBOutsideAsmmod 模組 = new LRBOutsideAsmmod(調整工具);
		/** 平推後用的調整模組 */
		LRBInsideAsmmod 後模組 = new LRBInsideAsmmod(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組, 後模組);

		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
