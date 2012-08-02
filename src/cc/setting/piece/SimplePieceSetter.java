/**
 * 
 */
package cc.setting.piece;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * @author Ihc
 * 
 */
public class SimplePieceSetter implements ChineseCharacterTypeSetter
{
	private String fontName;
	private int fontStyle;
	private int fontResolution;
	protected FontRenderContext fontRenderContext;
	protected Font font;
	protected final String TZU_MODEL = "意";
	protected final Rectangle2D TZU_MODEL_TERRITORY;
	protected final Area TZU_MODEL_AREA;

	public SimplePieceSetter(FontRenderContext fontRenderContext,
			String fontName, int fontStyle, int fontResolution)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontResolution = fontResolution;
		this.fontRenderContext = fontRenderContext;
		this.font = new Font(fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				this.TZU_MODEL);
		this.TZU_MODEL_TERRITORY = glyphVector.getOutline().getBounds2D();
		BasicStroke basicStroke = new BasicStroke();
		this.TZU_MODEL_AREA = new Area(
				basicStroke.createStrokedShape(TZU_MODEL_TERRITORY));
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterWen chineseCharacterWen)
	{
		PieceMovableTypeWen shapeMovableTypeWen = new PieceMovableTypeWen(
				chineseCharacterWen);
		RectangularArea rectangularArea = null;
		if (font.canDisplay(chineseCharacterWen.getCodePoint()))
		{
			GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
					chineseCharacterWen.getChars());
			rectangularArea = new RectangularArea(glyphVector.getOutline());
		}
		else
		{
			rectangularArea = findWenForNoBuiltIn(chineseCharacterWen);
		}
		rectangularArea.moveToOrigin();
		shapeMovableTypeWen.setPiece(rectangularArea);
		return shapeMovableTypeWen;
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterTzu chineseCharacterTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				chineseCharacterTzu);
		pieceMovableTypeTzu.setPiece(new RectangularArea());

		setChildren(pieceMovableTypeTzu, chineseCharacterTzu);

		switch (chineseCharacterTzu.getType())
		{
		case horizontal:
			horizontalSetting(pieceMovableTypeTzu);
			break;
		case vertical:
			verticalSetting(pieceMovableTypeTzu);
			break;
		case wrap:
			wrapSetting(pieceMovableTypeTzu);
			break;
		}

		if (pieceMovableTypeTzu.getChineseCharacter().getParent() == null)
			pieceMovableTypeTzu.getPiece().setTerritory(TZU_MODEL_TERRITORY);

		return pieceMovableTypeTzu;
	}

	protected RectangularArea findWenForNoBuiltIn(
			ChineseCharacterWen chineseCharacterWen)
	{
		return new RectangularArea(TZU_MODEL_AREA);
	}

	protected void setChildren(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		chineseCharacterMovableTypeTzu
				.setChildren(new ChineseCharacterMovableType[childrenSize]);
		for (int i = 0; i < childrenSize; ++i)
		{
			chineseCharacterMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this);
			chineseCharacterMovableTypeTzu.getChildren()[i]
					.setParent(chineseCharacterMovableTypeTzu);
		}
		return;
	}

	protected void horizontalSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = firstChild.getPiece().getTerritory().getWidth()
				+ secondChild.getPiece().getTerritory().getWidth();
		rectDouble.height = Math
				.max(firstChild.getPiece().getTerritory().getHeight(),
						secondChild.getPiece().getTerritory().getHeight());
		pieceMovableTypeTzu.getPiece().setTerritoryDimension(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().add(new Area(rectDouble));
		firstChild
				.getPiece()
				.getTerritory()
				.setRect(0.0, 0.0,
						firstChild.getPiece().getTerritory().getWidth(),
						rectDouble.height);
		secondChild
				.getPiece()
				.getTerritory()
				.setRect(firstChild.getPiece().getTerritory().getWidth(), 0.0,
						secondChild.getPiece().getTerritory().getWidth(),
						rectDouble.height);
		return;

	}

	protected void verticalSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = Math.max(firstChild.getPiece().getTerritory()
				.getWidth(), secondChild.getPiece().getTerritory().getWidth());
		rectDouble.height = firstChild.getPiece().getTerritory().getHeight()
				+ secondChild.getPiece().getTerritory().getHeight();
		pieceMovableTypeTzu.getPiece().setTerritoryDimension(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().add(new Area(rectDouble));
		firstChild
				.getPiece()
				.getTerritory()
				.setRect(0.0, 0.0, rectDouble.width,
						firstChild.getPiece().getTerritory().getHeight());
		secondChild
				.getPiece()
				.getTerritory()
				.setRect(0.0, firstChild.getPiece().getTerritory().getHeight(),
						rectDouble.width,
						secondChild.getPiece().getTerritory().getHeight());
		return;
	}

	protected void wrapSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = firstChild.getPiece().getTerritory().getWidth() * 2;
		rectDouble.height = firstChild.getPiece().getTerritory().getHeight() * 2;
		pieceMovableTypeTzu.getPiece().setTerritoryDimension(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().add(new Area(rectDouble));
		firstChild.getPiece().getTerritory()
				.setRect(0.0, 0.0, rectDouble.width, rectDouble.height);
		secondChild
				.getPiece()
				.getTerritory()
				.setRect(
						(firstChild.getPiece().getTerritory().getWidth() - secondChild
								.getPiece().getTerritory().getWidth()) / 2,
						(firstChild.getPiece().getTerritory().getHeight() - secondChild
								.getPiece().getTerritory().getHeight()) / 2,
						secondChild.getPiece().getTerritory().getWidth(),
						secondChild.getPiece().getTerritory().getHeight());
		return;
	}

	public String getFontName()
	{
		return fontName;
	}

	public void setFontName(String fontName)
	{
		this.fontName = fontName;
	}

	public int getFontStyle()
	{
		return fontStyle;
	}

	public void setFontStyle(int fontStyle)
	{
		this.fontStyle = fontStyle;
	}

	public FontRenderContext getFontRenderContext()
	{
		return fontRenderContext;
	}

	public void setFontRenderContext(FontRenderContext fontRenderContext)
	{
		this.fontRenderContext = fontRenderContext;
	}

	public int getFontResolution()
	{
		return fontResolution;
	}

	public void setFontResolution(int fontResolution)
	{
		this.fontResolution = fontResolution;
	}

}
