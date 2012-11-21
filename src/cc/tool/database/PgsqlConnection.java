package cc.tool.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PgsqlConnection
{
	static final String url = "jdbc:postgresql://localhost/IhcData?useUnicode=true&characterEncoding=utf-8";
	private Connection con;
	private Statement stmt;

	PgsqlConnection()
	{
		this(url, "Ihc", "983781");
	}

	PgsqlConnection(String urls, String account, String passwd)
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
			con = DriverManager.getConnection(urls, account, passwd);
		}
		catch (SQLException ex)
		{
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		return;
	}

	void close()
	{
		try
		{
			con.close();
		}
		catch (SQLException ex)
		{
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		return;
	}

	ResultSet executeQuery(String query) throws SQLException
	{
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		// stmt.close();
		return rs;
	}

	void executeUpdate(String query) throws SQLException
	{
		stmt = con.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
		return;
	}
}
