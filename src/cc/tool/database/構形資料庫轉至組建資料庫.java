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
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 選取連線 = new PgsqlConnection();
		PgsqlConnection 更新連線 = new PgsqlConnection();
		構形資料庫轉至組建資料庫 轉移工具 = new 構形資料庫轉至組建資料庫();
		int 上傳筆數 = 0;
		try
		{
			更新連線.executeUpdate("DELETE FROM \"漢字組建\".\"檢字表\"");
			String selectQuery = "SELECT \"編號\",\"Big5\",\"Unicode\",\"連接符號\",\"部件序\""
					+ " FROM \"構形資料庫\".\"檢字表\" "
					// + " WHERE \"連接符號\" = '5' "
					+ " ORDER BY \"Unicode\" ASC, \"Big5\" ASC"
			// + " LIMIT 100"
			;
			ResultSet rs = 選取連線.executeQuery(selectQuery);
			while (rs.next())
			{
				int 連接符號 = Integer.parseInt(rs.getString("連接符號"));
				String 部件序 = rs.getString("部件序");
				int[] 部件序控制碼 = 字串與控制碼轉換.轉換成控制碼(部件序);
				String 構字符號 = null;
				switch (連接符號)
				{
				case 1:
					構字符號 = "⿰";
					break;
				case 2:
					構字符號 = "⿱";
					break;
				case 3:
					構字符號 = "⿴";
					break;
				default:
					break;
				}
				try
				{
					switch (連接符號)
					{
					case 1:
					case 2:
					case 3:
					case 5:
						String insertQuery = "INSERT INTO \"漢字組建\".\"檢字表\" "
								+ "(\"Unicode\",\"Big5\",\"構形資料庫編號\",\"構字式\") "
								+ " VALUES ('" + rs.getString("Unicode")
								+ "','" + rs.getString("Big5") + "','"
								+ rs.getString("編號") + "','"
								+ 轉移工具.產生構字式(部件序控制碼, 構字符號) + "')";
						更新連線.executeUpdate(insertQuery);
						上傳筆數++;
						break;
					default:
						break;

					}
				}
				catch (IOException e)
				{
					System.err.println("發現錯誤！！！ " + rs.getString("編號") + " "
							+ 部件序);
					e.printStackTrace();
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("結束嘍～～ 時間：" + System.currentTimeMillis());
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
		// System.err.println(部件數);
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
		int 方便符號編號 = 控制碼 - 方便符號控制碼[0];
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
			throw new IOException("");
		}
		return 構字式;
	}
}
