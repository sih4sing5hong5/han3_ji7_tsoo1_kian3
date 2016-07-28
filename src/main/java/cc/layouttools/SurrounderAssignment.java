package cc.layouttools;

import java.util.Hashtable;

import org.slf4j.Logger;

import cc.log.IDSGenLogToolpack;
import cc.movabletype.PieceMovableTypeTzu;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.FinalCharComponent;

/**
 * 整合所有包圍工具，讓增減工具方便又不用修改其他程式碼。
 * 
 * @author Ihc
 */
public class SurrounderAssignment
{
	/** 記錄程式狀況 */
	Logger 記錄工具;

	/** 記錄哪些包圍部件（Unicode編碼）對應到哪些包圍工具 */
	private Hashtable<Integer, ObjMoveableTypeSurronder> 包圍部件工具表;

	/** 若包圍部件無定義，愛用的工具 */
	private ObjMoveableTypeSurronder 無支援暫時用包圍工具 = null;

	/** 建立分派工具。 */
	public SurrounderAssignment()
	{
		記錄工具 = new IDSGenLogToolpack().記錄工具(getClass());

		包圍部件工具表 = new Hashtable<Integer, ObjMoveableTypeSurronder>();
	}

	/**
	 * 加入一個包圍工具。若有與之前重覆的包圍部件，以先加入的為準。
	 * 
	 * @param 包圍工具
	 *            欲加入的工具
	 * @return 是否有和之前加入的包圍部件有重覆
	 */
	public boolean add(ObjMoveableTypeSurronder 包圍工具)
	{
		boolean 無重覆 = true;
		int[] 支援包圍部件控制碼清單 = 包圍工具.取得支援包圍部件控制碼清單();
		記錄工具.debug("「{}」支援「{}」組合符號", 包圍工具.getClass().toString(), 支援包圍部件控制碼清單);
		for (int 支援包圍部件控制碼 : 支援包圍部件控制碼清單)
		{
			if (包圍部件工具表.containsKey(支援包圍部件控制碼))
			{
				記錄工具.info("「{}」的「{}」組合符號佮儂仝款", 包圍工具.getClass().toString(),
						支援包圍部件控制碼);
				無重覆 = false;
			}
			else
			{
				包圍部件工具表.put(支援包圍部件控制碼, 包圍工具);
			}
		}
		return 無重覆;
	}

	/**
	 * 設定一个無支援暫時用包圍工具，拄著無
	 * 
	 * @param 包圍工具
	 *            無字欲用的工具
	 */
	public void 設定無支援暫時用包圍工具(ObjMoveableTypeSurronder 包圍工具)
	{
		this.無支援暫時用包圍工具 = 包圍工具;
		return;
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
		FinalCharComponent chineseCharacterTzu = (FinalCharComponent) pieceMovableTypeTzu
				.getChineseCharacter();
		CharComponent chineseCharacter = chineseCharacterTzu.底下元素()[0];
		int 外部活字控制碼 = chineseCharacter.Unicode編號();
		ObjMoveableTypeSurronder 包圍工具 = 包圍部件工具表.get(外部活字控制碼);
		if (包圍工具 != null)
		{
			包圍工具.組合(pieceMovableTypeTzu);
			return true;
		}
		else if (無支援暫時用包圍工具 != null)
		{
			if (pieceMovableTypeTzu.getChineseCharacter() instanceof CharComponent)
				記錄工具.info("組合符號無支援「{}」組字式", ((CharComponent) pieceMovableTypeTzu
						.getChineseCharacter()).樹狀結構組字式());
			else
				記錄工具.info("組合符號無支援「{}」CharComponent", Character.toChars(外部活字控制碼));
			無支援暫時用包圍工具.組合(pieceMovableTypeTzu);
			return true;
		}
		return false;
	}
}
