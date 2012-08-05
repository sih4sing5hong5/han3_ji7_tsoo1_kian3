package cc.core;

import java.text.StringCharacterIterator;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 漢字部件樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterTzu</code>記錄底下部件的組合方式。
 * 
 * @author Ihc
 */
public class ChineseCharacterTzu extends ChineseCharacter
{
	/**
	 * 部件的組合方式
	 */
	private final ChineseCharacterTzuCombinationType type;
	/**
	 * 底下的各個部件
	 */
	private final ChineseCharacter[] children;

	/**
	 * 建立一個字部件
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層為樹狀的樹根，傳入null
	 * @param codePoint
	 *            組合符號的Unicode編碼
	 * @param iterator
	 *            目前分析到的字串位置
	 * @throws ChineseCharacterFormatException
	 *             如果字串格式錯誤
	 * @throws IllegalArgumentException
	 *             如果<code>codePoint</code>不是部件組合符號
	 */
	ChineseCharacterTzu(ChineseCharacterTzu parent, int codePoint,
			StringCharacterIterator iterator)
			throws ChineseCharacterFormatException, IllegalArgumentException
	{
		super(parent);
		if (!ChineseCharacterTzuCombinationType.isCombinationType(codePoint))
			throw new IllegalArgumentException("這不是部件組合符號!!");
		type = ChineseCharacterTzuCombinationType.toCombinationType(codePoint);
		ChineseCharacterUtility utility = new ChineseCharacterUtility(iterator);
		children = new ChineseCharacter[type.getNumberOfChildren()];
		for (int i = 0; i < children.length; ++i)
		{
			children[i] = utility.parseCharacter(this);
		}
	}

	@Override
	public ChineseCharacterMovableType typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent)
	{
		return chineseCharacterTypeSetter.setTzu(parent, this);
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

	@Override
	public int getCodePoint()
	{
		return getType().toCodePoint();
	}
}
