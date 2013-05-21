package cc.setting.image;

import java.awt.Point;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 圖片活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>ImageMoveableType</code>）。<code>ImageMoveableType</code>
 * 在列印時才讀取字體，處理時不考慮部件的差異，皆視為同樣大小，每個部件當做矩形，記錄預計長寬（<code>region</code>）及位置（
 * <code>position</code>），目前長寬（<code>scaler</code> ）是給合體活字儲存紀錄的。
 * 
 * @author Ihc
 */
public class SimpleImageSetter implements ChineseCharacterTypeSetter
{
	@Override
	public ImageMoveableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		ImageMoveableTypeWen moveableType = new ImageMoveableTypeWen(parent,
				chineseCharacterWen);
		moveableType.setRegion(new Point(100, 100));
		moveableType.setPosition(new Point(0, 0));
		moveableType.setScaler(new Point(100, 100));
		return moveableType;
	}

	@Override
	public ImageMoveableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		ImageMoveableTypeTzu imageMoveableTypeTzu = new ImageMoveableTypeTzu(
				parent, chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		for (int i = 0; i < childrenSize; ++i)
		{
			imageMoveableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this, parent);
		}
		ImageMoveableType firstChild = (ImageMoveableType) imageMoveableTypeTzu
				.getChildren()[0];
		ImageMoveableType secondChild = (ImageMoveableType) imageMoveableTypeTzu
				.getChildren()[1];
		Point firstRegion = firstChild.getRegion();
		Point secondRegion = secondChild.getRegion();
		imageMoveableTypeTzu.setRegion(new Point());
		switch (chineseCharacterTzu.getType())
		{
		default:
			System.out.println("無支援，先用橫的組");
		case horizontal:
			imageMoveableTypeTzu.getRegion().x = firstRegion.x + secondRegion.x;
			imageMoveableTypeTzu.getRegion().y = Math.max(firstRegion.y,
					secondRegion.y);
			firstRegion.y = secondRegion.y = imageMoveableTypeTzu.getRegion().y;
			secondChild.setPosition(new Point(firstRegion.x, 0));
			break;
		case vertical:
			imageMoveableTypeTzu.getRegion().x = Math.max(firstRegion.x,
					secondRegion.x);
			imageMoveableTypeTzu.getRegion().y = firstRegion.y + secondRegion.y;
			firstRegion.x = secondRegion.x = imageMoveableTypeTzu.getRegion().x;
			secondChild.setPosition(new Point(0, firstRegion.y));
			break;
		case wrap:
			imageMoveableTypeTzu.getRegion().x = firstRegion.x << 1;
			imageMoveableTypeTzu.getRegion().y = firstRegion.y << 1;
			firstChild.setRegion(imageMoveableTypeTzu.getRegion());
			secondChild.setPosition(new Point(firstRegion.x >> 1,
					firstRegion.y >> 1));
			break;
		}
		imageMoveableTypeTzu.setPosition(new Point(0, 0));
		imageMoveableTypeTzu.setScaler(imageMoveableTypeTzu.getRegion());
		return imageMoveableTypeTzu;
	}
}
