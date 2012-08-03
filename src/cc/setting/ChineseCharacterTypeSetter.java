package cc.setting;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;

public interface ChineseCharacterTypeSetter
{
	public ChineseCharacterMovableType setWen(
			ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen);

	public ChineseCharacterMovableType setTzu(
			ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu);
}
