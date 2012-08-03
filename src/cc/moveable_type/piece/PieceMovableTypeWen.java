package cc.moveable_type.piece;

import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class PieceMovableTypeWen extends ChineseCharacterMovableTypeWen
		implements PieceMovableType
{
	private RectangularArea rectangularArea;

	public PieceMovableTypeWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		super(parent, chineseCharacterWen);
		rectangularArea = new RectangularArea();
	}

	@Override
	public RectangularArea getPiece()
	{
		return rectangularArea;
	}

	@Override
	public void setPiece(RectangularArea rectangularArea)
	{
		this.rectangularArea = rectangularArea;
	}
}
