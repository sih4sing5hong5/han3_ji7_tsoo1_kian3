package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 漢字部件樹狀結構的葉子。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。 <code>ChineseCharacterWen</code>
 * 記錄使用的部件。
 * 
 * @author Ihc
 */
public class ChineseCharacterWen extends ChineseCharacter
{
	/**
	 * 部件的Unicode編碼
	 */
	private final int codePoint;

	/**
	 * 建立一個字部件
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層為樹狀的樹根，傳入null
	 * @param codePoint
	 *            部件的Unicode編碼
	 */
	ChineseCharacterWen(ChineseCharacter parent,int codePoint)
	{
		super(parent);
		this.codePoint = codePoint;
	}

	@Override
	public ChineseCharacterMovableType typeset(ChineseCharacterTypeSetter writer)
	{
		return writer.setWen(this);
	}

	/**
	 * 取得部件Unicode編碼
	 * 
	 * @return 部件Unicode編碼
	 */
	public int getCodePoint()
	{
		return codePoint;
	}

	/**
	 * 取得部件的字元形態
	 * 
	 * @return 部件字元形態
	 */
	public char[] getChars()
	{
		return Character.toChars(codePoint);
	}
}
