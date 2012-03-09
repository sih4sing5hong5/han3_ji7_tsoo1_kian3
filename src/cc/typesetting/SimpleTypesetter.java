package cc.typesetting;

import java.awt.Point;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;

public class SimpleTypesetter implements ChineseCharacterTypesetter
{
	@Override
	public ImageMoveableTypeWen setWen(ChineseCharacterWen chineseCharacterWen)
	{
		ImageMoveableTypeWen moveableType = new ImageMoveableTypeWen(
				chineseCharacterWen);
		moveableType.setRegion(new Point(100, 100));
		moveableType.setPosition(new Point(0, 0));
		moveableType.setScaler(new Point(100, 100));
		return moveableType;
	}

	@Override
	public ImageMoveableTypeTzu setTzu(ChineseCharacterTzu chineseCharacterTzu)
	{
		ImageMoveableTypeTzu imageMoveableTypeTzu = new ImageMoveableTypeTzu(
				chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		imageMoveableTypeTzu
				.setChildren(new ChineseCharacterMovableType[childrenSize]);
		for (int i = 0; i < childrenSize; ++i)
		{
			imageMoveableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this);
			imageMoveableTypeTzu.getChildren()[i]
					.setParent(imageMoveableTypeTzu);
		}
		Point first = ((ImageMoveableType) imageMoveableTypeTzu.getChildren()[0])
				.getRegion();
		Point second = ((ImageMoveableType) imageMoveableTypeTzu.getChildren()[1])
				.getRegion();
		imageMoveableTypeTzu.setRegion(new Point());
		switch (chineseCharacterTzu.getType())
		{
		case horizontal:
			imageMoveableTypeTzu.getRegion().x = Math.max(first.x, second.x);
			imageMoveableTypeTzu.getRegion().y = first.y + second.y;
			break;
		case vertical:
			imageMoveableTypeTzu.getRegion().x = first.x + second.x;
			imageMoveableTypeTzu.getRegion().y = Math.max(first.y, second.y);
			break;
		case wrap:
			imageMoveableTypeTzu.getRegion().x = first.x << 1;
			imageMoveableTypeTzu.getRegion().y = first.y << 1;
			break;
		}
		return imageMoveableTypeTzu;
	}
}
