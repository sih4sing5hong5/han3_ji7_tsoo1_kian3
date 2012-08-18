package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 用於上蓋的包圍部件。蓋住上方，左右二邊只包含上方一小部份，像是「冖」、「宀」和「『學』上半部」等等。
 * 
 * @author Ihc
 */
public class 上蓋包圍工具 extends 物件活字包圍工具
{
	/**
	 * 建立上蓋包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 上蓋包圍工具(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("冖");
		支援包圍部件.add("宀");
		// 支援包圍部件.add("學");//TODO
	}

	@Override
	public void 組合(PieceMovableTypeTzu pieceMovableTypeTzu)
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
		AffineTransform shrinkTransform = 調整工具.getAffineTransform(smaller
				.getPiece().getBounds2D().getWidth()
				/ greater.getPiece().getBounds2D().getWidth(), 1.0);
		RectangularArea greaterPiece = greater.getPiece();
		調整工具.shrinkPieceByFixingStroke(greaterPiece, shrinkTransform);

		double insideShrinkRate = 1.0; // TODO 要依上部寬度決定
		AffineTransform insideShrinkTransform = 調整工具.getAffineTransform(
				insideShrinkRate, 1.0);
		調整工具.shrinkPieceByFixingStroke(down.getPiece(), insideShrinkTransform);

		double miniPos = 0.0, maxiPos = up.getPiece().getBounds2D().getHeight();
		while (miniPos + 調整工具.getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);
			down.getPiece().moveToOrigin();
			down.getPiece()
					.moveTo(down.getPiece().getBounds2D().getWidth()
							* (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
							middlePos);
			if (調整工具.areIntersected(up.getPiece(), down.getPiece()))
				miniPos = middlePos;
			else
				maxiPos = middlePos;
		}

		double downRadius = 調整工具.computePieceRadius(down.getPiece());
		down.getPiece().moveToOrigin();// TODO 人工參數
		down.getPiece().moveTo(
				down.getPiece().getBounds2D().getWidth()
						* (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
				miniPos - downRadius * 2.6);
		double nonsuitableToClose = 調整工具.nonsuitableToClose(up.getPiece(),
				down.getPiece(), down.getPiece().getBounds2D().getWidth());

		down.getPiece().moveToOrigin();
		down.getPiece().moveTo(
				down.getPiece().getBounds2D().getWidth()
						* (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
				miniPos);

		if (nonsuitableToClose > 1.6)// TODO 人工參數
			down.getPiece().moveTo(0, +downRadius * 3.0);
		else if (nonsuitableToClose > 0.8)
			down.getPiece().moveTo(0, 0);
		else
			down.getPiece().moveTo(0, -downRadius * 1.2);

		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(up.getPiece());
		pieceMovableTypeTzu.getPiece().add(down.getPiece());
		down.getPiece().moveToOrigin();
		return;
	}
}
