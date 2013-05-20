package cc.adjusting.piece;

import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 用於左下的包圍部件。從左下方包住，像是「辶」、「廴」和「『翅』的支」等等。
 * 
 * @author Ihc
 */
public class 左下包圍工具 extends 物件活字包圍工具
{
	/**
	 * 建立左下包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 左下包圍工具(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("辶");
		支援包圍部件.add("廴");
		// TODO　/*過建旭趕毡翅處爬題颱…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu 物件活字)
	{
		/** 平推前後用的調整模組 */
		左下接合模組 模組 = new 左下接合模組(調整工具);
		/** 要使用的平推工具 */
		平推包圍調整工具 平推包圍調整工具 = new 平推包圍調整工具(調整工具, 模組);

		RectangularArea[] 調整結果 = 平推包圍調整工具.產生調整後活字(物件活字.取得活字物件());
		物件活字.getPiece().重設並組合活字(調整結果);
		return;
	}
}
