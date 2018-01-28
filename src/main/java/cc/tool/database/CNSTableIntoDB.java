package cc.tool.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 協助全字庫對照表資料輸入資料庫的模組。
 * 
 * @author Ihc
 */
public class CNSTableIntoDB
{
	/** 檔案目錄 */
	private String 目錄;
	/** 所有檔案名 */
	private String[] 全部檔案;
	/** 資料庫中的表名 */
	private String 資料表;
	/** 全字庫所對應到的編碼欄位名 */
	private String 欄位名;

	/**
	 * 初使化建構函式，設定各項參數
	 * 
	 * @param 目錄
	 *            檔案目錄
	 * @param 全部檔案
	 *            所有檔案名
	 * @param 資料表
	 *            資料庫中的表名
	 * @param 欄位名
	 *            全字庫所對應到的編碼欄位名
	 */
	CNSTableIntoDB(String 目錄, String[] 全部檔案, String 資料表, String 欄位名)
	{
		this.目錄 = 目錄;
		this.全部檔案 = 全部檔案;
		this.資料表 = 資料表;
		this.欄位名 = 欄位名;
	}

	/** 將資料上傳到資料庫 */
	void 上傳()
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
					if (欄位名.equals("Big5"))
					{
						// 檢測十六進位字元和跳格（都是 BMP 字元，姑且用個 char 吧）
						StringBuilder stringBuilder = new StringBuilder();
						for (int i = 0; i < data.length(); i++)
						{

							char ch = data.charAt(i);
							if (ch == '\t'  || ch >= '0' && ch <= '9'
									|| ch >= 'A' && ch <= 'F'
									|| ch >= 'a' && ch <= 'f')
							{
								stringBuilder.append(ch);
							}
						}	
						data = stringBuilder.toString();
					}
					if (!data.equals(""))
					{
						String[] array = data.split("	");
						if (array[0].length() > 1)
						{
							int 字面 = Integer.parseInt(array[0]);
							array[0] = Integer.toHexString(字面);
						}
						String insertQuery = "INSERT INTO \"全字庫\".\"" + 資料表
								+ "\" " + "(\"Cns11643\",\"" + 欄位名 + "\") "
								+ " VALUES ('" + array[0] + array[1] + "','"
								+ array[2] + "')";
						更新連線.executeUpdate(insertQuery);
						上傳筆數++;
					}
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
