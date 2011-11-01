package cc.core;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Vector;

import cc.exception.CCParseTextException;

public class ChineseCharacterUtility
{
	private StringCharacterIterator iterator;

	public ChineseCharacterUtility(String string)
	{
		this.iterator = new StringCharacterIterator(string);
	}

	public ChineseCharacterUtility(StringCharacterIterator iterator)
	{
		this.iterator = iterator;
	}

	public Vector<ChineseCharacter> parseText()
	{
		Vector<ChineseCharacter> vector = new Vector<ChineseCharacter>();
		while (iterator.current() != CharacterIterator.DONE)
		{
			try
			{
				vector.add(parseCharacter());
				vector.lastElement().parent = null;
			}
			catch (CCParseTextException e)
			{
				vector.add(null);
			}
		}
		return null;
	}

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
				throw new CCParseTextException();// TODO EOF throw
		}
		else
		{
			codePoint = iterator.current();
		}
		iterator.next();
		ChineseCharacter chineseCharacter = null;
		if (ChineseCharacterCombinationType.isCombinationType(codePoint))
		{
			chineseCharacter = new ChineseCharacterCombination(codePoint,
					iterator);
		}
		else
		{
			chineseCharacter = new ChineseCharacterBase(codePoint);
		}
		return chineseCharacter;
	}
}
