package cc.setting.piece;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;
import cc.tool.database.資料庫命令字串;

/**
 * 連線到資料庫查展開式。
 * 
 * @author Ihc
 */
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
		資料庫命令字串 查詢命令 = new 資料庫命令字串(
				"SELECT \"統一碼\", \"構型資料庫字體號碼\", \"構型資料庫字型號碼\" "
						+ "FROM \"漢字組建\".\"檢字表\" " + "WHERE \"展開式\"=");
		查詢命令.加變數(展開式);
		查詢命令.加命令(" ORDER BY \"漢字組建\".\"檢字表\".\"構形資料庫編號\" ASC");
		// System.out.println(查詢命令);
		try
		{
			ResultSet 查詢結果 = 連線.executeQuery(查詢命令.toString());
			if (!查詢結果.next())
				return null;
			String 統一碼結果 = 查詢結果.getString("統一碼");
			int 統一碼 = -1;
			if (統一碼結果 != null)
				統一碼 = Integer.parseInt(統一碼結果, 16);
			String 字體結果 = 查詢結果.getString("構型資料庫字體號碼");
			int 構型資料庫字體號碼 = -1;
			if (字體結果 != null)
				構型資料庫字體號碼 = Integer.parseInt(字體結果);
			String 字型結果 = 查詢結果.getString("構型資料庫字型號碼");
			int 構型資料庫字型號碼 = -1;
			if (字型結果 != null)
				構型資料庫字型號碼 = Integer.parseInt(字型結果, 16);
			查詢結果.close();
			// System.out.println("查＝" + 展開式 + " 提著＝" + 統一碼 + " " + 構型資料庫字體號碼
			// + " " + 字型結果);
			return new 通用字型號碼(統一碼, 構型資料庫字體號碼, 構型資料庫字型號碼);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
