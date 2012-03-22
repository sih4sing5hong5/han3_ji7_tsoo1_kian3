package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterTypePrinter;

public class ChineseCharacterMovableTypeTzu extends ChineseCharacterMovableType
{
	public ChineseCharacterMovableTypeTzu(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	protected ChineseCharacterMovableType[] children;

	@Override
	public void adjust(ChineseCharacterTypeAdjuster adjuster)
	{
		adjuster.adjustTzu(this);
		return;
	}

	@Override
	public void print(ChineseCharacterTypePrinter printer)
	{
		printer.printTzu(this);
		return;
	}

	public ChineseCharacterMovableType[] getChildren()
	{
		return children;
	}

	public void setChildren(ChineseCharacterMovableType[] children)
	{
		this.children = children;
	}
}
