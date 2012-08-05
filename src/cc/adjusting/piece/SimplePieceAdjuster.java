package cc.adjusting.piece;

import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.moveable_type.rectangular_area.ShapeInformation;

/**
 * 區塊活字調整工具。調整<code>PieceMovableType</code>，因為把活字的資訊全部集中在同一個物件上（
 * <code>Piece</code>， <code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>SimplePiece</code>是在設定時兩兩配對後定框，調整時更改部件大小，但無法物件難實作距離貼近或拉開。
 * 
 * @author Ihc
 */
public class SimplePieceAdjuster implements ChineseCharacterTypeAdjuster
{
	/**
	 * 物件活字加寬工具
	 */
	private final ChineseCharacterTypeBolder chineseCharacterTypeBolder;
	/**
	 * 合併、調整的精細度
	 */
	private final double precision;

	/**
	 * 建立物件活字調整工具
	 * 
	 * @param chineseCharacterTypeBolder
	 *            物件活字加寬工具
	 * @param precision
	 *            合併、調整的精細度
	 */
	public SimplePieceAdjuster(
			ChineseCharacterTypeBolder chineseCharacterTypeBolder,
			double precision)
	{
		this.chineseCharacterTypeBolder = chineseCharacterTypeBolder;
		this.precision = precision;
	}

	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		RectangularArea rectangularArea = pieceMovableTypeWen.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		shrinkPieceByFixingStroke(rectangularArea, affineTransform);
		return;
	}

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
	 * 比較<code>RectangularArea</code>目前大小（<code>getBounds2D()</code>）和預期大小（
	 * <code>getTerritory()</code>），算出縮放矩陣
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

	/**
	 * 計算物件活字粗細係數
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @return 粗細係數
	 */
	protected double computeBoldCoefficient(RectangularArea rectangularArea)
	{
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea);
		return shapeInformation.getApproximativeRegion()
				/ shapeInformation.getApproximativeCircumference();
	}

	/**
	 * 利用二元搜尋法，找出符合粗細係數的物件活字筆劃加寬度
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @param originBoldCoefficient
	 *            粗細係數
	 * @return 筆劃加寬度
	 */
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
			if (nowBoldCoefficient < originBoldCoefficient)
				miniWidth = middleWidth;
			else
				maxiWidth = middleWidth;
		}
		return miniWidth;
	}

	/**
	 * 給定筆劃加寬度，取得物件活字外框
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @param strokeWidth
	 *            筆劃加寬度
	 * @return 物件活字外框
	 */
	protected RectangularArea getBoldSurface(RectangularArea rectangularArea,
			float strokeWidth)
	{
		Stroke stroke = chineseCharacterTypeBolder.getStroke(strokeWidth);
		return new RectangularArea(stroke.createStrokedShape(rectangularArea));
	}

	/**
	 * 固定粗細係數的情況下，縮小物件活字
	 * <p>
	 * 限制：<code>AffineTransform</code>二個縮放比例的絕對值必須小於等於1
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @param affineTransform
	 *            粗細係數
	 */
	protected void shrinkPieceByFixingStroke(RectangularArea rectangularArea,
			AffineTransform affineTransform)
	{
		double originBoldCoefficient = computeBoldCoefficient(rectangularArea);
		rectangularArea.transform(affineTransform);
		float strokeWidth = getStorkeWidthByCoefficient(rectangularArea,
				originBoldCoefficient);
		RectangularArea boldSurface = getBoldSurface(rectangularArea,
				strokeWidth);
		rectangularArea.add(boldSurface);
		return;
	}

	//
	// /**
	// * 取得筆劃加寬物件
	// *
	// * @param width
	// * 加寬的寬度
	// * @return 筆劃加寬物件
	// */
	// public Stroke getStroke(float width)
	// {
	// return new BasicStroke(width);
	// }

	/**
	 * 取得合併、調整的精細度
	 * 
	 * @return 合併、調整的精細度
	 */
	public double getPrecision()
	{
		return precision;
	}
}
