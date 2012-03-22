package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterTypePrinter;

public abstract class ChineseCharacterMovableType
{
	protected ChineseCharacter chineseCharacter;
	protected ChineseCharacterMovableType parent;

	public ChineseCharacterMovableType(ChineseCharacter chineseCharacter)
	{
		this.chineseCharacter = chineseCharacter;
		this.parent = null;
	}

	public abstract void adjust(ChineseCharacterTypeAdjuster adjuster);

	public abstract void print(ChineseCharacterTypePrinter printer);

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
