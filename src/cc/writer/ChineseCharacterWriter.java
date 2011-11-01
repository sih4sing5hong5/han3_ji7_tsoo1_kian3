package cc.writer;

import cc.core.ChineseCharacterBase;
import cc.core.ChineseCharacterCombination;

public interface ChineseCharacterWriter
{
	void writeBase(ChineseCharacterBase base);
	void writeCombination(ChineseCharacterCombination combination);
}
