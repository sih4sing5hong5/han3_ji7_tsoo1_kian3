package cc.其他輔助工具;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.slf4j.Logger;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.core.展開式查詢工具;
import cc.core.漢字序列分析工具;
import cc.core.組字式部件;
import cc.core.組字式部件正規化;
import cc.core.組字式部件組字式建立工具;
import cc.core.資料庫連線展開式查詢;
import cc.tool.database.PgsqlConnection;
import cc.tool.database.字串與控制碼轉換;

/**
 * 予線上組字佮處理客話造字較方便的程式。
 * 
 * @author 丞宏
 */
public class 產生組字式工具
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 佮資料庫的連線 */
	protected static PgsqlConnection 連線;
	/** 定義異寫編號數字 */
	int[] 編號陣列 = 字串與控制碼轉換.轉換成控制碼("甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戍亥陰陽乾坤震巽坎離艮兌");

	/**
	 * 主函式，設定相關視窗資訊。
	 * 
	 * @param args
	 *            呼叫引數
	 */
	public static void main(String[] args)
	{
		BufferedReader 輸入 = new BufferedReader(new InputStreamReader(System.in));
		連線 = new PgsqlConnection(PgsqlConnection.url, "Ihc", "983781");
		展開式查詢工具 查詢方式 = new 資料庫連線展開式查詢(連線);
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()

		String 句 = "媠⿰丨丨丨⿱⿰⿰糹言糹攵⿰⿰糹言糹攵";
		while (句 != null)
		{
			ChineseCharacterUtility ccUtility = new 漢字序列分析工具(句, 查詢方式);
			Vector<ChineseCharacter> ccArray = ccUtility.parseText();

			組字式部件正規化 正規化工具 = new 組字式部件正規化();
			組字式部件組字式建立工具 組字式建立工具 = new 組字式部件組字式建立工具();
			for (ChineseCharacter 部件 : ccArray)
			{
				組字式部件 組字部件 = (組字式部件) 部件;
				組字部件.建立組字式(組字式建立工具);
				// 記錄工具.debug(組字部件.提到組字式());
				正規化工具.正規化(部件);
				組字部件.建立組字式(組字式建立工具);
				System.out.print(組字部件.提到組字式());
			}

			try
			{
				句 = 輸入.readLine();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
		return;
	}
}
