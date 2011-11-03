package cc.typesetting;

import cc.core.ChineseCharacterBase;
import cc.core.ChineseCharacterCombination;

public interface ChineseCharacterTypesetter
{
	void setBase(ChineseCharacterBase base);

	void setCombination(ChineseCharacterCombination combination);
}
