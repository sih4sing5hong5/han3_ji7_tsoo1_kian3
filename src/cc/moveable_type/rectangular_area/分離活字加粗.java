package cc.moveable_type.rectangular_area;

import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import cc.adjusting.bolder.ChineseCharacterTypeBolder;

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
	protected double computePieceRadius(平面幾何 rectangularArea)
	{
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea);
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
	public double computeBoldCoefficient(平面幾何 rectangularArea)
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
	protected 平面幾何 依寬度資訊產生外殼(平面幾何 活字物件, 活字寬度資訊 寬度資訊)
	{
		double 原來闊度系數 = 寬度資訊.取得活字粗細係數();
		活字寬度資訊 這馬闊度資訊 = 取得活字寬度資訊(活字物件);
		double 這馬闊度係數 = 這馬闊度資訊.取得活字粗細係數();
		// TODO 改成牛頓法可能比較好
		平面幾何 新殼 = new 平面幾何();
		double miniWidth = 0.0, maxiWidth = 原來闊度系數 / 2.0;// TODO 奇怪參數
		while (miniWidth + getPrecision() < maxiWidth)
		{
			double middleWidth = 0.5 * (miniWidth + maxiWidth);
			新殼 = getBoldSurface(活字物件, middleWidth);

			double nowBoldCoefficient = computeBoldCoefficient(新殼);
			if (nowBoldCoefficient / 2.0 + 這馬闊度係數 < 原來闊度系數)
				miniWidth = middleWidth;
			else
				maxiWidth = middleWidth;
		}
		return 新殼;// getBoldSurface(活字物件, miniWidth);
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
	protected 平面幾何 getBoldSurface(平面幾何 rectangularArea, double strokeWidth)
	{
		if (strokeWidth < getPrecision())
			return new 平面幾何();
		Stroke stroke = chineseCharacterTypeBolder.getStroke(strokeWidth);
		return new 平面幾何(stroke.createStrokedShape(rectangularArea));
	}

	//
	// /**
	// * 固定粗細係數的情況下，縮小物件活字
	// * <p>
	// * 限制：<code>AffineTransform</code>二個縮放比例的絕對值必須小於等於1
	// *
	// * @param rectangularArea
	// * 物件活字
	// * @param affineTransform
	// * 粗細係數
	// */
	// void shrinkPieceByFixingStroke(分離活字 rectangularArea,
	// AffineTransform affineTransform)
	// {
	// // TODO
	// // 活字寬度資訊 舊活字寬度資訊 = 取得活字寬度資訊(rectangularArea);
	// // // double originBoldCoefficient =
	// // // computeBoldCoefficient(rectangularArea);
	// // rectangularArea.縮放(affineTransform);
	// // 依寬度資訊調整活字(rectangularArea, 舊活字寬度資訊);
	// // // double strokeWidth = getStorkeWidthByCoefficient(rectangularArea,
	// // // originBoldCoefficient);
	// // // RectangularArea boldSurface = getBoldSurface(rectangularArea,
	// // // strokeWidth);
	// // // rectangularArea.add(boldSurface);
	// return;
	// }

	/**
	 * 取得活字物件的活字寬度資訊
	 * 
	 * @param 活字物件
	 *            欲取得資訊的活字物件
	 * @return 活字寬度資訊
	 */
	活字寬度資訊 取得活字寬度資訊(平面幾何 活字物件)
	{
		return new 活字寬度資訊(computeBoldCoefficient(活字物件));
	}

	// /**
	// * 依照寬度資訊，來調整活字物件
	// *
	// * @param 活字物件
	// * 要被調整的活字物偉
	// * @param 寬度資訊
	// * 依據的寬度資訊
	// */
	// 平面幾何 依寬度資訊產生外殼(平面幾何 活字物件, 活字寬度資訊 寬度資訊)
	// {
	// // TODO
	// double strokeWidth = getStorkeWidthByCoefficient(活字物件, 寬度資訊.取得活字粗細係數());
	// 平面幾何 boldSurface = getBoldSurface(活字物件, strokeWidth);
	// return boldSurface;
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

	public void 加粗(分離活字 活字)
	{
		Vector<平面幾何> 原本字體 = 活字.提著原本字體();
		Vector<平面幾何> 字 = 活字.提著字();
		if (原本字體.size() != 字.size())
		{
			System.err.println(原本字體.size());
			System.err.println(字.size());
			System.err.println("字佮原本字體數無仝！！");
			return;
		}
		Vector<平面幾何> 字外殼 = new Vector<平面幾何>();
		for (int i = 0; i < 原本字體.size(); ++i)
		{
			活字寬度資訊 寬度資訊 = 取得活字寬度資訊(原本字體.elementAt(i));
			字外殼.add(依寬度資訊產生外殼(字.elementAt(i), 寬度資訊));
		}
		活字.設字外殼(字外殼);
		return;
	}
}
