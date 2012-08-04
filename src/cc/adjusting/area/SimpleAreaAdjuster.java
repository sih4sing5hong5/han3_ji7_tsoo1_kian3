package cc.adjusting.area;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.area.AreaMovableType;
import cc.moveable_type.area.AreaMovableTypeTzu;
import cc.moveable_type.area.AreaMovableTypeWen;

/**
 * 區塊活字調整工具。調整<code>AreaMovableType</code>，<code>AreaMovableType</code>
 * 在設定時就讀取字體，處理時要考慮部件的差異，<code>area</code>（區塊活字）記錄目前長寬位置及部件形狀，<code>bound</code>
 * 記錄預計長寬位置。
 * 
 * @author Ihc
 */
public class SimpleAreaAdjuster implements ChineseCharacterTypeAdjuster
{
	@Override
	public void adjustWen(ChineseCharacterMovableTypeWen wen)
	{
		AreaMovableTypeWen areaMovableTypeWen = (AreaMovableTypeWen) wen;
		AffineTransform affineTransform = getAffineTransform(areaMovableTypeWen);
		areaMovableTypeWen.getArea().transform(affineTransform);
		return;
	}

	@Override
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		AreaMovableTypeTzu areaMovableTypeTzu = (AreaMovableTypeTzu) tzu;
		AffineTransform affineTransform = getAffineTransform(areaMovableTypeTzu);
		areaMovableTypeTzu.getArea().transform(affineTransform);
		for (int i = 0; i < areaMovableTypeTzu.getChildren().length; ++i)
		{
			AreaMovableType child = (AreaMovableType) areaMovableTypeTzu
					.getChildren()[i];
			Rectangle2D bound = child.getBound();
			bound.setRect(bound.getX() * affineTransform.getScaleX(),
					bound.getY() * affineTransform.getScaleY(),
					bound.getWidth() * affineTransform.getScaleX(),
					bound.getHeight() * affineTransform.getScaleY());
			areaMovableTypeTzu.getChildren()[i].adjust(this);
		}
	}

	/**
	 * 依區塊活字目前和預計的長寬，產生出能轉換成預計長寬的縮放矩陣
	 * 
	 * @param areaMovableType
	 *            區塊活字
	 * @return 縮放矩陣
	 */
	private AffineTransform getAffineTransform(AreaMovableType areaMovableType)
	{
		Rectangle2D bound = areaMovableType.getBound();
		Rectangle2D rectangle2d = areaMovableType.getArea().getBounds2D();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(bound.getWidth() / rectangle2d.getWidth(),
				bound.getHeight() / rectangle2d.getHeight());
		return affineTransform;
	}
}
