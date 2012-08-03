/**
 * 
 */
package cc.setting.piece;

import java.awt.font.FontRenderContext;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class MergePieceSetter extends SimplePieceSetter
{

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
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				parent, chineseCharacterTzu, new RectangularArea(
						TZU_MODEL_TERRITORY));
		// pieceMovableTypeTzu.setPiece();

		setChildren(pieceMovableTypeTzu, chineseCharacterTzu);

		return pieceMovableTypeTzu;
	}

}
