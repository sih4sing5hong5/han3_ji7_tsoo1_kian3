package cc.core;

import java.text.StringCharacterIterator;

import cc.exception.CCParseTextException;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 漢字部件樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterTzu</code>記錄底下部件的組合方式。
 * 
 * @author Ihc
 */
public class ChineseCharacterTzu extends ChineseCharacter
{
	private final ChineseCharacterTzuCombinationType type;
	private final ChineseCharacter[] children;

	/**
	 * 建立一個字部件
	 * 
	 * @param codePoint
	 *            組合符號的Unicode編碼
	 * @param iterator
	 *            目前分析到的字串位置
	 * @throws CCParseTextException
	 *             如果字串格式錯誤
	 * @throws IllegalArgumentException
	 *             如果<code>codePoint</code>不是部件組合符號
	 */
	ChineseCharacterTzu(int codePoint, StringCharacterIterator iterator)
			throws CCParseTextException, IllegalArgumentException
	{
		if (ChineseCharacterTzuCombinationType.isCombinationType(codePoint))
			throw new IllegalArgumentException("這不是部件組合符號!!");
		type = ChineseCharacterTzuCombinationType.toCombinationType(codePoint);
		ChineseCharacterUtility utility = new ChineseCharacterUtility(iterator);
		children = new ChineseCharacter[type.getNumberOfChildren()];
		for (int i = 0; i < children.length; ++i)
		{
			children[i] = utility.parseCharacter();
			children[i].parent = this;
		}
	}

	@Override
	public ChineseCharacterMovableType typeset(ChineseCharacterTypeSetter writer)
	{
		return writer.setTzu(this);
	}

	/**
	 * 取得部件的組合方式
	 * 
	 * @return 部件的組合方式
	 */
	public ChineseCharacterTzuCombinationType getType()
	{
		return type;
	}

	/**
	 * 取得底下的各個部件
	 * 
	 * @return 底下的各個部件
	 */
	public ChineseCharacter[] getChildren()
	{
		return children;
	}
}
