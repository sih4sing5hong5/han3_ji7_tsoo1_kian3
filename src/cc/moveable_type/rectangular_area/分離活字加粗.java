/*******************************************************************************
 * 著作權所有 (C) 民國102年 意傳文化科技
 * 開發者：薛丞宏
 * 網址：http://意傳.台灣
 * 字型提供：
 * 	全字庫授權說明
 * 		© 2012 中華民國行政院研究發展考核委員會。本字型檔採用創用CC「姓名標示－禁止改作」3.0臺灣版授權條款釋出。您可以在不變更字型內容之條件下，重製、散布及傳輸本字型檔之著作內容。惟應保留本字型名稱及著作權聲明。
 * 		http://www.cns11643.gov.tw/AIDB/copyright.do
 * 		
 * 	「中央研究院漢字部件檢字系統」2.65版釋出聲明
 * 		……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
 * 		http://cdp.sinica.edu.tw/cdphanzi/declare.htm
 * 		
 * 	吳守禮台語注音字型：
 * 		©2012從宜工作室：吳守禮、吳昭新，以CC01.0通用(CC01.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
 * 		http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
 * 		
 * 本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主：
 * 	１．得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
 * 	２．任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼；
 * 	３．將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼
 * 
 * 此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。
 * 
 * 漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。
 * 
 * 謝謝您的使用與推廣～～
 ******************************************************************************/
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
