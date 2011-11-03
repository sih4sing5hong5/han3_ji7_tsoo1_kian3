package cc.moveable_type;

import java.awt.Point;

public class ImageMoveableType implements ChineseCharacterMovableType
{
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
