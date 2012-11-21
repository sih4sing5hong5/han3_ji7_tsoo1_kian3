package cc.tool.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import cc.core.ChineseCharacterFormatException;

public class 構形資料庫轉至組建資料庫
{
	static final int[] 方便符號控制碼;
	static
	{
		方便符號控制碼 = 字串與控制碼轉換.轉換成控制碼("");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PgsqlConnection 選取連線 = new PgsqlConnection();
		PgsqlConnection 更新連線 = new PgsqlConnection();
		int 上傳筆數 = 0;
		try
		{
			更新連線.executeUpdate("DELETE FROM \"漢字組建\".\"檢字表\"");
			String selectQuery = "SELECT \"編號\",\"Big5\",\"Unicode\",\"連接符號\",\"部件序\""
					+ " FROM \"構形資料庫\".\"檢字表\" "
					// + " WHERE \"連接符號\" = '5' "
					+ " ORDER BY \"Unicode\" ASC, \"Big5\" ASC" /*
																 * +
																 * " LIMIT 100"
																 */;
			ResultSet rs = 選取連線.executeQuery(selectQuery);
			Vector<String> file = new Vector<String>();
			while (rs.next())
			{
				// int 字體 = Integer.parseInt(rs.getString("字體"));
				// 字體 <<= 16;
				// int[] 字碼 = 字串與控制碼轉換.轉換成控制碼(rs.getString("字碼"));// TODO
				// if (字碼.length == 1)
				// 字體 += 字碼[0];
				// else
				// 字體 = 0;
				// System.out.println(字碼.length + " " + 字碼[0]);

				int 連接符號 = Integer.parseInt(rs.getString("連接符號"));
				String 部件序 = rs.getString("部件序");
				int[] 部件序控制碼 = 字串與控制碼轉換.轉換成控制碼(部件序);
				if (1 <= 連接符號 && 連接符號 <= 3)
				{
					if (部件序控制碼.length == 2)
					{
						String 構字符號 = new String();
						switch (連接符號)
						{
						case 1:
							構字符號 = "⿱";
							break;
						case 2:
							構字符號 = "⿰";
							break;
						case 3:
							構字符號 = "⿴";
							break;
						default:
							break;
						}
						String insertQuery = "INSERT INTO \"漢字組建\".\"檢字表\" "
								+ "(\"Unicode\",\"Big5\",\"構形資料庫編號\",\"構字式\") "
								+ " VALUES ('" + rs.getString("Unicode")
								+ "','" + rs.getString("Big5") + "','"
								+ rs.getString("編號") + "','" + 部件序 + "')";
						更新連線.executeUpdate(insertQuery);
						上傳筆數++;
					}
					else
					{
						// TODO
						System.out.println(部件序 + " " + rs.getString("編號"));
					}
				}
				else if (連接符號 == 5)
				{
					if (部件序控制碼.length != 2)
						System.out.println("有問題！！ " + 部件序);
					else
						上傳筆數++;
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
		System.out.println("上傳筆數=" + 上傳筆數);
	}

	private String 產生構字式(int[] 部件序控制碼, String 構字符號) throws IOException
	{
		int 註標 = 0;
		int 部件數 = 0;
		while (註標 < 部件序控制碼.length)
		{
			if (是否為方便符號(部件序控制碼[註標]))
				註標 += 2;
			else
				註標++;
			部件數++;
		}
		StringBuilder 構字式 = new StringBuilder();
		註標 = 0;
		while (註標 < 部件序控制碼.length)
		{
			if (部件數 > 1)
				構字式.append(構字符號);
			if (是否為方便符號(部件序控制碼[註標]))
			{
				構字式.append(展開方便符號(部件序控制碼[註標], Character.toChars(部件序控制碼[註標 + 1])));
				註標 += 2;
			}
			else
			{
				構字式.append(Character.toChars(部件序控制碼[註標]));
				註標++;
			}
			部件數--;
		}
		return 構字式.toString();
	}

	public boolean 是否為方便符號(int 控制碼)
	{
		for (int i = 0; i < 方便符號控制碼.length; ++i)
		{
			if (方便符號控制碼[i] == 控制碼)
				return true;
		}
		return false;
	}

	public StringBuilder 展開方便符號(int 控制碼, char[] 部件) throws IOException
	{
		StringBuilder 構字式 = new StringBuilder();
		int 方便符號編號 = 方便符號控制碼[0] - 控制碼;
		switch (方便符號編號)
		{
		case 0:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 1:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 2:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 3:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 4:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 5:
			構字式.append("⿱");
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 6:
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append("⿱");
			構字式.append(部件);
			構字式.append(部件);
			break;
		case 7:
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append("⿰");
			構字式.append(部件);
			構字式.append(部件);
			break;
		default:
			throw new IOException();
		}
		return 構字式;
	}
}
