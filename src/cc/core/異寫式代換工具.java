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
package cc.core;

/**
 * 共規的部件結構樹，內底的異寫式換做組字式。
 * 
 * @author Ihc
 */
public class 異寫式代換工具
{
	/** 定義異寫編號數字 */
	protected int[] 編號陣列;
	/** 異寫式查詢的方法 */
	protected 異寫式查詢工具 異寫式查詢;

	/**
	 * 建立一个代換工具。
	 * 
	 * @param 編號陣列
	 *            定義異寫編號數字
	 * @param 異寫式查詢
	 *            異寫式查詢的方法
	 */
	public 異寫式代換工具(int[] 編號陣列, 異寫式查詢工具 異寫式查詢)
	{
		this.編號陣列 = 編號陣列;
		this.異寫式查詢 = 異寫式查詢;
	}

	/**
	 * 將部件下跤的異寫式攏換掉，閣回傳上新的部件出來。 若是愛提著結果，愛記得共原本的指標換掉。
	 * 
	 * @param 部件
	 *            愛換的部件
	 * @return 換了異寫式的部件
	 */
	public ChineseCharacter 代換(ChineseCharacter 部件)
	{
		if (部件 instanceof ChineseCharacterTzu)
		{
			ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
			if (字部件.getType() == ChineseCharacterTzuCombinationType.異寫字編號符號)
				return 設定異體式(字部件);
			for (int i = 0; i < 字部件.getChildren().length; ++i)
				字部件.getChildren()[i] = 代換(字部件.getChildren()[i]);
		}
		return 部件;
	}

	/**
	 * 查異寫式所代表的組字式，順紲產生新的結果。
	 * 
	 * @param 字部件
	 *            是異寫式的主部件
	 * @return 新的異寫式部件
	 */
	protected ChineseCharacter 設定異體式(ChineseCharacterTzu 字部件)
	{
		if (字部件.getType() != ChineseCharacterTzuCombinationType.異寫字編號符號)
		{
			System.err.println("無應該入來異寫字編號符號");
			return 字部件;
		}
		int 異寫字編號 = 提著異寫式編號(字部件.getChildren()[1], 0);
		ChineseCharacter 倒爿部件 = 字部件.getChildren()[0];
		if (倒爿部件 instanceof 組字式部件)// 假設組字式佮規定的仝款
		{
			組字式部件 倒爿組字式部件 = (組字式部件) 倒爿部件;
			String 異寫字結果 = 異寫式查詢.查異寫組字式(倒爿組字式部件.提到組字式(), 異寫字編號);
			try
			{
				if (異寫字結果 != null && !異寫字結果.equals(倒爿組字式部件.提到組字式()))
				{
					展開式免查詢 查詢方式 = new 展開式免查詢();
					漢字序列分析工具 序列分析工具 = new 漢字序列分析工具(異寫字結果, 查詢方式);
					ChineseCharacter 新異寫部件;
					新異寫部件 = 序列分析工具.parseCharacter(字部件.getParent());
					return 新異寫部件;
				}
			}
			catch (ChineseCharacterFormatException e)
			{
				System.err.println("查出來的異寫字分析有問題");
			}
		}
		else
		{
			System.err.println("有異寫字，但是無支援");
		}
		return 倒爿部件;
	}

	/**
	 * 行規的異寫式的結構，並沿途算編號出來
	 * 
	 * @param 部件
	 *            編號的主結構
	 * @param 這馬數值
	 *            進前算的數值
	 * @return 上尾結果
	 */
	protected int 提著異寫式編號(ChineseCharacter 部件, int 這馬數值)
	{
		if (部件 instanceof ChineseCharacterWen)
		{
			ChineseCharacterWen 文部件 = (ChineseCharacterWen) 部件;
			return 決定異寫式編號(文部件, 這馬數值);
		}
		ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
		return 提著異寫式編號(字部件.getChildren()[1],
				提著異寫式編號(字部件.getChildren()[0], 這馬數值));
	}

	/**
	 * 共這个部件的值揣出來，閣加上進前算的數值
	 * 
	 * @param 文部件
	 *            代表一个數字的部件
	 * @param 這馬數值
	 *            進前算的數值
	 * @return 上尾結果
	 */
	protected int 決定異寫式編號(ChineseCharacterWen 文部件, int 這馬數值)
	{
		這馬數值 *= 編號陣列.length;
		for (int i = 0; i < 編號陣列.length; ++i)
			if (文部件.getCodePoint() == 編號陣列[i])
				return 這馬數值 + i;
		System.err.println("用無正確的編碼！！");
		return 這馬數值;
	}

}
