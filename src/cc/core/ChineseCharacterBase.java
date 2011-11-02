package cc.core;

import cc.typesetting.ChineseCharacterTypesetter;

public class ChineseCharacterBase extends ChineseCharacter
{
	private int codePoint;

	ChineseCharacterBase(int codePoint)
	{
		this.codePoint = codePoint;
	}

	@Override
	public void generateByWriter(ChineseCharacterTypesetter writer)
	{
		writer.writeBase(this);
	}

	public int getCodePoint()
	{
		return codePoint;
	}

}
