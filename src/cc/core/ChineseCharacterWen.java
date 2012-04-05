package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.setting.ChineseCharacterTypeSetter;

public class ChineseCharacterWen extends ChineseCharacter
{
	private int codePoint;

	ChineseCharacterWen(int codePoint)
	{
		this.codePoint = codePoint;
	}

	public int getCodePoint()
	{
		return codePoint;
	}

	public char[] getChars()
	{
		return Character.toChars(codePoint);
	}

	@Override
	public ChineseCharacterMovableType typeset(ChineseCharacterTypeSetter writer)
	{
		return writer.setWen(this);
	}
}
