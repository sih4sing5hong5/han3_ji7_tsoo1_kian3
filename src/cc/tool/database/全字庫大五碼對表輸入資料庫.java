package cc.tool.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class 全字庫大五碼對表輸入資料庫
{
	static String 基本平面 = "CNS2UNICODE_Unicode BMP字面.txt";
	static String 第二平面 = "CNS2UNICODE_Unicode第2字面.txt";
	static String 十五平面 = "CNS2UNICODE_Unicode第15字面.txt";
	static String[] 全部檔案 = new String[] { 基本平面, 第二平面, 十五平面 };

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int 上傳筆數 = 0;
		System.out.println("開始嘍～～ 時間：" + System.currentTimeMillis());
		PgsqlConnection 更新連線 = new PgsqlConnection();
		try
		{
			for (String 檔案 : 全部檔案)
			{
				File file = new File(
						"/home/Ihc/意泉計劃/機構資料/全字庫/授權薛丞宏先生/中文碼對照表/Uniocde/"
								+ 檔案);
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(file));
				String data = null;
				while ((data = bufferedReader.readLine()) != null)
				{
					// System.out.println(data);
					String[] array = data.split("	");
					String insertQuery = "INSERT INTO \"全字庫\".\"CNS2UNICODE\" "
							+ "(\"Cns11643\",\"Unicode\") " + " VALUES ('"
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
	}
}
