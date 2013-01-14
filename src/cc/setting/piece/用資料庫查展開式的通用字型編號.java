package cc.setting.piece;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;

public class 用資料庫查展開式的通用字型編號 implements 展開式查通用字型編號
{
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/** 初使化物件 */
	public 用資料庫查展開式的通用字型編號()
	{
		連線 = new PgsqlConnection(PgsqlConnection.url, "Ihc", "983781");// TODO
																		// 換讀取權限
	}

	@Override
	public 通用字型號碼 查通用字型編號(String 展開式)
	{
		StringBuilder 查詢命令 = new StringBuilder(
				"SELECT \"漢字組建\".\"檢字表\".\"Unicode\",\"構形資料庫\".\"檢字表\".\"字體\" "
						+ "FROM \"漢字組建\".\"檢字表\", \"構形資料庫\".\"檢字表\" "
						+ "WHERE \"漢字組建\".\"檢字表\".\"構形資料庫編號\"=\"構形資料庫\".\"檢字表\".\"編號\" "
						+ "AND \"展開式\"='");
		// StringBuilder 查詢命令 = new StringBuilder(
		// "SELECT \"漢字組建\".\"檢字表\".\"Unicode\",\"字體\" FROM \"漢字組建\".\"檢字表\", \"構形資料庫\".\"檢字表\" WHERE \"編號\"=\"\" AND \"展開式\"='");
		查詢命令.append(展開式);
		查詢命令.append("' ORDER BY \"漢字組建\".\"檢字表\".\"構形資料庫編號\" ASC");
//		System.out.println(查詢命令);
		try
		{
			ResultSet 查詢結果 = 連線.executeQuery(查詢命令.toString());
			if (!查詢結果.next())
				return null;
			String 統一碼結果 = 查詢結果.getString("Unicode");
			int 統一碼 = -1;
			if (統一碼結果 != null && !統一碼結果.equals("null"))// TODO改資料庫
				統一碼 = Integer.parseInt(統一碼結果, 16);
			String 字體結果 = 查詢結果.getString("字體");
			int 構型資料庫字體號碼 = -1;
			if (字體結果 != null && !統一碼結果.equals("null"))// TODO改資料庫
				構型資料庫字體號碼 = Integer.parseInt(字體結果);
			查詢結果.close();
//			System.out.println(統一碼 + " " + 構型資料庫字體號碼);
			return new 通用字型號碼(統一碼, 構型資料庫字體號碼);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
