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
package cc.tool.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 協助全字庫對照表資料輸入資料庫的模組。
 * 
 * @author Ihc
 */
public class 全字庫對照表輸入資料庫
{
	/** 檔案目錄 */
	private String 目錄;
	/** 所有檔案名 */
	private String[] 全部檔案;
	/** 資料庫中的表名 */
	private String 資料表;
	/** 全字庫所對應到的編碼欄位名 */
	private String 欄位名;

	/**
	 * 初使化建構函式，設定各項參數
	 * 
	 * @param 目錄
	 *            檔案目錄
	 * @param 全部檔案
	 *            所有檔案名
	 * @param 資料表
	 *            資料庫中的表名
	 * @param 欄位名
	 *            全字庫所對應到的編碼欄位名
	 */
	全字庫對照表輸入資料庫(String 目錄, String[] 全部檔案, String 資料表, String 欄位名)
	{
		this.目錄 = 目錄;
		this.全部檔案 = 全部檔案;
		this.資料表 = 資料表;
		this.欄位名 = 欄位名;
	}

	/** 將資料上傳到資料庫 */
	void 上傳()
	{
		int 上傳筆數 = 0;
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 更新連線 = new PgsqlConnection();
		try
		{
			for (String 檔案 : 全部檔案)
			{
				File file = new File(目錄 + 檔案);
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));
				String data = null;
				while ((data = bufferedReader.readLine()) != null)
				{
					if (欄位名.equals("Big5"))
					{
						StringBuilder stringBuilder = new StringBuilder();
						for (int i = 0; i < data.length(); i++)
							if (data.charAt(i) == '\t'
									|| Character.isDigit(data.charAt(i))
									|| data.charAt(i) >= 'A'
									&& data.charAt(i) <= 'F'
									|| data.charAt(i) >= 'a'
									&& data.charAt(i) <= 'f')
							{
								stringBuilder.append(data.charAt(i));
							}
						data = stringBuilder.toString();
					}
					if (!data.equals(""))
					{
						String[] array = data.split("	");
						if (array[0].length() > 1)
						{
							int 字面 = Integer.parseInt(array[0]);
							array[0] = Integer.toHexString(字面);
						}
						String insertQuery = "INSERT INTO \"全字庫\".\"" + 資料表
								+ "\" " + "(\"Cns11643\",\"" + 欄位名 + "\") "
								+ " VALUES ('" + array[0] + array[1] + "','"
								+ array[2] + "')";
						更新連線.executeUpdate(insertQuery);
						上傳筆數++;
					}
				}
				bufferedReader.close();
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("結束嘍～～ 時間：" + System.currentTimeMillis());
		System.out.println("上傳筆數=" + 上傳筆數);
		return;
	}
}
