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
		// PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen)
		// chineseCharacterMovableTypeWen;
		// pieceMovableTypeWen.getPiece().setTerritoryDimensionSameAsPiece();
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
		if (left.getPiece().getBounds2D().getHeight() > right.getPiece()
				.getBounds2D().getHeight())
		{
			greater = left;
			smaller = right;
		}
		else
		{
			greater = right;
			smaller = left;
		}
		// TODO 要哪一個尚未決定，不知為合正方形收縮記憶體會不足
		// AffineTransform shrinkTransform =
		// getAffineTransform(smaller.getPiece()
		// .getBounds2D().getHeight()
		// / greater.getPiece().getBounds2D().getHeight());
		AffineTransform shrinkTransform = getAffineTransform(1.0, smaller
				.getPiece().getBounds2D().getHeight()
				/ greater.getPiece().getBounds2D().getHeight());
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);
		// greaterPiece.setTerritoryDimensionSameAsPiece();

		double miniPos = 0.0, maxiPos = left.getPiece().getBounds2D()
				.getWidth();
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
		// right.getPiece().getTerritory().x = miniPos;
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(left.getPiece());
		pieceMovableTypeTzu.getPiece().add(right.getPiece());
		// pieceMovableTypeTzu.getPiece().setTerritoryDimensionSameAsPiece();
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
		if (up.getPiece().getBounds2D().getWidth() > down.getPiece()
				.getBounds2D().getWidth())
		{
			greater = up;
			smaller = down;
		}
		else
		{
			greater = down;
			smaller = up;
		}
		// AffineTransform shrinkTransform =
		// getAffineTransform(smaller.getPiece()
		// .getBounds2D().getWidth()
		// / greater.getPiece().getBounds2D().getWidth());
		AffineTransform shrinkTransform = getAffineTransform(smaller.getPiece()
				.getBounds2D().getWidth()
				/ greater.getPiece().getBounds2D().getWidth(), 1.0);
		RectangularArea greaterPiece = greater.getPiece();
		shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);
		// greaterPiece.setTerritoryDimensionSameAsPiece();

		double miniPos = 0.0, maxiPos = up.getPiece().getBounds2D().getHeight();
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
		// down.getPiece().getTerritory().y = miniPos;
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(up.getPiece());
		pieceMovableTypeTzu.getPiece().add(down.getPiece());
		// pieceMovableTypeTzu.getPiece().setTerritoryDimensionSameAsPiece();
		down.getPiece().moveToOrigin();// TODO Territory功能要再想好
		return;
	}

	/**
	 * 
	 * @param pieceMovableTypeTzu
	 */
	void wrapMerging(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		// TODO 暫時替代用
		PieceMovableType out = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], in = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(out.getPiece());
		pieceMovableTypeTzu.getPiece().add(in.getPiece());
		// pieceMovableTypeTzu.getPiece().setTerritoryDimensionSameAsPiece();
		return;
	}

	/**
	 * 
	 * @param scaler
	 * @return
	 */
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

	public RectangularArea format(PieceMovableType pieceMovableType)
	{
		RectangularArea target = new RectangularArea(
				pieceMovableType.getPiece());
		double widthCoefficient = 1.0, heightCoefficient = 1.0;
		// if (target.getBounds2D().getWidth() >
		// target.getTerritory().getWidth())
		widthCoefficient = target.getTerritory().getWidth()
				/ target.getBounds2D().getWidth();
		// if (target.getBounds2D().getHeight() > target.getTerritory()
		// .getHeight())
		heightCoefficient = target.getTerritory().getHeight()
				/ target.getBounds2D().getHeight();
		AffineTransform shrinkTransform = getAffineTransform(widthCoefficient,
				heightCoefficient);
		shrinkPieceByFixingStroke(target, shrinkTransform);
		target.moveTo(target.getTerritory().getX(), target.getTerritory()
				.getY());
		return target;
	}

	@Override
	protected void shrinkPieceByFixingStroke(RectangularArea rectangularArea,
			AffineTransform affineTransform)
	{
		// super.shrinkPieceByFixingStroke(rectangularArea, affineTransform);
		rectangularArea.transform(affineTransform);
	}

	// TODO 調整函式順序
}
