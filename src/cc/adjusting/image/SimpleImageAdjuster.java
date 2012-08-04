package cc.adjusting.image;

import java.awt.Point;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;

/**
 * 圖片活字調整工具。調整<code>ImageMoveableType</code>，<code>ImageMoveableType</code>
 * 在列印時才讀取字體，處理時不考慮部件的差異，皆視為同樣大小，每個部件當做矩形，記錄預計長寬（<code>region</code>）及位置（
 * <code>position</code>），目前長寬（<code>scaler</code>）是給合體活字儲存紀錄的。
 * 
 * @author Ihc
 */
public class SimpleImageAdjuster implements ChineseCharacterTypeAdjuster
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
			Point region = new Point(
					(int) Math.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getRegion().x * xScaler),
					(int) Math.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getRegion().y * yScaler));
			((ImageMoveableType) imageMoveableTypeTzu.getChildren()[i])
					.setRegion(region);
			Point position = new Point(
					(int) Math.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getPosition().x * xScaler),
					(int) Math.floor(((ImageMoveableType) imageMoveableTypeTzu
							.getChildren()[i]).getPosition().y * yScaler));
			((ImageMoveableType) imageMoveableTypeTzu.getChildren()[i])
					.setPosition(position);
			imageMoveableTypeTzu.getChildren()[i].adjust(this);
		}
		return;
	}
}
