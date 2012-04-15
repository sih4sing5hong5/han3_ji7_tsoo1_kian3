/**
 * 
 */
package cc.setting.area;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.area.AreaMovableType;
import cc.moveable_type.area.AreaMovableTypeTzu;
import cc.moveable_type.area.AreaMovableTypeWen;
import cc.moveable_type.area.AreaTool;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * @author Ihc
 * 
 */
public class SimpleAreaSetter implements ChineseCharacterTypeSetter
{
	private String fontName;
	private int fontStyle;
	private FontRenderContext fontRenderContext;

	public SimpleAreaSetter(String fontName, int fontStyle)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontRenderContext = new FontRenderContext(new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
	}

	@Override
	public AreaMovableTypeWen setWen(ChineseCharacterWen chineseCharacterWen)
	{
		AreaMovableTypeWen shapeMovableTypeWen = new AreaMovableTypeWen(
				chineseCharacterWen);
		Font font = new Font(fontName, fontStyle, 100);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				chineseCharacterWen.getChars());
		Area area = new Area(glyphVector.getOutline());
		AreaTool.moveToOrigin(area);
		shapeMovableTypeWen.setArea(area);
		shapeMovableTypeWen.setBound((Rectangle2D) shapeMovableTypeWen.getArea().getBounds().clone());
		return shapeMovableTypeWen;
	}

	@Override
	public AreaMovableTypeTzu setTzu(ChineseCharacterTzu chineseCharacterTzu)
	{
		AreaMovableTypeTzu shapeMovableTypeTzu = new AreaMovableTypeTzu(
				chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		shapeMovableTypeTzu
				.setChildren(new ChineseCharacterMovableType[childrenSize]);
		for (int i = 0; i < childrenSize; ++i)
		{
			shapeMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this);
			shapeMovableTypeTzu.getChildren()[i].setParent(shapeMovableTypeTzu);
		}
		AreaMovableType firstChild = (AreaMovableType) shapeMovableTypeTzu
				.getChildren()[0], secondChild = (AreaMovableType) shapeMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();

		switch (chineseCharacterTzu.getType())
		{
		case horizontal:
			rectDouble.width = firstChild.getBound().getWidth()
					+ secondChild.getBound().getWidth();
			rectDouble.height = Math.max(firstChild.getBound().getHeight(),
					secondChild.getBound().getHeight());
			firstChild.getBound().setRect(0.0, 0.0,
					firstChild.getBound().getWidth(), rectDouble.height);
			secondChild.getBound().setRect(firstChild.getBound().getWidth(),
					0.0, secondChild.getBound().getWidth(), rectDouble.height);
			break;
		case vertical:
			rectDouble.width = Math.max(firstChild.getBound().getWidth(),
					secondChild.getBound().getWidth());
			rectDouble.height = firstChild.getBound().getHeight()
					+ secondChild.getBound().getHeight();
			firstChild.getBound().setRect(0.0, 0.0, rectDouble.width,
					firstChild.getBound().getHeight());
			secondChild.getBound().setRect(0.0,
					firstChild.getBound().getHeight(), rectDouble.width,
					secondChild.getBound().getHeight());
			break;
		case wrap:
			rectDouble.width = firstChild.getBound().getWidth() * 2;
			rectDouble.height = firstChild.getBound().getHeight() * 2;
			firstChild.getBound().setRect(0.0, 0.0, rectDouble.width,
					rectDouble.height);
			secondChild.getBound().setRect(
					(firstChild.getBound().getWidth() - secondChild.getBound()
							.getWidth()) / 2,
					(firstChild.getBound().getHeight() - secondChild.getBound()
							.getHeight()) / 2,
					secondChild.getBound().getWidth(),
					secondChild.getBound().getHeight());
			break;
		}
		shapeMovableTypeTzu.setArea(new Area(rectDouble));
		shapeMovableTypeTzu.setBound(rectDouble);
		return shapeMovableTypeTzu;
	}

}
