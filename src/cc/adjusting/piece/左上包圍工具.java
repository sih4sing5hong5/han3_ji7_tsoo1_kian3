package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 用於左上的包圍部件。從左上方包住，像是「厂」、「广」、「尸」和「『左』的左上方」等等。
 * 
 * @author Ihc
 */
public class 左上包圍工具 extends 物件活字包圍工具
{
	/**
	 * 建立左下包圍工具
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public 左上包圍工具(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
		支援包圍部件.add("厂");
		支援包圍部件.add("广");
		支援包圍部件.add("疒");
		支援包圍部件.add("尸");
		支援包圍部件.add("戶");
		支援包圍部件.add("户");
		支援包圍部件.add("虍");
		// TODO　/*歷廈病居房灰老虐遞…*/
	}

	@Override
	public void 組合(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType out = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], in = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];

//		in.getPiece().moveToOrigin();
		RectangularArea insidePiece = 調整工具.getPieceWithSquareTerritory(in.getPiece());
		double miniPos = 0.0, maxiPos = insidePiece.getBounds2D().getHeight();
		while (miniPos + 調整工具.getPrecision() < maxiPos)
		{
			double middlePos = 0.5 * (miniPos + maxiPos);

			RectangularArea rectangularArea = new RectangularArea(insidePiece);
			AffineTransform affineTransform = 調整工具.getAffineTransform(middlePos
					/ insidePiece.getBounds2D().getHeight());
			rectangularArea.transform(affineTransform);
			rectangularArea.moveBy(out.getPiece().getBounds2D().getWidth()
					- rectangularArea.getBounds2D().getWidth(), out.getPiece()
					.getBounds2D().getHeight()
					- rectangularArea.getBounds2D().getHeight());
			
			if (調整工具.areIntersected(out.getPiece(), rectangularArea))
				maxiPos = middlePos;
			else
				miniPos = middlePos;
		}
		AffineTransform affineTransform = 調整工具.getAffineTransform(miniPos
				/ insidePiece.getBounds2D().getHeight());
		insidePiece.transform(affineTransform);
		insidePiece.moveBy(out.getPiece().getBounds2D().getWidth()
				- insidePiece.getBounds2D().getWidth(), out.getPiece()
				.getBounds2D().getHeight()
				- insidePiece.getBounds2D().getHeight());

		// double downRadius = 調整工具.computePieceRadius(in.getPiece());
		// in.getPiece().moveToOrigin();// TODO 人工參數
		// in.getPiece().moveBy(
		// in.getPiece().getBounds2D().getWidth()
		// * (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
		// miniPos - downRadius * 2.6);
		// double nonsuitableToClose = 調整工具.nonsuitableToClose(out.getPiece(),
		// in.getPiece(), in.getPiece().getBounds2D().getWidth());
		//
		// in.getPiece().moveToOrigin();
		// in.getPiece().moveBy(
		// in.getPiece().getBounds2D().getWidth()
		// * (1.0 - insideShrinkRate) / insideShrinkRate * 0.5,
		// miniPos);
		//
		// if (nonsuitableToClose > 1.6)// TODO 人工參數
		// in.getPiece().moveBy(0, +downRadius * 3.0);
		// else if (nonsuitableToClose > 0.8)
		// in.getPiece().moveBy(0, 0);
		// else
		// in.getPiece().moveBy(0, -downRadius * 1.2);

		pieceMovableTypeTzu.getPiece().reset();
		pieceMovableTypeTzu.getPiece().add(out.getPiece());
		pieceMovableTypeTzu.getPiece().add(insidePiece);
		// in.getPiece().moveToOrigin();
		return;
	}
}
