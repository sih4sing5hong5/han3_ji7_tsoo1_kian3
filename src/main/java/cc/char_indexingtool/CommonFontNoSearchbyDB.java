package cc.char_indexingtool;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc.tool.database.PgsqlConnection;
import cc.tool.database.DBCommandString;

/**
 * 連線到資料庫查展開式。
 * 
 * @author Ihc
 */
public class CommonFontNoSearchbyDB implements CommonFontNoSearch
{
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/**
	 * 初使化物件
	 * 
	 * @param 連線
	 *            佮資料庫的連線
	 */
	public CommonFontNoSearchbyDB(PgsqlConnection 連線)
	{
		this.連線 = 連線;
	}

	@Override
	public CommonFontNo 查通用字型編號(String 展開式)
	{
		DBCommandString 查詢命令 = new DBCommandString(
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
			return new CommonFontNo(統一碼, 構型資料庫字體號碼, 構型資料庫字型號碼);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// 無連線當作查無
		}
		return null;
	}
}
