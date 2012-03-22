package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterTypePrinter;

public class ChineseCharacterMovableTypeWen extends ChineseCharacterMovableType
{
	public ChineseCharacterMovableTypeWen(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	@Override
	public void adjust(ChineseCharacterTypeAdjuster adjuster)
	{
		adjuster.adjustWen(this);
		return;
	}

	@Override
	public void print(ChineseCharacterTypePrinter printer)
	{
		printer.printWen(this);
		return;
	}
}
