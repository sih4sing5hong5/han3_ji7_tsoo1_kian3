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
package cc.活字;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import cc.排版工具.尋找最低點;
import cc.筆觸工具.PathTravel;
import cc.筆觸工具.Point2DWithVector;
import cc.筆觸工具.控制點循訪;

/**
 * 活字型態。把<code>AreaTool</code>和預設位置大小整合起來，使用時比較方便。把<code>Area</code>
 * 的預計大小，改由繼承的方式重新撰寫<code>RectangularArea</code>，並將物件活字取名為<code>Piece-</code>。
 * 修改Area相關工具，使得Piece-也有相對應工具，並在逐步模組化。
 * 
 * @author Ihc
 */
public class 平面幾何 extends Area // implements 活字單元
{
	/**
	 * 活字預計位置及大小
	 */
	private Rectangle2D.Double 目標;

	/**
	 * 建立一個空的活字。
	 */
	public 平面幾何()
	{
		目標 = new Rectangle2D.Double();
	}

	/**
	 * 建立一個空的活字，並用<code>Rectangle2D</code>指定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */
	@Deprecated
	public 平面幾何(Rectangle2D 目標)
	{
		super();
		設目標範圍(目標);
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字。
	 * 
	 * @param s
	 *            字塊的形狀
	 */
	public 平面幾何(Shape s)
	{
		super(s);
		設目標範圍(字範圍());
	}

	/**
	 * 建立一個和<code>RectangularArea</code>相同的活字。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 */
	public 平面幾何(平面幾何 rectangularArea)
	{
		super(rectangularArea);
		設目標範圍(rectangularArea.目標範圍());
	}

	/**
	 * 用<code>Shape</code>建立一個相同形狀的活字， 並用<code>Rectangle2D</code>指定目標位置及大小。
	 * 
	 * @param rectangularArea
	 *            參考的活字
	 * @param 目標
	 *            預計位置及大小
	 */
	public 平面幾何(平面幾何 rectangularArea, Rectangle2D 目標)
	{
		super(rectangularArea);
		設目標範圍(目標);
	}

	/**
	 * 取得活字預計位置及大小
	 * 
	 * @return 圖形預計位置及大小
	 */

	public Rectangle2D.Double 目標範圍()
	{
		return 目標;
	}

	public Rectangle2D.Double 字範圍()
	{
		Rectangle.Double 範圍 = new Rectangle.Double();
		範圍.setRect(super.getBounds2D());
		return 範圍;
	}

	/**
	 * 設定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */

	public void 設目標範圍(Rectangle2D 目標)
	{
		this.目標 = new Rectangle2D.Double(目標.getX(), 目標.getY(), 目標.getWidth(),
				目標.getHeight());
		return;
	}

	/**
	 * 設定活字預計位置
	 * 
	 * @param x
	 *            水平位置
	 * @param y
	 *            垂直位置
	 */

	public void 設目標範圍所在(double x, double y)
	{
		目標.setRect(x, y, 目標.getWidth(), 目標.getHeight());
		return;
	}

	/**
	 * 設定活字預計大小
	 * 
	 * @param width
	 *            寬度
	 * @param height
	 *            高度
	 */

	public void 設目標範圍大細(double width, double height)
	{
		目標.setRect(目標.getX(), 目標.getY(), width, height);
		return;
	}

	public void 圖形重設()
	{
		super.reset();
		return;
	}

	/**
	 * 將物件重設，並將底下活字物件組合起來
	 * 
	 * @param 活字物件
	 *            合體活字底下的活字物件
	 */
	public void 重設並組合活字(平面幾何[] 活字物件)
	{
		圖形重設();
		for (平面幾何 活字物件單元 : 活字物件)
		{
			合併活字(活字物件單元);
		}
		return;
	}

	/**
	 * 把活字座標移動指定距離
	 * 
	 * @param x
	 *            要移動的水平距離
	 * @param y
	 *            要移動的垂直距離
	 */

	public void 徙(double x, double y)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setTransform(1, 0, 0, 1, x, y);
		this.縮放(affineTransform);
		return;
	}

	/**
	 * 把活字座標移回原點
	 */

	public void 徙轉原點()
	{
		Rectangle2D rectangle2d = this.字範圍();
		徙(-rectangle2d.getX(), -rectangle2d.getY());
		return;
	}

	public void 縮放(AffineTransform 縮放矩陣)
	{
		super.transform(縮放矩陣);
		return;
	}

	public void 合併活字(平面幾何 活字物件)
	{
		add(活字物件);
	}

	public void 減去活字(平面幾何 活字物件)
	{
		subtract(活字物件);
	}

	public void 畫佇(Graphics2D 布)
	{
		布.draw(this);
		return;
	}

	public Point2DWithVector 揣上低的點()
	{
		尋找最低點 記錄 = new 尋找最低點();
		PathTravel pathTravel = new PathTravel(new 控制點循訪(記錄));
		pathTravel.travelOn(new GeneralPath(this));
		return 記錄.取得最低點();
	}
}
