package cc.printing;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

public interface ChineseCharacterTypePrinter
{
	void printWen(ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen);

	void printTzu(ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu);
}
