package cc.othertools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.slf4j.Logger;

import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup_byDB;
import cc.tool.database.PgsqlConnection;
import cc.tool.database.String2ControlCode;
import idsrend.CharComponentStructureAdjuster.IDSnormalizer;
import idsrend.charcomponent.CharComponent;
import idsrend.parser.IDSParser;

/**
 * 予線上組字佮處理客話造字較方便的程式。
 * 
 * @author 丞宏
 */
public class IDSGenerator
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 佮資料庫的連線 */
	protected static PgsqlConnection 連線;
	/** 定義異寫編號數字 */
	int[] 編號陣列 = String2ControlCode.轉換成控制碼("甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戍亥陰陽乾坤震巽坎離艮兌");

	/**
	 * 主函式，設定相關視窗資訊。
	 * 
	 * @param args
	 *            呼叫引數
	 */
	public static void main(String[] args)
	{
		BufferedReader 輸入 = new BufferedReader(new InputStreamReader(System.in));
		連線 = new PgsqlConnection();
		ExpSequenceLookup 查詢方式 = new ExpSequenceLookup_byDB(連線);
		// TODO ExpSequenceLookup_byDB(連線) ExpSequenceNoLookup()

		String 句 = "媠⿰丨丨丨⿱⿰⿰糹言糹攵⿰⿰糹言糹攵";
		while (句 != null)
		{
			IDSParser ccUtility = new IDSParser(句, 查詢方式);
			Vector<CharComponent> ccArray = ccUtility.解析();

			IDSnormalizer 正規化工具 = new IDSnormalizer();
			for (CharComponent CharComponent : ccArray)
			{
				CharComponent 組字部件 = (CharComponent) CharComponent;
				組字部件.樹狀結構組字式();
				// 記錄工具.debug(組字部件.提到組字式());
				正規化工具.正規化(CharComponent);
				組字部件.樹狀結構組字式();
				System.out.print(組字部件.樹狀結構組字式());
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
