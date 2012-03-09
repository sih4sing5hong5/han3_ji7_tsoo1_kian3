package cc.moveable_type;

import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterPrinter;

public abstract class ChineseCharacterMovableType
{
	protected ChineseCharacter chineseCharacter;
	protected ChineseCharacterMovableType parent;

	public ChineseCharacterMovableType(ChineseCharacter chineseCharacter)
	{
		this.chineseCharacter = chineseCharacter;
		this.parent = null;
	}

	public abstract void print(ChineseCharacterPrinter printer);

	public ChineseCharacter getChineseCharacter()
	{
		return chineseCharacter;
	}

	public ChineseCharacterMovableType getParent()
	{
		return parent;
	}

	public void setParent(ChineseCharacterMovableType parent)
	{
		this.parent = parent;
	}
}
