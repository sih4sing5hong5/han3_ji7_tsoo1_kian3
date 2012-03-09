package cc.printing;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

public interface ChineseCharacterPrinter
{
	void printWen(ChineseCharacterMovableTypeWen wen);

	void printTzu(ChineseCharacterMovableTypeTzu tzu);
}
