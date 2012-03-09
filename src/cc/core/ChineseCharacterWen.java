package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.typesetting.ChineseCharacterTypesetter;

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

	@Override
	public ChineseCharacterMovableType typeset(ChineseCharacterTypesetter writer)
	{
		return writer.setWen(this);
	}
}
