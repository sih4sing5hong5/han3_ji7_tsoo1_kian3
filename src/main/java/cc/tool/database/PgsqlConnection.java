package cc.tool.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Pgsql連線物件。
 * 
 * @author Ihc
 */
public class PgsqlConnection
{
	/** 預設資料庫位置 */
	static public final String url = "jdbc:postgresql://localhost/漢字組建?useUnicode=true&characterEncoding=utf-8";
	/** 連線物件 */
	private Connection connection;
	/** 連線狀態 */
	private Statement statement;

	/**
	 * 自動連線到資料庫，資料庫設定唯讀，這个帳號就無修改的權限。
	 * 
	 * <pre>
	 * ALTER DATABASE "漢字組建" set default_transaction_read_only=on;
	 * </pre>
	 */
	public PgsqlConnection()
	{
		this(url, "漢字組建", "ChineseCharacter");
	}

	/**
	 * 連線到資料庫。
	 * 
	 * @param urls
	 *            資料庫位置
	 * @param account
	 *            資料庫帳號
	 * @param passwd
	 *            資料庫密碼
	 */
	public PgsqlConnection(String urls, String account, String passwd)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (java.lang.ClassNotFoundException e)
		{
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		try
		{
			connection = DriverManager.getConnection(urls, account, passwd);
		}
		catch (SQLException ex)
		{
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		return;
	}

	/** 關閉連線。 */
	public void close()
	{
		try
		{
			connection.close();
		}
		catch (SQLException ex)
		{
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		return;
	}

	/**
	 * 查詢資料。
	 * 
	 * @param query
	 *            向資料庫提出的要求
	 * @return 查詢結果
	 * @throws SQLException
	 *             資料庫錯誤
	 */
	public ResultSet executeQuery(String query) throws SQLException
	{
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		// stmt.close();
		return rs;
	}

	/**
	 * 更改資料庫。
	 * 
	 * @param query
	 *            向資料庫提出的更改
	 * @throws SQLException
	 *             資料庫錯誤
	 */
	public void executeUpdate(String query) throws SQLException
	{
		statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
		return;
	}
}
