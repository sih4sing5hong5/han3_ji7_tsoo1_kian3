package cc.moveable_type.image;

import java.awt.Point;

import cc.core.ChineseCharacter;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

public class ImageMoveableTypeWen extends ChineseCharacterMovableTypeWen implements ImageMoveableType
{
	public ImageMoveableTypeWen(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	private Point region, position, scaler;

	public Point getRegion()
	{
		return region;
	}

	public void setRegion(Point region)
	{
		this.region = region;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}

	public Point getScaler()
	{
		return scaler;
	}

	public void setScaler(Point scaler)
	{
		this.scaler = scaler;
	}
}
