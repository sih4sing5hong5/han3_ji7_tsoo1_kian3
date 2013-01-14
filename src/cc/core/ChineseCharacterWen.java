package cc.core;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.漢字組建活字;
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
	 * 建立一個文部件
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層為樹狀的樹根，傳入null
	 * @param codePoint
	 *            部件的Unicode編碼
	 */
	ChineseCharacterWen(ChineseCharacterTzu parent, int codePoint)
	{
		super(parent);
		this.codePoint = codePoint;
	}

	@Override
	public 漢字組建活字 typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent)
	{
		return chineseCharacterTypeSetter.setWen(parent, this);
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
}
