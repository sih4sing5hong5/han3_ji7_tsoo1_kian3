package cc.moveable_type.rectangular_area;

import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableType;
import cc.程式記錄.漢字組建記錄工具包;

public class 分離活字加粗
{
	/** 物件活字加寬工具 */
	private final ChineseCharacterTypeBolder chineseCharacterTypeBolder;
	/** 合併、調整的精細度 */
	private final double precision;

	public 分離活字加粗(ChineseCharacterTypeBolder chineseCharacterTypeBolder,
			double precision)
	{
		this.chineseCharacterTypeBolder = chineseCharacterTypeBolder;
		this.precision = precision;
	}

	/**
	 * 計算物件活字粗細半徑
	 * 
	 * @param rectangularArea
	 *            物件活字
	 * @return 粗細半徑
	 */
	protected double computePieceRadius(分離活字 rectangularArea)
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
	protected double computeBoldCoefficient(分離活字 rectangularArea)
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
	protected double getStorkeWidthByCoefficient(分離活字 rectangularArea,
			double originBoldCoefficient)
	{
		// TODO 改成牛頓法可能比較好
		double miniWidth = 0.0, maxiWidth = (double) originBoldCoefficient;
		while (miniWidth + getPrecision() < maxiWidth)
		{
			double middleWidth = 0.5 * (miniWidth + maxiWidth);
			分離活字 boldSurface = getBoldSurface(rectangularArea, middleWidth);
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
	protected 分離活字 getBoldSurface(分離活字 rectangularArea, double strokeWidth)
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
	void shrinkPieceByFixingStroke(分離活字 rectangularArea,
			AffineTransform affineTransform)
	{
		// TODO
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
	活字寬度資訊 取得活字寬度資訊(分離活字 活字物件)
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
	void 依寬度資訊調整活字(分離活字 活字物件, 活字寬度資訊 寬度資訊)
	{
		// TODO
		// double strokeWidth = getStorkeWidthByCoefficient(活字物件,
		// 寬度資訊.取得活字粗細係數());
		// 活字單元 boldSurface = getBoldSurface(活字物件, strokeWidth);
		// 活字物件.合併活字(boldSurface);
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
