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
		AffineTransform affineTransform = getAffineTransform(rectangularArea);// TODO
																				// 不放太大
		shrinkPieceByFixingStroke(rectangularArea, affineTransform);
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
	protected AffineTransform getAffineTransform(RectangularArea rectangularArea)
	{
		Rectangle2D territory = rectangularArea.getTerritory();
		Rectangle2D bounds = rectangularArea.getBounds2D();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		return affineTransform;
	}

	protected double computeBoldCoefficient(RectangularArea rectangularArea)
	{
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea);
		return shapeInformation.getApproximativeRegion()
				/ shapeInformation.getApproximativeCircumference();
	}

	protected float getStorkeWidthByCoefficient(
			RectangularArea rectangularArea, double originBoldCoefficient)
	{
		float miniWidth = 0.0f, maxiWidth = (float) originBoldCoefficient;
		while (miniWidth + getPrecision() < maxiWidth)
		{
			float middleWidth = 0.5f * (miniWidth + maxiWidth);
			RectangularArea boldSurface = getBoldSurface(rectangularArea,
					middleWidth);

			boldSurface.add(rectangularArea);
			double nowBoldCoefficient = computeBoldCoefficient(boldSurface);
			// System.out.println("now=" + nowBoldCoefficient + " mini="
			// + miniWidth + " maxi=" + maxiWidth);
			if (nowBoldCoefficient < originBoldCoefficient)
				miniWidth = middleWidth;
			else
				maxiWidth = middleWidth;
		}
		return miniWidth;
	}

	protected RectangularArea getBoldSurface(RectangularArea rectangularArea,
			float strokeWidth)
	{
		Stroke stroke = new BasicStroke(strokeWidth);
		return new RectangularArea(
				stroke.createStrokedShape(rectangularArea));
	}

	protected void shrinkPieceByFixingStroke(RectangularArea rectangularArea,
			AffineTransform affineTransform)
	{ // javadoc文件註解：不放太大
		double originBoldCoefficient = computeBoldCoefficient(rectangularArea);
		rectangularArea.transform(affineTransform);
		float strokeWidth = getStorkeWidthByCoefficient(rectangularArea,
				originBoldCoefficient);
		RectangularArea boldSurface = getBoldSurface(rectangularArea,
				strokeWidth);
		rectangularArea.add(boldSurface);
		return;
	}

	public double getPrecision()
	{
		return 1e-1;
	}
}
