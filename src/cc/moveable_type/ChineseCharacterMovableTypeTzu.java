package cc.moveable_type;

import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterPrinter;

public class ChineseCharacterMovableTypeTzu extends ChineseCharacterMovableType
{
	public ChineseCharacterMovableTypeTzu(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	protected ChineseCharacterMovableType[] children;

	@Override
	public void print(ChineseCharacterPrinter printer)
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
