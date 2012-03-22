package cc.adjusting;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

public interface ChineseCharacterTypeAdjuster
{
	public void adjustWen(ChineseCharacterMovableTypeWen wen);
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu);
}
