package cc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;
import cc.tool.database.資料庫命令字串;

public class 資料庫連線異寫式查詢 implements 異寫式查詢工具
{
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/**
	 * 初使化物件
	 * 
	 * @param 連線
	 *            佮資料庫的連線
	 */
	public 資料庫連線異寫式查詢(PgsqlConnection 連線)
	{
		this.連線 = 連線;
	}

	@Override
	public String 查異寫組字式(String 組字式, int 異寫字編號)
	{
		String 異寫式 = null;
		資料庫命令字串 查詢指令 = new 資料庫命令字串(
				"SELECT \"乙\".\"組字式\" "+
				"FROM \"漢字組建\".\"異寫字表\" AS \"甲\",\"漢字組建\".\"異寫字表\" AS \"乙\" "+
						"WHERE \"甲\".\"組字式\"=");
		查詢指令.加變數(組字式);
		查詢指令.加命令(" AND \"甲\".\"異寫組別\"=\"乙\".\"異寫組別\" AND \"乙\".\"異寫編號\"=");
		查詢指令.加變數(Integer.toHexString(異寫字編號));		
		try
		{
			ResultSet 查詢結果 = 連線.executeQuery(查詢指令.toString());
			if (查詢結果.next())
				異寫式 = 查詢結果.getString("組字式");
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
		return 異寫式;
	}
}
