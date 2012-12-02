package cc.tool.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class 全字庫對照表輸入資料庫
{
	private String 目錄 ;
	private String[] 全部檔案;
	private String 資料表;
	private String 欄位名;

	全字庫對照表輸入資料庫(String 目錄, String[] 全部檔案, String 資料表, String 欄位名)
	{
		this.目錄 = 目錄;
		this.全部檔案 = 全部檔案;
		this.資料表 = 資料表;
		this.欄位名 = 欄位名;
	}

	void 更新()
	{
		int 上傳筆數 = 0;
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 更新連線 = new PgsqlConnection();
		try
		{
			for (String 檔案 : 全部檔案)
			{
				File file = new File(目錄 + 檔案);
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));
				String data = null;
				while ((data = bufferedReader.readLine()) != null)
				{
					// System.out.println(data);
					String[] array = data.split("	");
					String insertQuery = "INSERT INTO \"全字庫\".\"" + 資料表 + "\" "
							+ "(\"Cns11643\",\"" + 欄位名 + "\") " + " VALUES ('"
							+ array[0] + array[1] + "','" + array[2] + "')";
					更新連線.executeUpdate(insertQuery);
					上傳筆數++;
				}
				bufferedReader.close();
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("結束嘍～～ 時間：" + System.currentTimeMillis());
		System.out.println("上傳筆數=" + 上傳筆數);
		return;
	}
}
