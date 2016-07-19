package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於上下左的包圍部件。從上下左三邊包住，像是「匚」和「⼖」等等。
 * 
 * @author Ihc
 */
public class TBLSurrounder extends ObjMoveableTypeSurronder
{
	/**
	 * 建立上下左三邊包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public TBLSurrounder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("匚");// 音方，方器義
		支援包圍部件.add("⼖");// 音夕，藏匿義
		// TODO　/*匣區…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前先用的調整模組 */
		TBLSurroundAssembler 模組 = new TBLSurroundAssembler(調整工具);
		/** 平推後用的調整模組 */
		TBLSurroundAsmmod 後模組 = new TBLSurroundAsmmod(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組, 後模組);
		
		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
