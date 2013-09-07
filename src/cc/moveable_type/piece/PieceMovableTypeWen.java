package cc.moveable_type.piece;

import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.rectangular_area.活字單元;

/**
 * 物件活字樹狀結構的葉子。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，
 * <code>RectangularArea</code>型態 ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * 
 * @author Ihc
 * @see ChineseCharacterMovableTypeWen
 */
public class PieceMovableTypeWen extends ChineseCharacterMovableTypeWen
		implements PieceMovableType
{
	/**
	 * 物件活字
	 */
	private final 活字單元 rectangularArea;

	/**
	 * 建立字活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterWen
	 *            文部件結構
	 * @param rectangularArea
	 *            物件活字
	 */
	public PieceMovableTypeWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen,
			活字單元 rectangularArea)
	{
		super(parent, chineseCharacterWen);
		this.rectangularArea = rectangularArea;
	}

	@Override
	public 活字單元 getPiece()
	{
		return rectangularArea;
	}

	// @Override
	// public void setPiece(RectangularArea rectangularArea)
	// {
	// this.rectangularArea = rectangularArea;
	// }
}
