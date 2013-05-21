package cc.core;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.漢字組建活字;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 儲存漢字部件樹狀結構。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。 <code>ChineseCharacter</code>為
 * <code>ChineseCharacterWen</code>及<code>ChineseCharacterTzu</code>
 * 的共用介面，方便以後活字的產生。
 * 
 * @author Ihc
 */
public abstract class ChineseCharacter
{
	/**
	 * 指向上一層的部件結構
	 */
	private final ChineseCharacterTzu parent;

	/**
	 * 建立漢字部件結構
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層為樹狀的樹根，傳入null
	 */
	public ChineseCharacter(ChineseCharacterTzu parent)
	{
		this.parent = parent;
	}

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
	public abstract 漢字組建活字 typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent);

	/**
	 * 取得上一層部件結構。
	 * 
	 * @return 上一層部件結構
	 */
	public ChineseCharacterTzu getParent()
	{
		return parent;
	}

	/**
	 * 取得部件Unicode編碼
	 * 
	 * @return 部件Unicode編碼
	 */
	public abstract int getCodePoint();

	/**
	 * 取得部件的字元形態
	 * 
	 * @return 部件字元形態
	 */
	public char[] getChars()
	{
		return Character.toChars(getCodePoint());
	}
}