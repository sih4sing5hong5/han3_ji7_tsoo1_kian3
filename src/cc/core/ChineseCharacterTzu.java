package cc.core;

import java.text.StringCharacterIterator;

import cc.exception.CCParseTextException;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.setting.ChineseCharacterTypeSetter;

public class ChineseCharacterTzu extends ChineseCharacter
{
	private ChineseCharacterTzuCombinationType type;
	private ChineseCharacter[] children;
	private boolean theSameChildren;

	ChineseCharacterTzu(int codePoint, StringCharacterIterator iterator)
			throws CCParseTextException
	{
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

	public ChineseCharacterTzuCombinationType getType()
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
