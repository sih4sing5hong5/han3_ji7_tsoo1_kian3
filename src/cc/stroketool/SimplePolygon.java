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
package cc.stroketool;

import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * 用來計算多邊形的資訊。其中頂點的順序按照<code>Area</code>為主，順時鐘代表圖形實心，逆時鐘代表圖形挖空。
 * 
 * @author Ihc
 */
public class SimplePolygon
{
	/**
	 * 依序記錄每個頂點位置
	 */
	private Vector<Point2D> apex = new Vector<Point2D>();
	/**
	 * 目前頂點間的長度（不包含頭尾）
	 */
	private double circumference = 0.0;
	/**
	 * 目前多邊形的面積（不包含行列式的最後一行）
	 */
	private double regionSize = 0.0;

	/**
	 * 把裡面頂點資料都清掉
	 */
	public void clear()
	{
		apex.clear();
		circumference = 0.0;
		regionSize = 0.0;
		return;
	}

	/**
	 * 增加一個頂點
	 * 
	 * @param point
	 *            要新增的頂點
	 */
	public void addPoint(Point2D point)
	{
		apex.add(point);
		if (apex.size() > 1)
		{
			Point2D previous = apex.elementAt(apex.size() - 2), last = apex
					.lastElement();
			circumference += previous.distance(last);
			regionSize += previous.getX() * last.getY() - previous.getY()
					* last.getX();
		}
		return;
	}

	/**
	 * 增加一個頂點
	 * 
	 * @param x
	 *            新增頂點的x值
	 * @param y
	 *            新增頂點的y值
	 */
	public void addPoint(double x, double y)
	{
		addPoint(new Point2D.Double(x, y));
		return;
	}

	/**
	 * 把第一個加到最後，讓行列式和距離算完。
	 */
	public void finish()
	{
		if (apex.size() != 0 && !apex.firstElement().equals(apex.lastElement()))
		{
			addPoint(apex.firstElement());
		}
		return;
	}

	/**
	 * 算出目前多邊形的周長
	 * 
	 * @return 多邊形的周長
	 */
	public double getCircumference()
	{
		if (apex.size() == 0)
			return 0.0;
		return circumference + apex.firstElement().distance(apex.lastElement());
	}

	/**
	 * 算出目前多邊形的面積
	 * 
	 * @return 多邊形的面積
	 */
	public double getRegionSize()
	{
		if (apex.size() == 0)
			return 0.0;
		Point2D first = apex.firstElement(), last = apex.lastElement();
		return -0.5
				* (regionSize + last.getX() * first.getY() - last.getY()
						* first.getX());
	}
}
