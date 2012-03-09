package cc.moveable_type.image;

import java.awt.Point;

import cc.core.ChineseCharacter;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;

public interface ImageMoveableType
{
	public Point getRegion();

	public Point getPosition();

	public Point getScaler();
}
