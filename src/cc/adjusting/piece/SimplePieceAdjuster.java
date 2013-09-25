package cc.adjusting.piece;

import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.slf4j.Logger;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.ShapeInformation;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.活字單元;
import cc.程式記錄.漢字組建記錄工具包;

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
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 物件活字加寬工具 */
	private final ChineseCharacterTypeBolder chineseCharacterTypeBolder;
	/** 合併、調整的精細度 */
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
		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());

		this.chineseCharacterTypeBolder = chineseCharacterTypeBolder;
		this.precision = precision;
	}

	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen) chineseCharacterMovableTypeWen;
		活字單元 rectangularArea = pieceMovableTypeWen.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		shrinkPieceByFixingStroke(rectangularArea, affineTransform);
		return;
	}

	@Override
	public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) tzu;
		活字單元 rectangularArea = pieceMovableTypeTzu.getPiece();
		AffineTransform affineTransform = getAffineTransform(rectangularArea);
		rectangularArea.縮放(affineTransform);
		for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
		{
			PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
					.getChildren()[i];
			Rectangle2D childTerritory = child.getPiece().目標範圍();
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
	protected AffineTransform getAffineTransform(活字單元 rectangularArea)
	{
		Rectangle2D territory = rectangularArea.目標範圍();
		Rectangle2D bounds = rectangularArea.字範圍();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		return affineTransform;
	}

	/**
	 * 計算物件活字粗細半徑
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @return 粗細半徑
	 */
	protected double computePieceRadius(活字單元 rectangularArea)
	{
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea.目前的字體());
		return shapeInformation.getApproximativeRegion()
				/ shapeInformation.getApproximativeCircumference();
	}

	/**
	 * 計算物件活字粗細係數
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @return 粗細係數
	 */
	protected double computeBoldCoefficient(活字單元 rectangularArea)
	{
		return computePieceRadius(rectangularArea);
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
	protected double getStorkeWidthByCoefficient(活字單元 rectangularArea,
			double originBoldCoefficient)
	{
		// TODO 改成牛頓法可能比較好
		double miniWidth = 0.0, maxiWidth = (double) originBoldCoefficient;
		while (miniWidth + getPrecision() < maxiWidth)
		{
			double middleWidth = 0.5 * (miniWidth + maxiWidth);
			活字單元 boldSurface = getBoldSurface(rectangularArea, middleWidth);
			boldSurface.合併活字(rectangularArea);
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
	protected 活字單元 getBoldSurface(活字單元 rectangularArea, double strokeWidth)
	{
		Stroke stroke = chineseCharacterTypeBolder.getStroke(strokeWidth);
		return new 分離活字(new 平面幾何(stroke.createStrokedShape(rectangularArea
				.目前的字體())));
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
	void shrinkPieceByFixingStroke(活字單元 rectangularArea,
			AffineTransform affineTransform)
	{
		//TODO
		活字寬度資訊 舊活字寬度資訊 = 取得活字寬度資訊(rectangularArea);
		// double originBoldCoefficient =
		// computeBoldCoefficient(rectangularArea);
		rectangularArea.縮放(affineTransform);
		依寬度資訊調整活字(rectangularArea, 舊活字寬度資訊);
		// double strokeWidth = getStorkeWidthByCoefficient(rectangularArea,
		// originBoldCoefficient);
		// RectangularArea boldSurface = getBoldSurface(rectangularArea,
		// strokeWidth);
		// rectangularArea.add(boldSurface);
		return;
	}

	/**
	 * 取得活字物件的活字寬度資訊
	 * 
	 * @param 活字物件
	 *            欲取得資訊的活字物件
	 * @return 活字寬度資訊
	 */
	活字寬度資訊 取得活字寬度資訊(活字單元 活字物件)
	{
		return new 活字寬度資訊(computeBoldCoefficient(活字物件));
	}

	/**
	 * 依照寬度資訊，來調整活字物件
	 * 
	 * @param 活字物件
	 *            要被調整的活字物偉
	 * @param 寬度資訊
	 *            依據的寬度資訊
	 */
	void 依寬度資訊調整活字(活字單元 活字物件, 活字寬度資訊 寬度資訊)
	{
		//TODO
//		double strokeWidth = getStorkeWidthByCoefficient(活字物件, 寬度資訊.取得活字粗細係數());
//		活字單元 boldSurface = getBoldSurface(活字物件, strokeWidth);
//		活字物件.合併活字(boldSurface);
	}

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
