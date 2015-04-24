package 漢字組建.服務整合;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class 網路服務試驗
{
	static final String 圖片存放路徑 = "組字圖片";;
	static final String 主機 = "localhost";
	static final String 連接埠 = "8080";

	@Test
	public void 國() throws IOException
	{
		比較網路結果("國");
	}

	private void 比較網路結果(String 組字式) throws IOException
	{
		URL url;
		HttpURLConnection conn;
		String 網路路徑 = "http://" + 主機 + ":" + 連接埠 + "/宋體/" + 組字式+".png";
		String 檔案路徑 = 圖片存放路徑 + "/" + 組字式+".png";
		url = new URL(網路路徑);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream 網路圖片 = conn.getInputStream();
		InputStream 檔案圖片 = new FileInputStream(檔案路徑);
		while (true)
		{
			int 網路資料 = 網路圖片.read();
			int 檔案資料 = 檔案圖片.read();
			assertEquals(網路資料, 檔案資料);
			if (網路資料 == -1)
				break;
		}
		檔案圖片.close();
		return;
	}
}
