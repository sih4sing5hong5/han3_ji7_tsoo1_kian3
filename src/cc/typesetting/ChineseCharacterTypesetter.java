package cc.typesetting;

import cc.core.ChineseCharacterBase;
import cc.core.ChineseCharacterCombination;

public interface ChineseCharacterTypesetter
{
	void writeBase(ChineseCharacterBase base);
	void writeCombination(ChineseCharacterCombination combination);
}
