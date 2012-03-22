package cc.adjusting.image;

import java.awt.Point;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;

public class SampleImageAdjuster implements ChineseCharacterTypeAdjuster
{
	@Override
	public void adjustWen(ChineseCharacterMovableTypeWen wen)
	{
		ImageMoveableTypeWen imageMoveableTypeWen = (ImageMoveableTypeWen) wen;
		imageMoveableTypeWen.setScaler(imageMoveableTypeWen.getRegion());
		return;
	}

	@Override
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		ImageMoveableTypeTzu imageMoveableTypeTzu = (ImageMoveableTypeTzu) tzu;
		float xScaler = (float) imageMoveableTypeTzu.getRegion().x
				/ (float) imageMoveableTypeTzu.getScaler().x;
		float yScaler = (float) imageMoveableTypeTzu.getRegion().y
				/ (float) imageMoveableTypeTzu.getScaler().y;
		imageMoveableTypeTzu.setScaler(imageMoveableTypeTzu.getRegion());
		for (int i = 0; i < imageMoveableTypeTzu.getChildren().length; ++i)
		{
			Point region = new Point((int) Math
					.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getRegion().x
							* xScaler), (int) Math
					.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getRegion().y
							* yScaler));
			((ImageMoveableType) imageMoveableTypeTzu.getChildren()[i])
					.setRegion(region);
			//TODO position
			imageMoveableTypeTzu.getChildren()[i].adjust(this);
		}
		return;
	}
}
