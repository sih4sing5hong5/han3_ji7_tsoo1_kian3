package cc.core;

import cc.moveable_type.ChineseCharacterMovableType;
import cc.typesetting.ChineseCharacterTypesetter;

public abstract class ChineseCharacter
{
	protected ChineseCharacter parent;

	public abstract ChineseCharacterMovableType typeset(ChineseCharacterTypesetter writer);
}