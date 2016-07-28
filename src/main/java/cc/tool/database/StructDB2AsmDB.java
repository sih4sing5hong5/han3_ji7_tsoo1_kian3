package cc.tool.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 把構形資料庫轉譯到漢字組建資料庫的型態。
 * 
 * @author Ihc
 */
public class StructDB2AsmDB
{
	/** 中央研究院構字式所用的所有方便符號 */
	static final int[] 方便符號控制碼;
	static
	{
		方便符號控制碼 = String2ControlCode.轉換成控制碼("");
	}

	/**
	 * 主函式。
	 * 
	 * @param args
	 *            程式參數
	 */
	public static void main(String[] args)
	{
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 選取連線 = new PgsqlConnection();
		PgsqlConnection 更新連線 = new PgsqlConnection();
		StructDB2AsmDB 轉移工具 = new StructDB2AsmDB();
		int 上傳筆數 = 0;
		try
		{
			// 更新連線.executeUpdate("DELETE FROM \"漢字組建\".\"檢字表\"");
			String selectQuery = "SELECT \"編號\",\"字體\",\"字碼\",\"Unicode\",\"連接符號\",\"部件序\""
					+ " FROM \"構形資料庫\".\"檢字表\" "
					// + " WHERE \"連接符號\" = '5' "
					+ " ORDER BY \"編號\" ASC"
			// + " LIMIT 100"
			// 註解好用
			;
			ResultSet rs = 選取連線.executeQuery(selectQuery);
			while (rs.next())
			{
				int 連接符號 = Integer.parseInt(rs.getString("連接符號"));
				String 部件序 = rs.getString("部件序");
				int[] 部件序控制碼 = String2ControlCode.轉換成控制碼(部件序);
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
						String 字型號碼 = Integer.toHexString(String2ControlCode.轉換成控制碼(rs
								.getString("字碼"))[0]);
						String 統一碼 = rs.getString("Unicode");
						if (統一碼 != null)
							統一碼 = 統一碼.toLowerCase();
						DBCommandString 新增命令 = new DBCommandString(
								"INSERT INTO \"漢字組建\".\"檢字表\" "
										+ "(\"統一碼\","
										+ "\"構形資料庫編號\",\"構型資料庫字體號碼\",\"構型資料庫字型號碼\","
										+ "\"組字式\") " + " VALUES (");
						新增命令.加變數(統一碼);
						新增命令.加命令(',');
						新增命令.加變數(rs.getString("編號"));
						新增命令.加命令(',');
						新增命令.加變數(rs.getString("字體"));
						新增命令.加命令(',');
						新增命令.加變數(字型號碼);
						新增命令.加命令(',');
						新增命令.加變數(轉移工具.產生構字式(部件序控制碼, 構字符號));
						新增命令.加命令(")");
						// System.out.println(新增命令.toString());
						更新連線.executeUpdate(新增命令.toString());
						上傳筆數++;
						break;
					default:
						break;

					}
				}
				catch (IDSPatternIncorrectException e)
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

	/**
	 * 將構字式的部件序，轉換成漢字組建的組字式。
	 * 
	 * @param 部件序控制碼
	 *            部件序的控制碼陣列
	 * @param 構字符號
	 *            此構字式的構字符號，若無傳入<code>null</code>
	 * @return 所求組字式
	 * @throws IDSPatternIncorrectException
	 *             構字式裡應只有一個部件，卻用到構字符號
	 */
	private String 產生構字式(int[] 部件序控制碼, String 構字符號) throws IDSPatternIncorrectException
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
			{
				if (構字符號 == null)
					throw new IDSPatternIncorrectException("明明是方便符號，部件序裡卻有兩個部件！！");
				else
					構字式.append(構字符號);
			}
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

	/**
	 * 判斷一控制碼是否為構形資料庫中的方便符號
	 * 
	 * @param 控制碼
	 *            欲判斷的字
	 * @return 是否為構形資料庫中的方便符號
	 */
	public boolean 是否為方便符號(int 控制碼)
	{
		for (int i = 0; i < 方便符號控制碼.length; ++i)
		{
			if (方便符號控制碼[i] == 控制碼)
				return true;
		}
		return false;
	}

	/**
	 * 把使用方便符號的構字式，展開成組字式
	 * 
	 * @param 控制碼
	 *            方便符號的控制碼
	 * @param CharComponent
	 *            方便符號所複製的部件
	 * @return 相對應的組字式
	 * @throws IDSPatternIncorrectException
	 *             若傳入的不是方便符號
	 */
	public StringBuilder 展開方便符號(int 控制碼, char[] 部件) throws IDSPatternIncorrectException
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
			throw new IDSPatternIncorrectException("傳入的不是方便符號！！");
		}
		return 構字式;
	}
}
