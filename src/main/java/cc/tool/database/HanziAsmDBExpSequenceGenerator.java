package cc.tool.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import idsrend.CharComponentStructureAdjuster.IDSnormalizer;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.parser.IDSParser;

/**
 * 由漢字組建資料庫產生展開式。
 * 
 * @author Ihc
 */
public class HanziAsmDBExpSequenceGenerator
{
	/** 佮主機資料庫的連線 */
	PgsqlConnection 連線;
	/** 攏總更新幾筆 */
	int 上傳筆數;
	/** 正規化工具 */
	IDSnormalizer 部件正規化;

	/**
	 * 主函式。
	 * 
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		HanziAsmDBExpSequenceGenerator 產生工具 = new HanziAsmDBExpSequenceGenerator();
		產生工具.執行();
		return;
	}

	/** 做代誌！！ */
	private void 執行()
	{
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		連線 = new PgsqlConnection();
		部件正規化 = new IDSnormalizer();
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
					int[] 組字式控制碼 = String2ControlCode.轉換成控制碼(組字式);
					StringBuilder 展開式 = new StringBuilder();
					for (int i = 0; i < 組字式控制碼.length; ++i)
					{
						if (CompositionMethods
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
						IDSParser 序列分析工具 = new IDSParser(展開式.toString(),
								new ExpSequenceNoLookup());
						CharComponent CharComponent = 序列分析工具.解析().firstElement();
						部件正規化.正規化(CharComponent);
						CharComponent 組字部件 = (CharComponent) CharComponent;
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
									+ String2ControlCode.轉換成字串(Integer
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
