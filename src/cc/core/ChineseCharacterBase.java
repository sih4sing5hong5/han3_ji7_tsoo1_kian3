package cc.core;

import cc.writer.ChineseCharacterWriter;

public class ChineseCharacterBase extends ChineseCharacter
{
	private int codePoint;

	ChineseCharacterBase(int codePoint)
	{
		this.codePoint = codePoint;
	}

	@Override
	public void generateByWriter(ChineseCharacterWriter writer)
	{
		writer.writeBase(this);
	}

	public int getCodePoint()
	{
		return codePoint;
	}

}
