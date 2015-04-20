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
package cc.揀字工具;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;
import java.io.IOException;

/**
 * 佮幾仔个字體合做一个，方便存取佮判斷是毋是有這控制碼的字型。
 * 
 * @author Ihc
 */
public class 合併字體 extends 通用字體
{
	/** 字體的物件 */
	protected Font[] 字體集;
	/** 楷體字體下的所在 */
	static public final String[] 楷體字體位址表 = new String[] {
			"./字體/全字庫-101_11_28/TW-Kai-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Kai-Ext-B-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Kai-Plus-98_1.ttf",
			"./字體/漢字構形資料庫-2_7/cdpeudck.tte", };
	/** 宋體字體下的所在 */
	static public final String[] 宋體字體位址表 = new String[] {
			"./字體/全字庫-101_11_28/TW-Sung-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Sung-Ext-B-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Sung-Plus-98_1.ttf",
			"./字體/漢字構形資料庫-2_7/cdpeudc.tte", };
	/** 楷體字體的物件 */
	static private final 合併字體 楷體字體;
	/** 宋體字體的物件 */
	static private final 合併字體 宋體字體;
	static
	{
		楷體字體 = new 合併字體(楷體字體位址表);
		宋體字體 = new 合併字體(宋體字體位址表);
	}

	/** 予改變參數時的私有建構工具。 */
	protected 合併字體()
	{
	}

	/**
	 * 合併字體的準備工具。
	 * 
	 * @param 字體位置
	 *            字體放的所在
	 */
	public 合併字體(String[] 字體位置)
	{
		字體集 = new Font[字體位置.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			try
			{
				字體集[i] = Font.createFont(Font.TRUETYPE_FONT, new File(字體位置[i]));// TODO
																				// font
																				// type
			}
			catch (FontFormatException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public 合併字體 調整字體大小(float 字體大小)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體大小);
		}
		return 調整結果;
	}

	@Override
	public 合併字體 調整字體選項(int 字體選項)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項);
		}
		return 調整結果;
	}

	@Override
	public 合併字體 調整字體參數(int 字體選項, float 字體大小)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項, 字體大小);
		}
		return 調整結果;
	}

	@Override
	public boolean 有這个字型無(int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (Font 字體 : 字體集)
			{
				if (字體.canDisplay(控制碼))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (Font 字體 : 字體集)
			{
				if (字體.canDisplay(控制碼))
				{
					return 字體.createGlyphVector(渲染選項, Character.toChars(控制碼));
				}
			}
		}
		return null;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的楷體字體。
	 * 
	 * @return　對公家機關提來的楷體字體
	 */
	static public 合併字體 提著楷體字體()
	{
		return 楷體字體;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的宋體字體。
	 * 
	 * @return　對公家機關提來的宋體字體
	 */
	static public 合併字體 提著宋體字體()
	{
		return 宋體字體;
	}
}
