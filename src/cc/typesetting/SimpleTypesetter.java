package cc.typesetting;

import java.awt.Point;

import cc.core.ChineseCharacterBase;
import cc.core.ChineseCharacterCombination;
import cc.moveable_type.ImageMoveableType;

public class SimpleTypesetter implements ChineseCharacterTypesetter
{
	@Override
	public void setBase(ChineseCharacterBase base)
	{
		ImageMoveableType moveableType = new ImageMoveableType();
		moveableType.setRegion(new Point(100, 100));
		moveableType.setPosition(new Point(0, 0));
		moveableType.setScaler(new Point(100, 100));
		base.setMovableType(moveableType);
		return;
	}

	@Override
	public void setCombination(ChineseCharacterCombination combination)
	{
		for (int i = 0; i < combination.getType().getNumberOfChildren(); ++i)
			combination.getChildren()[i].typeset(this);
		Point first = combination.getChildren()[0].getArea();
		Point second = combination.getChildren()[1].getArea();
		ImageMoveableType moveableType = new ImageMoveableType();
		switch (combination.getType())
		{
		case horizontal:
			combination.getArea().x = Math.max(first.x, second.x);
			combination.getArea().y = first.y + second.y;
			break;
		case vertical:
			combination.getArea().x = first.x + second.x;
			combination.getArea().y = Math.max(first.y, second.y);
			break;
		case wrap:
			combination.getArea().x = first.x << 1;
			combination.getArea().y = first.y << 1;
			break;
		}
		return;
	}
}
