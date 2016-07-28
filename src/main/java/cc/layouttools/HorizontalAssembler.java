package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於上蓋的包圍部件。蓋住上方，左右二邊只包含上方一小部份，像是「冖」、「宀」和「『學』上半部」等等。
 * 
 * @author Ihc
 */
public class HorizontalAssembler extends ObjMoveableTypeSurronder
{
	/**
	 * 建立水平拼合工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public HorizontalAssembler(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 左爿佮右爿佮做伙時的調整模組 */
		HorizontalAsmmod 模組 = new HorizontalAsmmod(調整工具);
		/** 將上下兩部件拼合的工具 */
		BisearchPasteAssembler 貼合工具 = new BisearchPasteAssembler();

		貼合工具.執行(模組, 物件活字.取得活字物件());

		SeprateMovabletype[] 調整結果 = 模組.取得調整後活字物件();
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
