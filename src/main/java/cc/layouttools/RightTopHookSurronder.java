package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於右上下勾的包圍部件。從右上下勾包住，像是「勹」、「『疆』的弓」和「『島』的外部」等等。
 * 
 * @author Ihc
 */
public class RightTopHookSurronder extends ObjMoveableTypeSurronder
{
	/**
	 * 建立上下左三邊包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public RightTopHookSurronder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("勹");
		支援包圍部件.add("弓");
		// TODO　/*旬疆鳥島…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前先用的調整模組 */
		RightTopHookAsmmod 模組 = new RightTopHookAsmmod(調整工具, 6.0);// TODO 人工參數
		/** 平推後用的調整模組 */
		FullSurInsideAssembler 後模組 = new FullSurInsideAssembler(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組, 後模組);
		
		SeprateMovabletype[] 活字物件 = 物件活字.取得活字物件();
		模組.設定外部活字資訊(活字物件[0]);
		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(活字物件);
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
