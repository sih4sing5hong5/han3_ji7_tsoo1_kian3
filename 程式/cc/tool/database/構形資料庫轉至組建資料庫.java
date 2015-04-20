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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 把構形資料庫轉譯到漢字組建資料庫的型態。
 * 
 * @author Ihc
 */
public class 構形資料庫轉至組建資料庫
{
	/** 中央研究院構字式所用的所有方便符號 */
	static final int[] 方便符號控制碼;
	static
	{
		方便符號控制碼 = 字串與控制碼轉換.轉換成控制碼("");
	}

	/**
	 * 主函式。
	 * 
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 選取連線 = new PgsqlConnection();
		PgsqlConnection 更新連線 = new PgsqlConnection();
		構形資料庫轉至組建資料庫 轉移工具 = new 構形資料庫轉至組建資料庫();
		int 上傳筆數 = 0;
		try
		{
			// 更新連線.executeUpdate("DELETE FROM \"漢字組建\".\"檢字表\"");
			String selectQuery = "SELECT \"編號\",\"字體\",\"字碼\",\"Unicode\",\"連接符號\",\"部件序\""
					+ " FROM \"構形資料庫\".\"檢字表\" "
					// + " WHERE \"連接符號\" = '5' "
					+ " ORDER BY \"編號\" ASC"
			// + " LIMIT 100"
			// 註解好用
			;
			ResultSet rs = 選取連線.executeQuery(selectQuery);
			while (rs.next())
			{
				int 連接符號 = Integer.parseInt(rs.getString("連接符號"));
				String 部件序 = rs.getString("部件序");
				int[] 部件序控制碼 = 字串與控制碼轉換.轉換成控制碼(部件序);
				String 構字符號 = null;
				switch (連接符號)
				{
				case 1:
					構字符號 = "⿰";
					break;
				case 2:
					構字符號 = "⿱";
					break;
				case 3:
					構字符號 = "⿴";
					break;
				default:
					break;
				}
				try
				{
					switch (連接符號)
					{
					case 1:
					case 2:
					case 3:
					case 5:
						String 字型號碼 = Integer.toHexString(字串與控制碼轉換.轉換成控制碼(rs
								.getString("字碼"))[0]);
						String 統一碼 = rs.getString("Unicode");
						if (統一碼 != null)
							統一碼 = 統一碼.toLowerCase();
						資料庫命令字串 新增命令 = new 資料庫命令字串(
								"INSERT INTO \"漢字組建\".\"檢字表\" "
										+ "(\"統一碼\","
										+ "\"構形資料庫編號\",\"構型資料庫字體號碼\",\"構型資料庫字型號碼\","
										+ "\"組字式\") " + " VALUES (");
						新增命令.加變數(統一碼);
						新增命令.加命令(',');
						新增命令.加變數(rs.getString("編號"));
						新增命令.加命令(',');
						新增命令.加變數(rs.getString("字體"));
						新增命令.加命令(',');
						新增命令.加變數(字型號碼);
						新增命令.加命令(',');
						新增命令.加變數(轉移工具.產生構字式(部件序控制碼, 構字符號));
						新增命令.加命令(")");
						// System.out.println(新增命令.toString());
						更新連線.executeUpdate(新增命令.toString());
						上傳筆數++;
						break;
					default:
						break;

					}
				}
				catch (構字式格式錯誤例外 e)
				{
					System.err.println("發現錯誤！！！ " + rs.getString("編號") + " "
							+ 部件序);
					e.printStackTrace();
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("結束嘍～～ 時間：" + System.currentTimeMillis());
		System.out.println("上傳筆數=" + 上傳筆數);
	}

	/**
	 * 將構字式的部件序，轉換成漢字組建的組字式。
	 * 
	 * @param 部件序控制碼
	 *            部件序的控制碼陣列
	 * @param 構字符號
	 *            此構字式的構字符號，若無傳入<code>null</code>
	 * @return 所求組字式
	 * @throws 構字式格式錯誤例外
	 *             構字式裡應只有一個部件，卻用到構字符號
	 */
	private String 產生構字式(int[] 部件序控制碼, String 構字符號) throws 構字式格式錯誤例外
	{
		int 註標 = 0;
		int 部件數 = 0;
		while (註標 < 部件序控制碼.length)
		{
			if (是否為方便符號(部件序控制碼[註標]))
				註標 += 2;
			else
				註標++;
			部件數++;
		}
		// System.err.println(部件數);
		StringBuilder 構字式 = new StringBuilder();
		註標 = 0;
		while (註標 < 部件序控制碼.length)
		{
			if (部件數 > 1)
			{
				if (構字符號 == null)
					throw new 構字式格式錯誤例外("明明是方便符號，部件序裡卻有兩個部件！！");
				else
					構字式.append(構字符號);
			}
			if (是否為方便符號(部件序控制碼[註標]))
			{
				構字式.append(展開方便符號(部件序控制碼[註標], Character.toChars(部件序控制碼[註標 + 1])));
				註標 += 2;
			}
			else
			{
				構字式.append(Character.toChars(部件序控制碼[註標]));
				註標++;
			}
			部件數--;
		}
		return 構字式.toString();
	}

	/**
	 * 判斷一控制碼是否為構形資料庫中的方便符號
	 * 
	 * @param 控制碼
	 *            欲判斷的字
	 * @return 是否為構形資料庫中的方便符號
	 */
	public boolean 是否為方便符號(int 控制碼)
	{
		for (int i = 0; i < 方便符號控制碼.length; ++i)
		{
			if (方便符號控制碼[i] == 控制碼)
				return true;
		}
		return false;
	}

	/**
	 * 把使用方便符號的構字式，展開成組字式
	 * 
	 * @param 控制碼
	 *            方便符號的控制碼
	 * @param 部件
	 *            方便符號所複製的部件
	 * @return 相對應的組字式
	 * @throws 構字式格式錯誤例外
	 *             若傳入的不是方便符號
	 */
	public StringBuilder 展開方便符號(int 控制碼, char[] 部件) throws 構字式格式錯誤例外
	{
		StringBuilder 構字式 = new StringBuilder();
		int 方便符號編號 = 控制碼 - 方便符號控制碼[0];
		switch (方便符號編號)
		{
		case 0:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 1:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 2:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 3:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 4:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 5:
			構字式.append("⿱");
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 6:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 7:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		default:
			throw new 構字式格式錯誤例外("傳入的不是方便符號！！");
		}
		return 構字式;
	}
}
