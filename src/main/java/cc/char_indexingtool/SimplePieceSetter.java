package cc.char_indexingtool;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.PieceMovableTypeWen;
import cc.movabletype.SeprateMovabletype;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;
import cc.movabletype.PlaneGeometry;

/**
 * 物件活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>PieceMovableType</code>）。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，
 * <code>RectangularArea</code>型態 ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>SimplePiece</code>是在設定時兩兩配對後定框，調整時更改部件大小，但無法物件難實作距離貼近或拉開。
 * 
 * @author Ihc
 */
public abstract class SimplePieceSetter extends ObjMovableTypeBasicSettingTool
{
	/** 活字字型的名稱 */
	private String fontName;
	/** 活字字型的選項 */
	private int fontStyle;
	/** 活字的點距 */
	private int fontResolution;
	/** 活字的渲染屬性 */
	protected FontRenderContext fontRenderContext;
	/** 活字的字體 */
	protected Font font;

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
		super(null, null);
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontResolution = fontResolution;
		this.fontRenderContext = fontRenderContext;
		this.font = new Font(fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				SimplePieceSetter.tzuModelCharacter);
		this.tzuModelTerritory = glyphVector.getOutline().getBounds2D();
		BasicStroke basicStroke = new BasicStroke();
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(tzuModelTerritory));
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			NonFinalCharComponent chineseCharacterWen)
	{
		SeprateMovabletype rectangularArea = null;
		if (font.canDisplay(chineseCharacterWen.Unicode編號()))
		{
			GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
					chineseCharacterWen.部件組字式());
			rectangularArea = new SeprateMovabletype(new PlaneGeometry(glyphVector.getOutline()));
			rectangularArea.設字範圍(tzuModelTerritory);
			rectangularArea.設目標範圍(rectangularArea.這馬字範圍());
		}
		else
		{
			rectangularArea = findWenForNoBuiltIn(chineseCharacterWen);
		}
		rectangularArea.徙轉原點();

		PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
				parent, chineseCharacterWen, rectangularArea);
		return pieceMovableTypeWen;
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
			FinalCharComponent chineseCharacterTzu)
	{
		for (int i = 0; i < chineseCharacterMovableTypeTzu.getChildren().length; ++i)
		{
			chineseCharacterMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.底下元素()[i].typeset(this,
					chineseCharacterMovableTypeTzu);
		}
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
