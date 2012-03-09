package cc.typesetting;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableType;

public interface ChineseCharacterTypesetter
{
	public ChineseCharacterMovableType setWen(ChineseCharacterWen wen);
	public ChineseCharacterMovableType setTzu(ChineseCharacterTzu tzu);
}
