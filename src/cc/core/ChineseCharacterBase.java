package cc.core;

import cc.printing.ChineseCharacterPrinter;
import cc.typesetting.ChineseCharacterTypesetter;

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

	@Override
	public void typeset(ChineseCharacterTypesetter writer)
	{
		writer.setBase(this);
		return;
	}

	@Override
	public void print(ChineseCharacterPrinter printer)
	{
		printer.printBase(this);
		return;
	}
}
