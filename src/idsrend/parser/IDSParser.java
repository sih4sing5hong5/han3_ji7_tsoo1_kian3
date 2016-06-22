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
package idsrend.parser;

import java.util.Vector;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.tool.database.String2ControlCode;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;

/**
 * 新的漢字序列分析工具。分析漢字序列的時陣，攏佇這个物件中處理，免予字部件處理。
 * 
 * @author Ihc
 */
public class IDSParser
{
	/** 愛分析的統一碼控制碼陣列 */
	protected int[] 統一碼控制碼;
	/** 目前處理到的控制碼位置 */
	protected int 陣列位置;
	/** 查詢展開式的工具 */
	ExpSequenceLookup 展開式查詢;

	/**
	 * 用字串佮查詢工具初使化物件。
	 * 
	 * @param 漢字字串
	 *            愛分析的字串
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public IDSParser(String 漢字字串, ExpSequenceLookup 展開式查詢)
	{
		this(String2ControlCode.轉換成控制碼(漢字字串), 展開式查詢);
	}

	/**
	 * 用統一碼控制碼佮查詢工具初使化物件。
	 * 
	 * @param 統一碼控制碼
	 *            愛分析的統一碼控制碼陣列
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public IDSParser(int[] 統一碼控制碼, ExpSequenceLookup 展開式查詢)
	{
		this.統一碼控制碼 = 統一碼控制碼;
		this.陣列位置 = 0;
		this.展開式查詢 = 展開式查詢;
	}

	/**
	 * 分析字串並回傳字串中全部的漢字部件
	 * 
	 * @return 字串中全部的漢字部件。若字串格式有錯，不完整的部件不會被加上去，並且在陣列最後會補上一個null當作通知
	 */
	public Vector<CharComponent> 解析()
	{
		Vector<CharComponent> vector = new Vector<CharComponent>();
		try
		{
			while (!組合式是毋是結束矣())
			{
				vector.add(解析一個組字式());
			}
		}
		catch (IDSExecption e)
		{
			vector.add(null);
		}
		return vector;
	}

	public CharComponent 解析一個組字式() throws IDSExecption
	{
		if (組合式是毋是結束矣())
			throw new IDSExecption();
		CharComponent chineseCharacter = null;
		if (CompositionMethods.isCombinationType(目前控制碼()))
		{
			FinalCharComponent chineseCharacterTzu = new FinalCharComponent(目前控制碼());
			下一个控制碼();
			for (int i = 0; i < chineseCharacterTzu.底下元素().length; ++i)
			{
				chineseCharacterTzu.底下元素()[i] = 解析一個組字式();
			}
			chineseCharacter = chineseCharacterTzu;
		}
		else
		{
			String 展開式 = null;
			展開式 = 展開式查詢.查詢展開式(目前控制碼());
			if (展開式 == null)
				chineseCharacter = new NonFinalCharComponent(目前控制碼());
			else
			{
				IDSParser 分析工具 = new IDSParser(展開式, new ExpSequenceNoLookup());// 避免把異體字給展開
				chineseCharacter = 分析工具.解析一個組字式();
			}
			下一个控制碼();
		}
		return chineseCharacter;
	}

	/**
	 * 提到目前的控制碼。
	 * 
	 * @return 目前的控制碼
	 */
	protected int 目前控制碼()
	{
		return 統一碼控制碼[陣列位置];
	}

	/** 換到下一个控制碼。 */
	protected void 下一个控制碼()
	{
		陣列位置++;
		return;
	}

	protected boolean 組合式是毋是結束矣()
	{
		return 統一碼控制碼.length == 陣列位置;
	}
}
