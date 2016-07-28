package cc.layouttools;

import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;

/**
 * 用於左右上的包圍部件。從左右上三邊包住，像是「冂」、「門」、「鬥」、「『咼』的外部」和「『夃』的乃」等等。
 *
 * @author Ihc
 */
public class LRTDoorSurronder extends ObjMoveableTypeSurronder
{

	/**
	 * 建立左右上三邊包圍工具
	 *
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public LRTDoorSurronder(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("冂");
		支援包圍部件.add("門");
		支援包圍部件.add("鬥");
		支援包圍部件.add("乃");
		支援包圍部件.add("⻔");
		支援包圍部件.add("⺇");
		支援包圍部件.add("⺆");
		支援包圍部件.add("冇");
		支援包圍部件.add("冎");
		支援包圍部件.add("卩");

		// TODO　/*冏間鬥盈咼…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前先用的調整模組 */
		FullSurOutsideAssembler 模組 = new FullSurOutsideAssembler(調整工具);
		/** 平推後用的調整模組 */
		FullSurInsideAssembler 後模組 = new FullSurInsideAssembler(調整工具);
		/** 要使用的平推工具 */
		FlatSurronder FlatSurronder = new FlatSurronder(調整工具, 模組, 後模組, false);

		SeprateMovabletype[] 調整結果 = FlatSurronder.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
