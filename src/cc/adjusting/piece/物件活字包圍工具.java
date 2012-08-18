package cc.adjusting.piece;

import java.util.Vector;

import cc.moveable_type.piece.PieceMovableTypeTzu;

/**
 * 包圍工具基本架構，提供包圍部件相對應的調整工具。
 * 
 * @author Ihc
 */
public abstract class 物件活字包圍工具
{
	/** 使用此包圍工具的調整工具，並使用其自身合併相關函式 */
	protected MergePieceAdjuster 調整工具;
	/** 支援包圍部件列表 */
	protected Vector<String> 支援包圍部件;

	/**
	 * 建立包圍工具。
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 物件活字包圍工具(MergePieceAdjuster 調整工具)
	{
		this.調整工具 = 調整工具;
		this.支援包圍部件 = new Vector<String>();
	}

	/**
	 * 取得此包圍工具支援哪些部件進行組合
	 * 
	 * @return 支援部件的Unicode編碼
	 */
	public int[] 取得支援包圍部件控制碼清單()
	{
		int[] 支援包圍部件控制碼清單 = new int[支援包圍部件.size()];
		for (int i = 0; i < 支援包圍部件.size(); ++i)
		{
			switch (支援包圍部件.elementAt(i).length())
			{
			case 1:
			case 2:
				支援包圍部件控制碼清單[i] = 支援包圍部件.elementAt(i).codePointAt(0);
				break;
			default:
				支援包圍部件控制碼清單[i] = 0;
				break;
			}
		}
		return 支援包圍部件控制碼清單;
	}

	/**
	 * 用此工具合併合體活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            合體活字
	 */
	public abstract void 合併(PieceMovableTypeTzu pieceMovableTypeTzu);
}