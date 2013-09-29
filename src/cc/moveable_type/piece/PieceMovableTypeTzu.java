/**
 * 
 */
package cc.moveable_type.piece;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.分離活字;

/**
 * 物件活字樹狀結構的上層節點。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，
 * <code>RectangularArea</code>型態 ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * 
 * @author Ihc
 * @see ChineseCharacterMovableTypeTzu
 */
public class PieceMovableTypeTzu extends ChineseCharacterMovableTypeTzu
		implements PieceMovableType
{
	/**
	 * 物件活字
	 */
	private final 分離活字 rectangularArea;

	/**
	 * 建立字活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterTzu
	 *            字部件結構
	 * @param rectangularArea
	 *            物件活字
	 */
	public PieceMovableTypeTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu,
			分離活字 rectangularArea)
	{
		super(parent, chineseCharacterTzu);
		this.rectangularArea = rectangularArea;
	}

	@Override
	public 分離活字 getPiece()
	{
		return rectangularArea;
	}

	/**
	 * 取得合體活字下各個元件的活字物件
	 * 
	 * @return 各個元件的活字物件
	 */
	public 分離活字[] 取得活字物件()
	{
		分離活字[] 活字物件 = new 分離活字[getChildren().length];
		for (int i = 0; i < getChildren().length; ++i)
			活字物件[i] = new 分離活字((分離活字)
					((PieceMovableType) getChildren()[i]).getPiece());
		return 活字物件;
	}
}
