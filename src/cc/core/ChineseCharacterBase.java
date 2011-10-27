package cc.core;

public class ChineseCharacterBase extends ChineseCharacter
{
	private int codePoint;

	ChineseCharacterBase(int codePoint)
	{
		this.codePoint = codePoint;
	}

	public int getCodePoint()
	{
		return codePoint;
	}
}
