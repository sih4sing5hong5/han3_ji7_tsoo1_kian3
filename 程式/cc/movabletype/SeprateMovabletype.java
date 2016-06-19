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
package cc.movabletype;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.security.InvalidParameterException;
import java.util.Vector;

import cc.stroketool.Point2DWithVector;

public class SeprateMovabletype
{
	/** 目前字體大細，所在 */
	private Vector<PlaneGeometry> 字;
	/** 為著保留字體闊，若維護矩陣顛倒愛去顧平移 */
	private Vector<PlaneGeometry> 原本字體;
	/** 加粗莫合併的外殼 */
	private Vector<PlaneGeometry> 字外殼;
	/** 這馬的範圍，是為著閃避「一字問題」 */
	private Rectangle2D.Double 這馬;
	/** 目標範圍，上尾印的時陣愛看印偌大 */
	private Rectangle2D.Double 目標;

	public SeprateMovabletype()
	{
		字 = new Vector<PlaneGeometry>();
		原本字體 = new Vector<PlaneGeometry>();
		字外殼 = null;
		這馬 = new Rectangle.Double();
		目標 = new Rectangle.Double();
	}

	public SeprateMovabletype(PlaneGeometry 幾何)
	{
		this();
		字.add(幾何);
		原本字體.add(幾何);
		這馬字範圍();
	}

	public SeprateMovabletype(SeprateMovabletype 活字)
	{
		字 = new Vector<PlaneGeometry>();
		for (PlaneGeometry 活字的字 : 活字.字)
			字.add(new PlaneGeometry(活字的字));
		原本字體 = new Vector<PlaneGeometry>(活字.原本字體);
		字外殼 = null;
		這馬 = new Rectangle.Double();
		這馬.setRect(活字.這馬);
		目標 = new Rectangle.Double();
		目標.setRect(活字.目標);
	}

	private Rectangle2D.Double 圖形範圍()
	{
		if (字.isEmpty())
			return null;
		Rectangle2D.Double 圖形 = new Rectangle2D.Double();
		圖形.setRect(字.firstElement().getBounds2D());
		for (PlaneGeometry 平面 : 字)
			圖形.setRect(圖形.createUnion(平面.getBounds2D()));
		return 圖形;
	}

	// @Override
	// public Rectangle2D.Double 字範圍()
	// {
	// 這馬.setRect(這馬.createUnion(圖形範圍()));
	// return 這馬;
	// }

	public Rectangle2D.Double 這馬字範圍()
	{
		for (PlaneGeometry 平面 : 字)
			這馬.setRect(這馬.createUnion(平面.getBounds2D()));
		return 這馬;
	}

	public void 這馬字範圍照圖形範圍()
	{
		這馬.setRect(圖形範圍());
		return;
	}

	public void 切掉字範圍()
	{
		// System.out.println("切掉字範圍");
		// System.out.println(字範圍());
		// System.out.println(目標範圍());
		// System.out.println(圖形範圍());
		Rectangle2D.Double 新位置 = 這馬字範圍();
		Rectangle2D.Double 圖形位置 = 圖形範圍();
		if (圖形位置 != null)
		{
			if (新位置.getHeight() > 目標.getHeight())
			{
				這馬.setRect(這馬.getX(),
						// 這馬.getY(),
						圖形位置.getCenterY() - 目標.getHeight() / 2.0,
						這馬.getWidth(), 目標.getHeight());
			}
			if (新位置.getWidth() > 目標.getWidth())
			{
				這馬.setRect(圖形位置.getCenterX() - 目標.getWidth() / 2.0, 這馬.getY(),
						目標.getWidth(), 這馬.getHeight());
			}
		}
		這馬字範圍();
		// System.out.println(字範圍());
	}

	public void 設字範圍(Rectangle2D 目標)
	{
		// TODO Auto-generated method stub
		這馬 = new Rectangle.Double();
		這馬.setRect(目標);
		這馬字範圍();
		return;
	}

	public void 重設字範圍()
	{
		// TODO Auto-generated method stub
		這馬 = new Rectangle.Double();
		這馬字範圍();
		return;
	}

	/**
	 * 取得活字預計位置及大小
	 * 
	 * @return 活字預計位置及大小
	 */

	public Rectangle2D.Double 目標範圍()
	{
		return 目標;
	}

	/**
	 * 設定活字預計位置及大小
	 * 
	 * @param 目標
	 *            預計位置及大小
	 */

	public void 設目標範圍(Rectangle2D 目標)
	{
		this.目標.setRect(目標);
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
		this.目標.setRect(x, y, this.目標.getWidth(), this.目標.getHeight());
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
		this.目標.setRect(this.目標.getX(), this.目標.getY(), width, height);
	}

	/** 共活字的圖形清掉 */

	public void 圖形重設()
	{
		字.clear();
		原本字體.clear();
		這馬.setRect(0, 0, 0, 0);
		// for (PlaneGeometry 平面 : 字)
		// {
		// 平面.圖形重設();
		// }
		return;
	}

	/**
	 * 將物件重設，並將底下活字物件組合起來
	 * 
	 * @param 活字物件
	 *            合體活字底下的活字物件
	 */

	public void 重設並組合活字(SeprateMovabletype[] 活字物件)
	{
		圖形重設();
		for (SeprateMovabletype 活字 : 活字物件)
		{
			if (活字 instanceof SeprateMovabletype)
				合併活字((SeprateMovabletype) 活字);
			else
				throw new InvalidParameterException();
		}
		切掉字範圍();
		徙轉原點();
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
		這馬.setRect(這馬.getX() + x, 這馬.getY() + y, 這馬.getWidth(), 這馬.getHeight());
		for (PlaneGeometry 幾何 : 字)
		{
			幾何.徙(x, y);
		}
		return;
	}

	/**
	 * 把活字的座標移回原點
	 */

	public void 徙轉原點()
	{
		Rectangle2D.Double 位移範圍 = 這馬字範圍();
		徙(-位移範圍.getX(), -位移範圍.getY());
		return;
	}

	public void 縮放(AffineTransform 縮放矩陣)
	{
		for (PlaneGeometry 幾何 : 字)
		{
			幾何.縮放(縮放矩陣);
		}// TODO 字範圍
			// Point2D.Double 角 = new Point2D.Double(這馬.getX(), 這馬.getY());
		// 縮放矩陣.transform(角, 角);
		// Point2D.Double 闊 = new Point2D.Double(這馬.getWidth(), 這馬.getHeight());
		// 縮放矩陣.transform(闊, 闊);
		// 這馬.setRect(角.getX(), 角.getY(), 闊.getX(), 闊.getY());
		這馬.setRect(縮放矩陣.createTransformedShape(這馬).getBounds2D());
		return;
	}

	public void 合併活字(SeprateMovabletype 活字物件)
	{
		字.addAll(活字物件.字);
		原本字體.addAll(活字物件.原本字體);
		這馬.setRect(這馬.createUnion(活字物件.這馬字範圍()));
		return;
	}

	public void 減去活字(SeprateMovabletype 活字物件)
	{
		for (PlaneGeometry 幾何 : 字)
			for (PlaneGeometry 別个幾何 : 活字物件.字)
			{
				幾何.減去活字(別个幾何);
			}
		return;
	}

	public void 畫佇(Graphics2D 布)
	{
		for (PlaneGeometry 幾何 : 字)
			布.draw(幾何);
		if (字外殼 != null)
			for (PlaneGeometry 幾何 : 字外殼)
				布.draw(幾何);
		return;
	}

	public Point2DWithVector 揣上低的點()
	{
		Point2DWithVector 上低的點 = null;
		for (PlaneGeometry 幾何 : 字)
		{
			Point2DWithVector 這个低點 = 幾何.揣上低的點();
			if (上低的點 == null || 上低的點.getY() > 這个低點.getY())
				上低的點 = 這个低點;
		}
		return 上低的點;
	}

	public PlaneGeometry 目前的字體()
	{
		PlaneGeometry 字體結果 = new PlaneGeometry();
		for (PlaneGeometry 幾何 : 字)
			字體結果.合併活字(幾何);
		return 字體結果;
	}

	public Vector<PlaneGeometry> 提著字()
	{
		return 字;
	}

	public Vector<PlaneGeometry> 提著原本字體()
	{
		return 原本字體;
	}

	public void 設字外殼(Vector<PlaneGeometry> 字外殼)
	{
		this.字外殼 = 字外殼;
		return;
	}
}
