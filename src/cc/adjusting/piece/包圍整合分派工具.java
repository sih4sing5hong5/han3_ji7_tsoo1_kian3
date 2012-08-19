package cc.adjusting.piece;

import java.util.Hashtable;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;

/**
 * 整合所有包圍工具，讓增減工具方便又不用修改其他程式碼。
 * 
 * @author Ihc
 */
public class 包圍整合分派工具
{
	/** 記錄哪些包圍部件（Unicode編碼）對應到哪些包圍工具 */
	private Hashtable<Integer, 物件活字包圍工具> 包圍部件工具表;

	/** 建立分派工具。 */
	public 包圍整合分派工具()
	{
		包圍部件工具表 = new Hashtable<Integer, 物件活字包圍工具>();
	}

	/**
	 * 加入一個包圍工具。若有與之前重覆的包圍部件，以先加入的為準。
	 * 
	 * @param 包圍工具
	 *            欲加入的工具
	 * @return 是否有和之前加入的包圍部件有重覆
	 */
	public boolean add(物件活字包圍工具 包圍工具)
	{//TODO 改成丟例外
		boolean 無重覆 = true;
		int[] 支援包圍部件控制碼清單 = 包圍工具.取得支援包圍部件控制碼清單();
		for (int i = 0; i < 支援包圍部件控制碼清單.length; ++i)
		{
			if (包圍部件工具表.containsKey(支援包圍部件控制碼清單[i]))
			{
				無重覆 = false;
			}
			else
			{
				包圍部件工具表.put(支援包圍部件控制碼清單[i], 包圍工具);
			}
		}
		return 無重覆;
	}

	/**
	 * 將活字物件依部件屬性尋找工具組合起來，並回傳成功與否。
	 * 
	 * @param pieceMovableTypeTzu
	 *            活字物件
	 * @return 是否有相對應的包圍工具可使用
	 */
	public boolean 組合(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		ChineseCharacterTzu chineseCharacterTzu = (ChineseCharacterTzu) pieceMovableTypeTzu
				.getChineseCharacter();
		ChineseCharacter chineseCharacter = chineseCharacterTzu.getChildren()[0];
		int 外部活字控制碼 = chineseCharacter.getCodePoint();
		物件活字包圍工具 包圍工具 = 包圍部件工具表.get(外部活字控制碼);
		if (包圍工具 != null)
		{
			包圍工具.組合(pieceMovableTypeTzu);
			return true;
		}
		return false;
	}
}
