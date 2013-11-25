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
 * 利用二元搜尋來組合兩個活字，並且依據兩個活字之間筆劃的關係，調整兩活字的間距。
 * 
 * @author Ihc
 */
public class 二元搜尋貼合工具
{

	// public 二元搜尋貼合工具()
	// {
	// this.模組 = null;
	// }
	//
	// /**
	// * 建立二元搜尋貼合工具
	// *
	// * @param 模組
	// * 要執行的二元搜尋貼合模組
	// * @throws IllegalArgumentException
	// * 若模組為null，則丟出此例外
	// */
	// public 二元搜尋貼合工具(二元搜尋貼合模組 模組) throws IllegalArgumentException
	// {
	// this.模組 = 模組;
	// if (模組 == null)
	// throw new IllegalArgumentException();
	// }

	/**
	 * 用初使化設定的模組，來產生調整後的各活字物件
	 * 
	 * @param 模組
	 *            要執行的二元搜尋貼合模組
	 * @param 活字物件
	 *            要調整的活字物件
	 */
	public void 執行(二元搜尋貼合模組 模組, 分離活字[] 活字物件)
	{
		模組.初始化(活字物件);

		double mininumValue = 模組.下限初始值(), maxinumValue = 模組.上限初始值();
		while (mininumValue + 模組.取得精確度() < maxinumValue)
		{
			double middleValue = 0.5 * (mininumValue + maxinumValue);
			模組.變形處理(middleValue);
			if (模組.活字是否太接近() ^ 模組.太接近時參數變大())
				maxinumValue = middleValue;
			else
				mininumValue = middleValue;
		}

		double 活字寬度 = 模組.活字寬度();
		模組.變形處理(mininumValue - 活字寬度 * 2.6 * 縮放參數(模組));

		double 活字相斥值 = 模組.活字相斥值();
		// TODO 人工參數
		if (活字相斥值 > 1.6)
			mininumValue += 活字寬度 * 3.0 * 縮放參數(模組);
		else if (活字相斥值 > 0.8)
			mininumValue += 0.0;
		else
			mininumValue += -活字寬度 * 1.2 * 縮放參數(模組);

		if (mininumValue < 模組.下限初始值())
			mininumValue = 模組.下限初始值();
		if (mininumValue > 模組.上限初始值())
			mininumValue = 模組.上限初始值();

		模組.變形處理(mininumValue);
		模組.最後處理();
		return;
	}

	/**
	 * 判斷模組在縮放時，參數要增加還是要減少。
	 * 
	 * @param 模組
	 *            要執行的二元搜尋貼合模組
	 * @return 若是參數和大小成正比就回傳1.0，若成反比回傳-1.0
	 */
	protected double 縮放參數(二元搜尋貼合模組 模組)
	{
		if (模組.太接近時參數變大())
			return 1.0;
		return -1.0;
	}
}
