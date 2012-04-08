/**
 * 
 */
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
 * @author Ihc
 * 
 */
public class SimpleAreaAdjuster implements ChineseCharacterTypeAdjuster
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.adjusting.ChineseCharacterTypeAdjuster#adjustWen(cc.moveable_type.
	 * ChineseCharacterMovableTypeWen)
	 */
	@Override
	public void adjustWen(ChineseCharacterMovableTypeWen wen)
	{
		AreaMovableTypeWen areaMovableTypeWen = (AreaMovableTypeWen) wen;
		AffineTransform affineTransform = getAffineTransform(areaMovableTypeWen);
		areaMovableTypeWen.getArea().transform(affineTransform);
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.adjusting.ChineseCharacterTypeAdjuster#adjustTzu(cc.moveable_type.
	 * ChineseCharacterMovableTypeTzu)
	 */
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
			bound.setRect(bound.getX() * affineTransform.getScaleX(), bound
					.getY()
					* affineTransform.getScaleY(), bound.getWidth()
					* affineTransform.getScaleX(), bound.getHeight()
					* affineTransform.getScaleY());
			// System.out.println(" " + affineTransform.getScaleX()
			// + affineTransform.getScaleY());
			areaMovableTypeTzu.getChildren()[i].adjust(this);
			// areaMovableTypeTzu.getArea().add(child.getArea());
		}
	}

	private AffineTransform getAffineTransform(AreaMovableType areaMovableType)
	{
		Rectangle2D bound = areaMovableType.getBound();
		Rectangle2D rectangle2d = areaMovableType.getArea().getBounds2D();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(rectangle2d.getWidth() / bound.getWidth(),
				rectangle2d.getHeight() / bound.getHeight());
		return affineTransform;
	}
}
