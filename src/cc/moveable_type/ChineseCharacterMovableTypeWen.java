package cc.moveable_type;

import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterPrinter;

public abstract class ChineseCharacterMovableTypeWen extends
		ChineseCharacterMovableType
{
	public ChineseCharacterMovableTypeWen(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	@Override
	public void print(ChineseCharacterPrinter printer)
	{
		printer.printWen(this);
		return;
	}
}
