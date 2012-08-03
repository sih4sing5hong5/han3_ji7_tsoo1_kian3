package cc.core;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Vector;

import cc.exception.CCParseTextException;

/**
 * 用來分析漢字部件的工具。給一字串，分析出含所有漢字的部件結構。
 * 
 * @author Ihc
 */
public class ChineseCharacterUtility
{
	/**
	 * 字串目前處理到的位置
	 */
	private StringCharacterIterator iterator;

	/**
	 * 以<code>String</code>建立一個分析工具
	 * 
	 * @param string
	 *            欲分析的字串
	 */
	public ChineseCharacterUtility(String string)
	{
		this.iterator = new StringCharacterIterator(string);
	}

	/**
	 * 以<code>StringCharacterIterator</code>建立一個分析工具
	 * 
	 * @param iterator
	 *            欲分析的字串
	 */
	public ChineseCharacterUtility(StringCharacterIterator iterator)
	{
		this.iterator = iterator;
	}

	/**
	 * 分析字串並回傳字串中全部的漢字部件
	 * 
	 * @return 字串中全部的漢字部件。若字串格式有錯，不完整的部件不會被加上去，並且在陣列最後會補上一個null當作通知
	 */
	public Vector<ChineseCharacter> parseText()
	{
		Vector<ChineseCharacter> vector = new Vector<ChineseCharacter>();
		try
		{
			while (iterator.current() != CharacterIterator.DONE)
			{
				vector.add(parseCharacter());
				vector.lastElement().setParent(null);
			}
		}
		catch (CCParseTextException e)
		{
			vector.add(null);
		}
		return vector;
	}

	/**
	 * 分析下一個漢字部件
	 * 
	 * @return 下一個漢字部件
	 * @throws CCParseTextException
	 *             如果字串結構不對，通常是因為組合符號太多，部件有缺漏，無法形成一個完整的漢字結構。
	 */
	ChineseCharacter parseCharacter() throws CCParseTextException
	{
		if (iterator.current() == CharacterIterator.DONE)
			throw new CCParseTextException();// TODO EOF throw
		int codePoint = 0;
		if (Character.isHighSurrogate(iterator.current()))
		{
			if (iterator.getIndex() + 1 != iterator.getEndIndex())
			{
				codePoint = Character.toCodePoint(iterator.current(),
						iterator.next());
			}
			else
			{
				iterator.next();
				throw new CCParseTextException();// TODO EOF throw
			}
		}
		else
		{
			codePoint = iterator.current();
		}
		iterator.next();
		ChineseCharacter chineseCharacter = null;
		if (ChineseCharacterTzuCombinationType.isCombinationType(codePoint))
		{
			chineseCharacter = new ChineseCharacterTzu(codePoint, iterator);
		}
		else
		{
			chineseCharacter = new ChineseCharacterWen(codePoint);
		}
		return chineseCharacter;
	}
}
