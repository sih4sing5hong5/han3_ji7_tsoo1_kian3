package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.printing.ChineseCharacterPrinter;
import cc.typesetting.ChineseCharacterTypesetter;

public abstract class ChineseCharacter
{
	protected ChineseCharacter parent;
	private ChineseCharacterMovableType movableType;

	public abstract void typeset(ChineseCharacterTypesetter writer);

	public abstract void print(ChineseCharacterPrinter printer);

	public ChineseCharacterMovableType getMovableType()
	{
		return movableType;
	}

	public void setMovableType(ChineseCharacterMovableType movableType)
	{
		this.movableType = movableType;
	}
}