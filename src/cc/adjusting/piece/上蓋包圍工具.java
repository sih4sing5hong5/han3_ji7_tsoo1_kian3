package cc.adjusting.piece;

import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.分離活字;

/**
 * 用於上蓋的包圍部件。蓋住上方，左右二邊只包含上方一小部份，像是「冖」、「宀」和「『學』上半部」等等。
 * 
 * @author Ihc
 */
public class 上蓋包圍工具 extends 物件活字包圍工具
{
	/**
	 * 建立上蓋包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 上蓋包圍工具(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("冖");
		支援包圍部件.add("");//「冖」中研院造字
		支援包圍部件.add("宀");
		// 支援包圍部件.add("學");//TODO
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 上蓋和下部拼合時的調整模組 */
		上蓋拼合模組 模組 = new 上蓋拼合模組(調整工具);
		/** 將上下兩部件拼合的工具 */
		二元搜尋貼合工具 貼合工具 = new 二元搜尋貼合工具();

		貼合工具.執行(模組, 物件活字.取得活字物件());

		分離活字[] 調整結果 = 模組.取得調整後活字物件();
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
