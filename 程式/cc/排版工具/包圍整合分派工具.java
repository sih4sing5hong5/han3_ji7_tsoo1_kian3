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
package cc.排版工具;

import java.util.Hashtable;

import org.slf4j.Logger;

import 漢字組建.部件.字部件;
import 漢字組建.部件.部件;
import cc.活字.PieceMovableTypeTzu;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 整合所有包圍工具，讓增減工具方便又不用修改其他程式碼。
 * 
 * @author Ihc
 */
public class 包圍整合分派工具
{
	/** 記錄程式狀況 */
	Logger 記錄工具;

	/** 記錄哪些包圍部件（Unicode編碼）對應到哪些包圍工具 */
	private Hashtable<Integer, 物件活字包圍工具> 包圍部件工具表;

	/** 若包圍部件無定義，愛用的工具 */
	private 物件活字包圍工具 無支援暫時用包圍工具 = null;

	/** 建立分派工具。 */
	public 包圍整合分派工具()
	{
		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());

		包圍部件工具表 = new Hashtable<Integer, 物件活字包圍工具>();
	}

	/**
	 * 加入一個包圍工具。若有與之前重覆的包圍部件，以先加入的為準。
	 * 
	 * @param 包圍工具
	 *            欲加入的工具
	 * @return 是否有和之前加入的包圍部件有重覆
	 */
	public boolean add(物件活字包圍工具 包圍工具)
	{
		boolean 無重覆 = true;
		int[] 支援包圍部件控制碼清單 = 包圍工具.取得支援包圍部件控制碼清單();
		記錄工具.debug("「{}」支援「{}」組合符號", 包圍工具.getClass().toString(), 支援包圍部件控制碼清單);
		for (int 支援包圍部件控制碼 : 支援包圍部件控制碼清單)
		{
			if (包圍部件工具表.containsKey(支援包圍部件控制碼))
			{
				記錄工具.info("「{}」的「{}」組合符號佮儂仝款", 包圍工具.getClass().toString(),
						支援包圍部件控制碼);
				無重覆 = false;
			}
			else
			{
				包圍部件工具表.put(支援包圍部件控制碼, 包圍工具);
			}
		}
		return 無重覆;
	}

	/**
	 * 設定一个無支援暫時用包圍工具，拄著無
	 * 
	 * @param 包圍工具
	 *            無字欲用的工具
	 */
	public void 設定無支援暫時用包圍工具(物件活字包圍工具 包圍工具)
	{
		this.無支援暫時用包圍工具 = 包圍工具;
		return;
	}

	/**
	 * 將活字物件依部件屬性尋找工具組合起來，並回傳成功與否。
	 * 
	 * @param pieceMovableTypeTzu
	 *            活字物件
	 * @return 是否有相對應的包圍工具可使用
	 */
	public boolean 組合(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		字部件 chineseCharacterTzu = (字部件) pieceMovableTypeTzu
				.getChineseCharacter();
		部件 chineseCharacter = chineseCharacterTzu.底下元素()[0];
		int 外部活字控制碼 = chineseCharacter.Unicode編號();
		物件活字包圍工具 包圍工具 = 包圍部件工具表.get(外部活字控制碼);
		if (包圍工具 != null)
		{
			包圍工具.組合(pieceMovableTypeTzu);
			return true;
		}
		else if (無支援暫時用包圍工具 != null)
		{
			if (pieceMovableTypeTzu.getChineseCharacter() instanceof 部件)
				記錄工具.info("組合符號無支援「{}」組字式", ((部件) pieceMovableTypeTzu
						.getChineseCharacter()).樹狀結構組字式());
			else
				記錄工具.info("組合符號無支援「{}」部件", Character.toChars(外部活字控制碼));
			無支援暫時用包圍工具.組合(pieceMovableTypeTzu);
			return true;
		}
		return false;
	}
}
