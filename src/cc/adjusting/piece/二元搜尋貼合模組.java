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
package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.分離活字;

/**
 * 用利用二元搜尋來組合兩個活字的模組，定義相關的動作，二元搜尋的上下限，變形方式等等細節。
 * 
 * @author Ihc
 */
public abstract class 二元搜尋貼合模組
{
	/** 使用此模組的調整工具，並使用其自身合併相關函式 */
	protected MergePieceAdjuster 調整工具;

	/**
	 * 建立二元搜尋貼合模組。
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 二元搜尋貼合模組(MergePieceAdjuster 調整工具)
	{
		this.調整工具 = 調整工具;
	}

	/**
	 * 初始化此模組。
	 * 
	 * @param rectangularAreas
	 *            要調整的物件活字
	 */
	public abstract void 初始化(分離活字[] rectangularAreas);

	/**
	 * 取得二元搜尋的初始下限。
	 * 
	 * @return 二元搜尋的初始下限
	 */
	public double 下限初始值()
	{
		return 調整工具.getPrecision();
	}

	/**
	 * 取得二元搜尋的初始上限。
	 * 
	 * @return 二元搜尋的初始上限
	 */

	public abstract double 上限初始值();

	/**
	 * 取得調整活字的最低精準度。
	 * 
	 * @return 調整活字的最低精準度
	 */
	public double 取得精確度()
	{
		return 調整工具.getPrecision();
	}

	/**
	 * 判斷兩活字之間是否太接近。
	 * 
	 * @return 兩活字之間是否太接近
	 */
	public abstract boolean 活字是否太接近();

	/**
	 * 若活字間太接近，參數是否是要調大。
	 * 
	 * @return 參數是否是要調大
	 */
	public abstract boolean 太接近時參數變大();

	/**
	 * 依參數改變活字特性。
	 * 
	 * @param middleValue
	 *            調整用參數
	 */
	public abstract void 變形處理(double middleValue);

	/**
	 * 取得目前活字的寬度
	 * 
	 */
	public double 活字寬度()
	{
		return 調整工具.平均闊度();
	}

	/**
	 * 兩活字接觸的邊界長度，用來判斷是否不適合太接近，計算相斥值。
	 * 
	 * @return 兩活字接觸的邊界長度
	 */
	public abstract double 接觸邊長();

	/**
	 * 計算兩活字是否適合太接近，相斥值越大，距離要越遠越好
	 * 
	 * @return 兩活字相斥值
	 */
	public abstract double 活字相斥值();

	/** 最後收尾的設定以及調整 */
	public abstract void 最後處理();

	/**
	 * 取得經由此模組調整後活字物件
	 * 
	 * @return 調整後活字物件
	 */
	public abstract 分離活字[] 取得調整後活字物件();
}
