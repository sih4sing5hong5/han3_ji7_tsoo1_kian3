package cc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;

/**
 * 連上漢字組建的檢字表，用統一碼控制碼去搜尋展開式。
 * 
 * @author Ihc
 */
public class 資料庫連線展開式查詢 extends 展開式查詢工具
{
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/**
	 * 初使化物件
	 * 
	 * @param 連線
	 *            佮資料庫的連線
	 */
	public 資料庫連線展開式查詢(PgsqlConnection 連線)
	{
		this.連線 = 連線;
	}

	public String 查詢展開式(int 統一碼控制碼)
	{
		String 展開式 = null;
		StringBuilder 查詢指令 = new StringBuilder(
				"SELECT \"展開式\" FROM \"漢字組建\".\"檢字表\" WHERE \"統一碼\"='");
		查詢指令.append(Integer.toHexString(統一碼控制碼));
		查詢指令.append('\'');
		try
		{
			ResultSet 查詢結果 = 連線.executeQuery(查詢指令.toString());
			if (查詢結果.next())
				展開式 = 查詢結果.getString("展開式");
			查詢結果.close();
		}
		catch (SQLException e)
		{
			//TODO 優化
			// 直接予下跤回傳控制碼
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			//TODO 優化，若無連線時會入來
			e.printStackTrace();			
		}
		return 展開式;
	}
}
