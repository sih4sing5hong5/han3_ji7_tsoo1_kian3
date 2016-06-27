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
package cc.char_indexingtool;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * 愛予設定工具揣字的介面。
 * 
 * @author Ihc
 */
public abstract class CommonFont
{
	// /** 活字字體的選項 */
	// private int 字體選項;
	// /** 活字的大小 */
	// private int 字體大小;
	/**
	 * 調整字體的字型大細。
	 * 
	 * @param 字體大小
	 *            欲調整到偌大
	 * @return　調整好的字體
	 */
	public abstract CommonFont 調整字體大小(float 字體大小);

	/**
	 * 選擇字體的性質。
	 * 
	 * @param 字體選項
	 *            欲調整的的性質
	 * @return　調整好的字體
	 */
	public abstract CommonFont 調整字體選項(int 字體選項);

	/**
	 * 改變字體的參數。
	 * 
	 * @param 字體選項
	 *            欲調整的的性質
	 * @param 字型大小
	 *            欲調整到偌大
	 * @return　調整好的字體
	 */
	public abstract CommonFont 調整字體參數(int 字體選項, float 字型大小);

	/**
	 * 判斷這个字體的預設字體有法度顯示這个控制號碼無。
	 * 
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @return 字體敢有這个控制碼的字型無
	 */
	public boolean 有這个字型無(int 控制碼)
	{
		return 有這个字型無(控制碼, 0);
	}

	/**
	 * 判斷這个字體的預設字體有法度顯示這个控制號碼無。
	 * 
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @param 字體編號
	 *            選擇佗一个字體
	 * @return 提著這字體這个控制碼的字型，若無，回傳<code>null</code>
	 */
	public abstract boolean 有這个字型無(int 控制碼, int 字體編號);

	/**
	 * 提到預設字體的控制號碼字型。
	 * 
	 * @param 渲染選項
	 *            活字的渲染屬性
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @return 字體敢有這个控制碼的字型無
	 */
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼)
	{
		return 提這个字型(渲染選項, 控制碼, 0);
	}

	/**
	 * 提到特地字體的控制號碼字型。
	 * 
	 * @param 渲染選項
	 *            活字的渲染屬性
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @param 字體編號
	 *            選擇佗一个字體
	 * @return 提著這字體這个控制碼的字型，若無，回傳<code>null</code>
	 */
	public abstract GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號);

	/**
	 * 看覓這个字體敢有字型號碼指定的字型向量。
	 * 
	 * @param 字型號碼
	 *            字型的資料
	 * @return 這个字體有字型號碼指定的字型向量無
	 */
	public boolean 有這个字型無(CommonFontNo 字型號碼)
	{
		if (字型號碼 == null)
			return false;
		boolean 有無 = false;
		if (字型號碼.有統一碼無())
			有無 |= 有這个字型無(字型號碼.提統一碼());
		if (字型號碼.有構型資料庫號碼資料無())
			有無 |= 有這个字型無(字型號碼.提構型資料庫字型號碼(), 字型號碼.提構型資料庫字體號碼());
		return 有無;
	}

	/**
	 * 佇這个渲染選項，提出字型號碼指定的字型向量。
	 * 
	 * @param 渲染選項
	 *            想欲愛的渲染參數
	 * @param 字型號碼
	 *            字型的資料
	 * @return 這个字體的字型。若字體無這个字型，回傳<code>null</code>
	 */
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, CommonFontNo 字型號碼)
	{
		// System.out.println(字型號碼.提統一碼() + " " + 字型號碼.提構型資料庫字體號碼());
		if (字型號碼 == null)
			return null;
		GlyphVector 字型 = null;
		if (字型號碼.有統一碼無())
			字型 = 提這个字型(渲染選項, 字型號碼.提統一碼());
		if (字型 != null)
			return 字型;
		if (字型號碼.有構型資料庫號碼資料無())
			字型 = 提這个字型(渲染選項, 字型號碼.提構型資料庫字型號碼(), 字型號碼.提構型資料庫字體號碼());
		return 字型;
	}
}
