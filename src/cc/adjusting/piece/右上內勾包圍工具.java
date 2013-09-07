package cc.adjusting.piece;

import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.活字單元;

/**
 * 用於右上下勾的包圍部件。從右上下勾包住，像是「勹」、「『疆』的弓」和「『島』的外部」等等。
 * 
 * @author Ihc
 */
public class 右上內勾包圍工具 extends 物件活字包圍工具
{
	/**
	 * 建立上下左三邊包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 右上內勾包圍工具(MergePieceAdjuster 調整工具)
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
		右上內勾接合模組 模組 = new 右上內勾接合模組(調整工具, 6.0);// TODO 人工參數
		/** 平推後用的調整模組 */
		四面後接合模組 後模組 = new 四面後接合模組(調整工具);
		/** 要使用的平推工具 */
		平推包圍調整工具 平推包圍調整工具 = new 平推包圍調整工具(調整工具, 模組, 後模組);
		
		活字單元[] 活字物件 = 物件活字.取得活字物件();
		模組.設定外部活字資訊(活字物件[0]);
		活字單元[] 調整結果 = 平推包圍調整工具.產生調整後活字(活字物件);
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
