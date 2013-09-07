/**
 * 
 */
package cc.setting.piece;

import java.awt.font.FontRenderContext;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.活字單元;

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
			ChineseCharacterTzu chineseCharacterTzu)
	{
		活字單元 rectangularArea = new 平面幾何();
		rectangularArea.設目標範圍(tzuModelTerritory);
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				parent, chineseCharacterTzu, rectangularArea);

		setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);

		return pieceMovableTypeTzu;
	}
}
