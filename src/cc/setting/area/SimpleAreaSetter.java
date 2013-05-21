package cc.setting.area;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.area.AreaMovableType;
import cc.moveable_type.area.AreaMovableTypeTzu;
import cc.moveable_type.area.AreaMovableTypeWen;
import cc.moveable_type.area.AreaTool;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 區塊活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>AreaMovableType</code>）。<code>AreaMovableType</code>
 * 在設定時就讀取字體，處理時要考慮部件的差異，<code>area</code>（區塊活字）記錄目前長寬位置及部件形狀，<code>bound</code>
 * 記錄預計長寬位置。
 * 
 * @author Ihc
 */
public class SimpleAreaSetter implements ChineseCharacterTypeSetter
{
	/** 建立物件活字設定工具 */
	protected final String fontName;
	/** 活字字型的選項 */
	protected final int fontStyle;
	/** 活字的點距 */
	protected final int fontResolution;
	/** 活字的渲染屬性 */
	protected final FontRenderContext fontRenderContext;

	/**
	 * 建立區塊活字設定工具
	 * 
	 * @param fontName
	 *            建立物件活字設定工具
	 * @param fontStyle
	 *            活字字型的選項
	 * @param fontResolution
	 *            活字的點距
	 * @param fontRenderContext
	 *            活字的渲染屬性
	 */
	public SimpleAreaSetter(String fontName, int fontStyle, int fontResolution,
			FontRenderContext fontRenderContext)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontResolution = fontResolution;
		this.fontRenderContext = fontRenderContext;
	}

	@Override
	public AreaMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		AreaMovableTypeWen shapeMovableTypeWen = new AreaMovableTypeWen(parent,
				chineseCharacterWen);
		Font font = new Font(fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				chineseCharacterWen.getChars());
		Area area = new Area(glyphVector.getOutline());
		AreaTool.moveToOrigin(area);
		shapeMovableTypeWen.setArea(area);
		shapeMovableTypeWen.setBound((Rectangle2D) shapeMovableTypeWen
				.getArea().getBounds2D().clone());
		return shapeMovableTypeWen;
	}

	@Override
	public AreaMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		AreaMovableTypeTzu shapeMovableTypeTzu = new AreaMovableTypeTzu(parent,
				chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		for (int i = 0; i < childrenSize; ++i)
		{
			shapeMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this, parent);
		}
		AreaMovableType firstChild = (AreaMovableType) shapeMovableTypeTzu
				.getChildren()[0], secondChild = (AreaMovableType) shapeMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();

		switch (chineseCharacterTzu.getType())
		{
		default:
			System.out.println("無支援，先用橫的組");
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

	/**
	 * 取得活字字型的名稱
	 * 
	 * @return 活字字型的名稱
	 */
	public String getFontName()
	{
		return fontName;
	}

	/**
	 * 取得活字字型的選項
	 * 
	 * @return 活字字型的選項
	 */
	public int getFontStyle()
	{
		return fontStyle;
	}

	/**
	 * 取得活字的點距
	 * 
	 * @return 活字的點距
	 */
	public int getFontResolution()
	{
		return fontResolution;
	}

	/**
	 * 取得活字的渲染屬性
	 * 
	 * @return 活字的渲染屬性
	 */
	public FontRenderContext getFontRenderContext()
	{
		return fontRenderContext;
	}
}
