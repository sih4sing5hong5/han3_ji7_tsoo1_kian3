/**
 * 
 */
package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class MergePieceSetter extends SimplePieceSetter
{
	protected final String TZU_MODEL = "意";
	protected final Rectangle2D TZU_MODEL_TERRITORY;

	/**
	 * 
	 * @param fontRenderContext
	 * @param fontName
	 * @param fontStyle
	 * @param fontResolution
	 */
	public MergePieceSetter(FontRenderContext fontRenderContext,
			String fontName, int fontStyle, int fontResolution)
	{
		super(fontRenderContext, fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				TZU_MODEL);
		TZU_MODEL_TERRITORY = glyphVector.getOutline().getBounds2D();
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterTzu chineseCharacterTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				chineseCharacterTzu);
		pieceMovableTypeTzu.setPiece(new RectangularArea(TZU_MODEL_TERRITORY));

		setChildren(pieceMovableTypeTzu, chineseCharacterTzu);

		return pieceMovableTypeTzu;
	}

}
