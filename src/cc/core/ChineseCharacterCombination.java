package cc.core;

import java.text.StringCharacterIterator;

import cc.exception.CCParseTextException;
import cc.typesetting.ChineseCharacterTypesetter;

public class ChineseCharacterCombination extends ChineseCharacter
{
	private ChineseCharacterCombinationType type;
	private ChineseCharacter[] children;
	private boolean theSameChildren;

	ChineseCharacterCombination(int codePoint, StringCharacterIterator iterator)
			throws CCParseTextException
	{
		type = ChineseCharacterCombinationType.toCombinationType(codePoint);
		ChineseCharacterUtility utility = new ChineseCharacterUtility(iterator);
		children = new ChineseCharacter[type.getNumberOfChildren()];
		for (int i = 0; i < children.length; ++i)
		{
			children[i] = utility.parseCharacter();
			children[i].parent = this;
		}
	}

	@Override
	public void generateByWriter(ChineseCharacterTypesetter writer)
	{
		writer.writeCombination(this);
	}

	public ChineseCharacterCombinationType getType()
	{
		return type;
	}

	public ChineseCharacter[] getChildren()
	{
		return children;
	}

	public boolean isTheSameChildren()
	{
		return theSameChildren;
	}

	public void setTheSameChildren(boolean theSameChildren)
	{
		this.theSameChildren = theSameChildren;
	}
}
