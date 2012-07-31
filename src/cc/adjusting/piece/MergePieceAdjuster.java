/**
 * 
 */
package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class MergePieceAdjuster extends SimplePieceAdjuster
{

	/**
	 * 
	 */
	public MergePieceAdjuster()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cc.adjusting.ChineseCharacterTypeAdjuster#adjustWen(cc.moveable_type.
	 *      ChineseCharacterMovableTypeWen)
	 */
	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		return;
	}

	@Override
	public void adjustTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			pieceMovableTypeTzu.getChildren()[i].adjust(this);
		}
		switch (((ChineseCharacterTzu) pieceMovableTypeTzu
				.getChineseCharacter()).getType())
		{
		case horizontal:
			horizontalMerging(pieceMovableTypeTzu);
			break;
		case vertical:
			verticalMerging(pieceMovableTypeTzu);
			break;
		case wrap:
			wrapMerging(pieceMovableTypeTzu);
			break;
		}
		return;
	}

	/**
	 * 
	 * @param pieceMovableTypeTzu
	 */
	void horizontalMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType left = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], right = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		PieceMovableType greater = null, smaller = null;
		if (left.getPiece().getTerritory().getHeight() > right.getPiece()
				.getTerritory().getHeight())
		{
			greater = left;
			smaller = right;
		}
		else
		{
			greater = right;
			smaller = left;
		}
		AffineTransform shrinkTransform = getAffineTransform(smaller.getPiece()
				.getTerritory().getHeight()
				/ greater.getPiece().getTerritory().getHeight());
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);

		double miniPos = 0.0, maxiPos = right.getPiece().getTerritory().getX();
		while (miniPos + getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);
			right.getPiece().moveToOrigin();
			right.getPiece().moveTo(middlePos, 0);
			if (areIntersected(left.getPiece(), right.getPiece()))
				miniPos = middlePos;
			else
				maxiPos = middlePos;
		}
		right.getPiece().moveToOrigin();
		right.getPiece().moveTo(miniPos, 0);
		right.getPiece().getTerritory().x = miniPos;
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(left.getPiece());
		pieceMovableTypeTzu.getPiece().add(right.getPiece());
		right.getPiece().moveToOrigin();// TODO Territory功能要再想好
		return;
	}

	/**
	 * 
	 * @param pieceMovableTypeTzu
	 */
	void verticalMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType up = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], down = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		PieceMovableType greater = null, smaller = null;
		if (up.getPiece().getTerritory().getWidth() > down.getPiece()
				.getTerritory().getWidth())
		{
			greater = up;
			smaller = down;
		}
		else
		{
			greater = down;
			smaller = up;
		}
		AffineTransform shrinkTransform = getAffineTransform(smaller.getPiece()
				.getTerritory().getHeight()
				/ greater.getPiece().getTerritory().getHeight());
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);

		double miniPos = 0.0, maxiPos = down.getPiece().getTerritory().getY();
		while (miniPos + getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);
			down.getPiece().moveToOrigin();
			down.getPiece().moveTo(0, middlePos);
			if (areIntersected(up.getPiece(), down.getPiece()))
				miniPos = middlePos;
			else
				maxiPos = middlePos;
		}
		down.getPiece().moveToOrigin();
		down.getPiece().moveTo(0, miniPos);
		down.getPiece().getTerritory().y = miniPos;
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(up.getPiece());
		pieceMovableTypeTzu.getPiece().add(down.getPiece());
		down.getPiece().moveToOrigin();// TODO Territory功能要再想好
		return;
	}

	/**
	 * 
	 * @param pieceMovableTypeTzu
	 */
	void wrapMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType out = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], in = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(out.getPiece());
		pieceMovableTypeTzu.getPiece().add(in.getPiece());
		return;
	}

	protected AffineTransform getAffineTransform(double scaler)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(scaler, scaler);
		return affineTransform;
	}

	protected AffineTransform getAffineTransform(double scalerX, double scalerY)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(scalerX, scalerY);
		return affineTransform;
	}

	protected boolean areIntersected(RectangularArea first,
			RectangularArea second)
	{
		RectangularArea rectangularArea = new RectangularArea(first);
		rectangularArea.subtract(second);
		return !rectangularArea.equals(first);
	}
}
