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

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;
import cc.tool.database.DBCommandString;

/**
 * 連線到資料庫查展開式。
 * 
 * @author Ihc
 */
public class CommonFontNoSearchbyDB implements CommonFontNoSearch
{
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/**
	 * 初使化物件
	 * 
	 * @param 連線
	 *            佮資料庫的連線
	 */
	public CommonFontNoSearchbyDB(PgsqlConnection 連線)
	{
		this.連線 = 連線;
	}

	@Override
	public CommonFontNo 查通用字型編號(String 展開式)
	{
		DBCommandString 查詢命令 = new DBCommandString(
				"SELECT \"統一碼\", \"構型資料庫字體號碼\", \"構型資料庫字型號碼\" "
						+ "FROM \"漢字組建\".\"檢字表\" " + "WHERE \"展開式\"=");
		查詢命令.加變數(展開式);
		查詢命令.加命令(" ORDER BY \"漢字組建\".\"檢字表\".\"構形資料庫編號\" ASC");
		// System.out.println(查詢命令);
		try
		{
			ResultSet 查詢結果 = 連線.executeQuery(查詢命令.toString());
			if (!查詢結果.next())
				return null;
			String 統一碼結果 = 查詢結果.getString("統一碼");
			int 統一碼 = -1;
			if (統一碼結果 != null)
				統一碼 = Integer.parseInt(統一碼結果, 16);
			String 字體結果 = 查詢結果.getString("構型資料庫字體號碼");
			int 構型資料庫字體號碼 = -1;
			if (字體結果 != null)
				構型資料庫字體號碼 = Integer.parseInt(字體結果);
			String 字型結果 = 查詢結果.getString("構型資料庫字型號碼");
			int 構型資料庫字型號碼 = -1;
			if (字型結果 != null)
				構型資料庫字型號碼 = Integer.parseInt(字型結果, 16);
			查詢結果.close();
			// System.out.println("查＝" + 展開式 + " 提著＝" + 統一碼 + " " + 構型資料庫字體號碼
			// + " " + 字型結果);
			return new CommonFontNo(統一碼, 構型資料庫字體號碼, 構型資料庫字型號碼);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// 無連線當作查無
		}
		return null;
	}
}
