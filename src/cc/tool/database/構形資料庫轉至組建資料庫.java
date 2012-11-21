package cc.tool.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class 構形資料庫轉至組建資料庫
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PgsqlConnection 選取連線 = new PgsqlConnection();
		PgsqlConnection 更新連線 = new PgsqlConnection();
		try
		{
			String selectQuery = "SELECT \"字體\",\"字碼\",\"Big5\",\"Unicode\",\"連接符號\",\"部件序\""
					+ " FROM \"構形資料庫\".\"檢字表\""
					+ " ORDER BY \"Unicode\" ASC, \"Big5\" ASC" + " LIMIT 100";
			ResultSet rs = 選取連線.executeQuery(selectQuery);
			Vector<String> file = new Vector<String>();
			while (rs.next())
			{
				int 字體 = Integer.parseInt(rs.getString("字體"));
				字體 <<= 16;
				String 字碼 = rs.getString("字碼");//TODO
				if (字碼.length() == 1)
					字體 += 字碼.codePointAt(0);
				else if (字碼.length() == 2)
					字體 += Character.toCodePoint(字碼.charAt(0), 字碼.charAt(1));
				else
					字體 = 0;

				int 連接符號 = Integer.parseInt(rs.getString("連接符號"));
				String 部件序 = rs.getString("部件序");
				int[] 部件序控制碼 = 字串與控制碼轉換.轉換成控制碼(部件序);
				if (1 <= 連接符號 && 連接符號 <= 3)
				{
					if (部件序控制碼.length == 2)
					{
						String insertQuery = "INSERT INTO \"漢字組建\".\"檢字表\" "
								+ "(\"Unicode\",\"Big5\",\"構形資料庫編號\",\"構字式\") "
								+ " VALUES ('" + rs.getString("Unicode")
								+ "','" + rs.getString("Big5") + "','" + 字體
								+ "','" + 部件序 + "')";
						更新連線.executeUpdate(insertQuery);
					}
					else
					{
						// TODO
					}
				}
				else
				{
					// TODO
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
