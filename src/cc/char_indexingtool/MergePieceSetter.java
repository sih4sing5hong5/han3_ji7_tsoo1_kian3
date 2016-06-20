/**
 * 
 */
package cc.char_indexingtool;

import java.awt.font.FontRenderContext;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.SeprateMovabletype;
import idsrend.charcomponent.FinalCharComponent;

/**
 * 活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>ChineseCharacterMovableType</code>）。
 * <p>
 * MergePiece看兩兩部件大小調整同高或同寬後再組合，彈性大，但現階段受加粗邊角問題影響。
 * 
 * @author Ihc
 */
public class MergePieceSetter extends SimplePieceSetter
{
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
	public MergePieceSetter(String fontName, int fontStyle, int fontResolution,
			FontRenderContext fontRenderContext)
	{
		super(fontName, fontStyle, fontResolution, fontRenderContext);
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			FinalCharComponent chineseCharacterTzu)
	{
		SeprateMovabletype rectangularArea = new SeprateMovabletype();
		rectangularArea.設字範圍(tzuModelTerritory);
		rectangularArea.設目標範圍(tzuModelTerritory);
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				parent, chineseCharacterTzu, rectangularArea);

		setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);

		return pieceMovableTypeTzu;
	}
}
