package cc.printing;

import cc.core.ChineseCharacterBase;
import cc.core.ChineseCharacterCombination;

public interface ChineseCharacterPrinter
{
	void printBase(ChineseCharacterBase base);

	void printCombination(ChineseCharacterCombination combination);
}
