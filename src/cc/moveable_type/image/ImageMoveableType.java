package cc.moveable_type.image;

import java.awt.Point;

public interface ImageMoveableType {
	public Point getRegion();

	public void setRegion(Point region);

	public Point getPosition();

	public void setPosition(Point position);

	public Point getScaler();

	public void setScaler(Point scaler);
}
