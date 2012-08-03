package cc.setting.piece;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 物件活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>PieceMovableType</code>）。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，
 * <code>RectangularArea</code>型態 ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * 
 * @author Ihc
 */
public class SimplePieceSetter implements ChineseCharacterTypeSetter
{
	/**
	 * 活字字型的名稱
	 */
	private String fontName;
	/**
	 * 活字字型的選項
	 */
	private int fontStyle;
	/**
	 * 活字的點距
	 */
	private int fontResolution;
	/**
	 * 活字的渲染屬性
	 */
	protected FontRenderContext fontRenderContext;
	/**
	 * 活字的字體
	 */
	protected Font font;
	/**
	 * 合體字組合符號參考的規範字
	 */
	protected final String tzuModelCharacter = "意";
	/**
	 * 合體字組合符號參考的位置及大小
	 */
	protected final Rectangle2D tzuModelTerritory;
	/**
	 * 獨體字缺字的替代物件
	 */
	protected final Area pieceForNoBuiltInWen;

	/**
	 * 建立物件活字設定工具
	 * 
	 * @param fontName
	 *            活字字型的名稱
	 * @param fontStyle
	 *            活字字型的選項
	 * @param fontResolution
	 *            活字的點距
	 * @param fontRenderContext
	 *            活字的渲染屬性
	 */
	public SimplePieceSetter(String fontName, int fontStyle,
			int fontResolution, FontRenderContext fontRenderContext)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontResolution = fontResolution;
		this.fontRenderContext = fontRenderContext;
		this.font = new Font(fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				this.tzuModelCharacter);
		this.tzuModelTerritory = glyphVector.getOutline().getBounds2D();
		BasicStroke basicStroke = new BasicStroke();
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(tzuModelTerritory));
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		// PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
		// parent, chineseCharacterWen);
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
		// pieceMovableTypeWen.setPiece(rectangularArea);

		PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
				parent, chineseCharacterWen, rectangularArea);
		return pieceMovableTypeWen;
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				parent, chineseCharacterTzu, new RectangularArea());
		// pieceMovableTypeTzu.setPiece();

		setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);

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

		if (pieceMovableTypeTzu.getParent() == null)
			pieceMovableTypeTzu.getPiece().setTerritory(tzuModelTerritory);

		return pieceMovableTypeTzu;
	}

	/**
	 * 字體缺字的替代方案
	 * 
	 * @param chineseCharacterWen
	 *            所缺的字部件
	 * @return 替代圖案或文字
	 */
	protected RectangularArea findWenForNoBuiltIn(
			ChineseCharacterWen chineseCharacterWen)
	{
		return new RectangularArea(pieceForNoBuiltInWen);
	}

	/**
	 * 設定底下活字部件
	 * 
	 * @param chineseCharacterMovableTypeTzu
	 *            目前設定的合體活字
	 * @param chineseCharacterTzu
	 *            目前設定的字部件
	 */
	protected void setChildrenRecursively(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		for (int i = 0; i < chineseCharacterMovableTypeTzu.getChildren().length; ++i)
		{
			chineseCharacterMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.getChildren()[i].typeset(this,
					chineseCharacterMovableTypeTzu);
		}
		return;
	}

	/**
	 * 水平組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
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

	/**
	 * 垂直組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
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

	/**
	 * 包圍組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
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
