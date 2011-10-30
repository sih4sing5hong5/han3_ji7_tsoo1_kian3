package cc.core;

import java.text.StringCharacterIterator;

public class ChineseCharacterCombination extends ChineseCharacter
{
	private ChineseCharacterCombinationType type;
	private ChineseCharacter[] children;
	private boolean theSameChildren;

	ChineseCharacterCombination(int codePoint, StringCharacterIterator iterator)
	{
		type = ChineseCharacterCombinationType.toCombinationType(codePoint);
		ChineseCharacterUtility utility = new ChineseCharacterUtility(iterator);
		children = new ChineseCharacterCombination[2];
		children[0] = utility.parseCharacter();
		children[1] = utility.parseCharacter();
	}

	ChineseCharacterCombinationType getType()
	{
		return type;
	}

	void setType(ChineseCharacterCombinationType type)
	{
		this.type = type;
	}

	boolean isTheSameChildren()
	{
		return theSameChildren;
	}

	void setTheSameChildren(boolean theSameChildren)
	{
		this.theSameChildren = theSameChildren;
	}
}
