/**
 * 
 */
package cc.movabletype;

import idsrend.charcomponent.FinalCharComponent;

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
	private final SeprateMovabletype rectangularArea;

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
			FinalCharComponent chineseCharacterTzu,
			SeprateMovabletype rectangularArea)
	{
		super(parent, chineseCharacterTzu);
		this.rectangularArea = rectangularArea;
	}

	@Override
	public SeprateMovabletype getPiece()
	{
		return rectangularArea;
	}

	/**
	 * 取得合體活字下各個元件的活字物件
	 * 
	 * @return 各個元件的活字物件
	 */
	public SeprateMovabletype[] 取得活字物件()
	{
		SeprateMovabletype[] 活字物件 = new SeprateMovabletype[getChildren().length];
		for (int i = 0; i < getChildren().length; ++i)
			活字物件[i] = new SeprateMovabletype((SeprateMovabletype)
					((PieceMovableType) getChildren()[i]).getPiece());
		return 活字物件;
	}
}
