package cc.core;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Vector;

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
			vector.add(parseCharacter());
			// TODO catch EOF
		}
		return null;
	}

	ChineseCharacter parseCharacter()
	{
		if (iterator.current() == CharacterIterator.DONE)
			;// TODO EOF throw
		int codePoint = 0;
		if (Character.isHighSurrogate(iterator.current()))
		{
			if (iterator.getIndex() + 1 != iterator.getEndIndex())
			{
				codePoint = Character.toCodePoint(iterator.current(),
						iterator.next());
			}
			else
				;// TODO EOF throw
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
