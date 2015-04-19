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

import 漢字組建.解析工具.組字式序列解析工具;
import 漢字組建.部件.組合方式;
import 漢字組建.部件.部件;
import 漢字組建.部件結構調整工具.組字式結構正規化工具;
import cc.core.展開式免查詢;

/**
 * 由漢字組建資料庫產生展開式。
 * 
 * @author Ihc
 */
public class 漢字組建資料庫產生展開式
{
	/** 佮主機資料庫的連線 */
	PgsqlConnection 連線;
	/** 攏總更新幾筆 */
	int 上傳筆數;
	/** 正規化工具 */
	組字式結構正規化工具 部件正規化;

	/**
	 * 主函式。
	 * 
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		漢字組建資料庫產生展開式 產生工具 = new 漢字組建資料庫產生展開式();
		產生工具.執行();
		return;
	}

	/** 做代誌！！ */
	private void 執行()
	{
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		連線 = new PgsqlConnection();
		部件正規化 = new 組字式結構正規化工具();
		上傳筆數 = 0;
		try
		{
			// 連線.executeUpdate("UPDATE \"漢字組建\".\"檢字表\" SET \"展開式\" = null");
			String selectAllQuery = "SELECT \"構形資料庫編號\""
					+ " FROM \"漢字組建\".\"檢字表\" " + " ORDER BY \"構形資料庫編號\" ASC"
			// + " LIMIT 100"
			;
			ResultSet allDataNumber = 連線.executeQuery(selectAllQuery);
			while (allDataNumber.next())
			{
				String 構形資料庫編號 = allDataNumber.getString("構形資料庫編號");
				String 選擇目前欲處理之字 = "SELECT \"統一碼\",\"構形資料庫編號\",\"組字式\",\"展開式\" "
						+ " FROM \"漢字組建\".\"檢字表\" WHERE \"構形資料庫編號\"='"
						+ 構形資料庫編號 + "'";
				取得該展開式(連線.executeQuery(選擇目前欲處理之字));
			}
		} catch (SQLException e)
		{
			System.err.println("巡訪時發現錯誤！！！ ");
			e.printStackTrace();
		}
		System.out.println("結束嘍～～ 時間：" + System.currentTimeMillis());
		System.out.println("上傳筆數=" + 上傳筆數);
	}

	/**
	 * 提著這逝資料的展開式，愛保證一定有組字式
	 * 
	 * @param 要處理的目標
	 *            愛揣的一逝資料
	 * @return 這逝資料的展開式
	 */
	private String 取得該展開式(ResultSet 要處理的目標)
	{
		return 取得該展開式(要處理的目標, 0);
	}

	/**
	 * 提著這逝資料的展開式
	 * 
	 * @param 要處理的目標
	 *            愛揣的一逝資料
	 * @param 控制碼
	 *            若無資料，當作是這个字元
	 * @return 這逝資料的展開式
	 */
	private String 取得該展開式(ResultSet 要處理的目標, int 控制碼)
	{
		String 所求展開式 = null;
		try
		{
			if (!要處理的目標.next())// 無組字式
			{
				所求展開式 = new String(Character.toChars(控制碼));
			} else
			{
				String 目標展開式 = 要處理的目標.getString("展開式");
				if (目標展開式 != null)// 處理過矣
				{
					所求展開式 = 目標展開式;
				} else
				{
					String 組字式 = 要處理的目標.getString("組字式");
					if (組字式 == null)// 無應該出現的現象
					{
						throw new RuntimeException("組字式有問題！！");
					}
					int[] 組字式控制碼 = 字串與控制碼轉換.轉換成控制碼(組字式);
					StringBuilder 展開式 = new StringBuilder();
					for (int i = 0; i < 組字式控制碼.length; ++i)
					{
						if (組合方式
								.isCombinationType(組字式控制碼[i]))
						{
							展開式.append(Character.toChars(組字式控制碼[i]));
						} else
						{
							// TODO 統一碼有兩個的情況愛考慮，目前先選第一个
							String 選取目標 = "SELECT \"統一碼\",\"構形資料庫編號\",\"組字式\",\"展開式\" "
									+ " FROM \"漢字組建\".\"檢字表\" WHERE \"統一碼\"='"
									+ Integer.toHexString(組字式控制碼[i])
									+ "' ORDER BY \"構形資料庫編號\" ASC LIMIT 1";
							展開式
									.append(取得該展開式(連線.executeQuery(選取目標),
											組字式控制碼[i]));
						}
					}
					String 目標編號 = 要處理的目標.getString("構形資料庫編號");
					if (目標編號 != null)
					{
						組字式序列解析工具 序列分析工具 = new 組字式序列解析工具(展開式.toString(),
								new 展開式免查詢());
						部件 部件 = 序列分析工具.解析().firstElement();
						部件正規化.正規化(部件);
						部件 組字部件 = (部件) 部件;
						組字部件.樹狀結構組字式();
						所求展開式 = 組字部件.樹狀結構組字式();
						String 更新目標 = "UPDATE \"漢字組建\".\"檢字表\" "
								+ "SET \"展開式\"='" + 所求展開式 + "' "
								+ "WHERE \"構形資料庫編號\"='" + 目標編號 + "'";
						連線.executeUpdate(更新目標);
						上傳筆數++;
						String 目標控制碼 = 要處理的目標.getString("統一碼");
						if (目標控制碼 == null)
							System.out.println("上傳筆數=" + 上傳筆數 + ' ' + 所求展開式
									+ ' ' + 目標控制碼);
						else
							System.out.println("上傳筆數="
									+ 上傳筆數
									+ ' '
									+ 所求展開式
									+ ' '
									+ 目標控制碼
									+ ' '
									+ 字串與控制碼轉換.轉換成字串(Integer
											.parseInt(目標控制碼, 16)));
					} else
					// 應該袂入來
					{
						throw new RuntimeException("程式有問題！！");
					}
				}
			}
		} catch (SQLException e)
		{
			System.err.println("展開時發現錯誤！！！ ");
			e.printStackTrace();
		} catch (RuntimeException e)
		{
			e.printStackTrace();
		}
		try
		{
			要處理的目標.close();
		} catch (SQLException e)
		{
			System.err.println("連線關掉時發現錯誤！！！ ");
			e.printStackTrace();
		}
		return 所求展開式;
	}
}
