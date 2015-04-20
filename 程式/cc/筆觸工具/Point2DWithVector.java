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
package cc.筆觸工具;

import java.awt.geom.Point2D;

/**
 * 二維向量。找不到內建的函式就自己做了～～
 * 
 * @author Ihc
 */
public class Point2DWithVector extends Point2D
{
	/** 甲座標 */
	private double x;
	/** 乙座標 */
	private double y;
	/** 原點 */
	static private final Point2DWithVector origin = new Point2DWithVector();

	/**
	 * 建立二維向量。
	 */
	public Point2DWithVector()
	{
		x = y = 0.0;
	}

	/**
	 * 建立二維向量。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public Point2DWithVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 建立二維向量。
	 * 
	 * @param point2d
	 *            參考座標
	 */
	public Point2DWithVector(Point2D point2d)
	{
		this(point2d.getX(), point2d.getY());
	}

	@Override
	public double getX()
	{
		return x;
	}

	@Override
	public double getY()
	{
		return y;
	}

	@Override
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
		return;
	}

	/**
	 * 設定座標。
	 * 
	 * @param point2d
	 *            座標
	 */
	public void setLocation(Point2D point2d)
	{
		setLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 和另一向量相加。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public void addLocation(double x, double y)
	{
		this.x += x;
		this.y += y;
		return;
	}

	/**
	 * 和另一向量相加。
	 * 
	 * @param point2d
	 *            欲相加的向量
	 */
	public void addLocation(Point2D point2d)
	{
		addLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 和另一向量相減。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public void subLocation(double x, double y)
	{
		addLocation(-x, -y);
		return;
	}

	/**
	 * 和另一向量相減。
	 * 
	 * @param point2d
	 *            欲相減的向量
	 */
	public void subLocation(Point2D point2d)
	{
		subLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 變成單位向量。
	 */
	public void setUnit()
	{
		double distance = Point2D.distance(0.0, 0.0, x, y);
		if (distance > 0.0)
		{
			this.x /= distance;
			this.y /= distance;
		}
		return;
	}

	/**
	 * 逆時針轉九十度。
	 */
	public void rotateRightAngle()

	{
		double a = this.x, b = this.y;
		this.x = -b;
		this.y = a;
		return;
	}

	/**
	 * 座標放大。
	 * 
	 * @param scaler
	 *            放大倍數
	 */
	public void multiple(double scaler)
	{
		this.x *= scaler;
		this.y *= scaler;
		return;
	}

	/**
	 * 和另一向量內積。
	 * 
	 * @param point2dWithVector
	 *            欲內積的向量
	 * @return 內積值
	 */
	public double innerProduct(Point2DWithVector point2dWithVector)
	{
		return x * point2dWithVector.x + y * point2dWithVector.y;
	}

	/**
	 * 和另一向量相乘。
	 * 
	 * @param point2dWithVector
	 *            欲相乘的向量
	 */
	public void multipleByPolarSystem(Point2DWithVector point2dWithVector)
	{
		double a = this.x, b = this.y;
		this.x = a * point2dWithVector.x - b * point2dWithVector.y;
		this.y = a * point2dWithVector.y + b * point2dWithVector.x;
		return;
	}

	/**
	 * 除以另一向量。
	 * 
	 * @param point2dWithVector
	 *            欲除以的向量
	 */
	public void divideByPolarSystem(Point2DWithVector point2dWithVector)
	{
		if (!point2dWithVector.equals(origin))
		{
			double a = this.x, b = this.y;
			this.x = a * point2dWithVector.x + b * point2dWithVector.y;
			this.y = -a * point2dWithVector.y + b * point2dWithVector.x;
			double distanceSq = origin.distanceSq(point2dWithVector);
			multiple(1.0 / distanceSq);
		}
		return;
	}

	@Override
	public String toString()
	{
		return "[Point2DWithVector,x=" + x + ",y=" + y + "]";
	}

	/**
	 * 給兩向量，判斷是否相同。
	 * 
	 * @param point2dWithVector
	 *            欲與之判斷的向量
	 * @return 向量是否相同
	 */
	public boolean areTheSameAs(Point2DWithVector point2dWithVector)
	{
		return areTheSameAs(point2dWithVector.x, point2dWithVector.y);
	}

	/**
	 * 給兩向量，判斷是否相同。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 * @return 向量是否相同
	 */
	public boolean areTheSameAs(double x, double y)
	{
		if (distance(x, y) > getPrecision())
			return false;
		return true;
	}

	/**
	 * 取得向量長度。
	 * 
	 * @return 向量長度
	 */
	public double getLength()
	{
		return distance(origin);
	}

	/**
	 * 取得向量誤差容許度。
	 * 
	 * @return 向量誤差容許度
	 */
	public double getPrecision()
	{
		return 1e-6;
	}
}
