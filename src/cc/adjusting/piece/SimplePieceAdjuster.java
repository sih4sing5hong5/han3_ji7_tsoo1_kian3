/**
 * 
 */
package cc.adjusting.piece;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * @author Ihc
 * 
 */
public class SimplePieceAdjuster implements ChineseCharacterTypeAdjuster
{
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
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		RectangularArea rectangularArea = pieceMovableTypeWen.getPiece();
		double originBoldCoefficient = computeBoldCoefficient(rectangularArea);
		AffineTransform affineTransform = getAffineTransform(rectangularArea);// TODO
																				// 不放太大
		rectangularArea.transform(affineTransform);
		System.out.println(affineTransform.getScaleX() + " "
				+ affineTransform.getScaleY());
		System.out.println("ori=" + originBoldCoefficient + " new="
				+ computeBoldCoefficient(rectangularArea));
		float strokeWidth = getStorkeWidthByCoefficient(rectangularArea,
				originBoldCoefficient);
		System.out.println(strokeWidth);
		Stroke basicStroke = new BasicStroke(strokeWidth);
		RectangularArea blodSurface = new RectangularArea(
				basicStroke.createStrokedShape(rectangularArea));
//		rectangularArea.subtract(rectangularArea);
		rectangularArea.add(blodSurface);
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see cc.adjusting.ChineseCharacterTypeAdjuster#adjustTzu(cc.moveable_type.
	 *      ChineseCharacterMovableTypeTzu)
	 */
	@Override
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) tzu;
		RectangularArea rectangularArea = pieceMovableTypeTzu.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		rectangularArea.transform(affineTransform);
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
					.getChildren()[i];
			Rectangle2D childTerritory = child.getPiece().getTerritory();
			childTerritory.setRect(
					childTerritory.getX() * affineTransform.getScaleX(),
					childTerritory.getY() * affineTransform.getScaleY(),
					childTerritory.getWidth() * affineTransform.getScaleX(),
					childTerritory.getHeight() * affineTransform.getScaleY());
			pieceMovableTypeTzu.getChildren()[i].adjust(this);
		}
	}

	/**
	 * 比較rectangularArea目前大小和預期大小，算出縮放矩陣
	 * 
	 * @param rectangularArea
	 *            計算縮放的目標
	 * @return 相對應的縮放矩陣
	 */
	private AffineTransform getAffineTransform(RectangularArea rectangularArea)
	{
		Rectangle2D territory = rectangularArea.getTerritory();
		Rectangle2D bounds = rectangularArea.getBounds2D();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		return affineTransform;
	}

	private double computeBoldCoefficient(RectangularArea rectangularArea)
	{
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea);
		return shapeInformation.getApproximativeRegion()
				/ shapeInformation.getApproximativeCircumference();
	}

	private float getStorkeWidthByCoefficient(RectangularArea rectangularArea,
			double originBoldCoefficient)
	{
		float miniWidth = 0.0f, maxiWidth = (float) originBoldCoefficient;
		while (miniWidth + 1e-1 < maxiWidth)
		{
			float middleWidth = 0.5f * (miniWidth + maxiWidth);
			Stroke basicStroke = new BasicStroke(middleWidth);
			RectangularArea blodSurface = new RectangularArea(
					basicStroke.createStrokedShape(rectangularArea));
			blodSurface.add(rectangularArea);
			double nowBoldCoefficient = computeBoldCoefficient(blodSurface);
//			System.out.println("now=" + nowBoldCoefficient + " mini="
//					+ miniWidth + " maxi=" + maxiWidth);
			if (nowBoldCoefficient < originBoldCoefficient)
				miniWidth = middleWidth;
			else
				maxiWidth = middleWidth;
		}
		return miniWidth;
	}
}
