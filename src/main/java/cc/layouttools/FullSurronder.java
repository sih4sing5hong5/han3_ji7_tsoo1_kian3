package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於四面的包圍部件。從四面包住，像是「囗」等等。
 *
 * @author Ihc
 */
public class FullSurronder extends ObjMoveableTypeSurronder
{
	/**
	 * 建立四面包圍工具
	 *
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public FullSurronder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("囗");
		支援包圍部件.add("⼞");
		支援包圍部件.add("⼝");
		//這三個unicode都不一樣，直覺能包的都讓它能作用吧
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前先用的調整模組 */
		FullSurOutsideAssembler 模組 = new FullSurOutsideAssembler(調整工具);
		/** 平推後用的調整模組 */
		FullSurInsideAssembler 後模組 = new FullSurInsideAssembler(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組, 後模組);

		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
