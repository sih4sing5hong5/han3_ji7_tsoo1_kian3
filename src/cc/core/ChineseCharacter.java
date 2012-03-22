package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.setting.ChineseCharacterTypeSetter;

public abstract class ChineseCharacter
{
	protected ChineseCharacter parent;

	public abstract ChineseCharacterMovableType typeset(ChineseCharacterTypeSetter writer);
}