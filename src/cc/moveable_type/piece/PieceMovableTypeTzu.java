/**
 * 
 */
package cc.moveable_type.piece;

import cc.core.ChineseCharacter;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class PieceMovableTypeTzu extends ChineseCharacterMovableTypeTzu
		implements PieceMovableType
{
	private RectangularArea rectangularArea;

	public PieceMovableTypeTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacter chineseCharacter)
	{
		super(parent, chineseCharacter);
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
