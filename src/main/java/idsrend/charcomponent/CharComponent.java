package idsrend.charcomponent;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.char_indexingtool.ChineseCharacterTypeSetter;
import cc.movabletype.ChineseCharCompositeMoveabletype;

/**
 * 儲存漢字部件樹狀結構。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。 <code>ChineseCharacter</code>為
 * <code>ChineseCharacterWen</code>及<code>ChineseCharacterTzu</code>
 * 的共用介面，方便以後活字的產生。
 * 
 * @author Ihc
 */
public abstract class CharComponent
{
	public abstract boolean 是文部件();

	public abstract boolean 是字部件();

	/**
	 * 取得部件的字串形態
	 * 
	 * @return 部件字串形態
	 */
	public String 部件組字式()
	{
		return new String(Character.toChars(Unicode編號()));
	}

	/**
	 * 取得部件Unicode編碼
	 * 
	 * @return 部件Unicode編碼
	 */
	public abstract int Unicode編號();

	/**
	 * 提到這个部件下跤的組字式。
	 * 
	 * @return 這个物件下跤的組字式
	 */
	public abstract String 樹狀結構組字式();

	/**
	 * 以此部件結構產生活字結構。用<code>ChineseCharacterTypeSetter</code>
	 * (活字設定工具)來轉換成ChineseCharacterMovableType(活字)。
	 * 
	 * @param chineseCharacterTypeSetter
	 *            欲採用的活字設定工具
	 * @param parent
	 *            此活字結構的上層活字
	 * @return 產生出來的活字結構
	 */
	public abstract ChineseCharCompositeMoveabletype typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent);

}
