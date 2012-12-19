package cc.core;

import cc.tool.database.字串與控制碼轉換;

/**
 * 單字查詢展開式介面。
 * 
 * @author Ihc
 */
abstract class 展開式查詢工具
{
	/**
	 * 查詢其展開式，若無，回傳null。
	 * 
	 * @param 統一碼控制碼
	 *            欲查的控制碼
	 * @return 這字的展開式
	 */
	public abstract String 查詢展開式(int 統一碼控制碼);

	/**
	 * 查詢字串第一个字的展開式，若無，回傳null。
	 * 
	 * @param 待查文字
	 *            欲查的字
	 * @return 第一个字的展開式
	 */
	public String 查詢展開式(String 待查文字)
	{
		int[] 待查控制碼 = 字串與控制碼轉換.轉換成控制碼(待查文字);
		return 查詢展開式(待查控制碼[0]);
	}
}
