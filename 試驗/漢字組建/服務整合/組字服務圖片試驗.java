package 漢字組建.服務整合;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class 組字服務圖片試驗
{
	static final String 圖片存放路徑 = "組字圖片";

	@Test
	public void 宋體png() throws IOException
	{
		比較網路結果("宋體", "意", "意", "png");
	}
	private void 比較網路結果(String 字體, String 組字式, String 檔案名, String 副檔名)
			throws IOException
	{
		String 新網路路徑 = String.format("http://%s:%s/%s.%s?字體=%s", 主機, 連接埠, 組字式,
				副檔名, 字體);
		String 檔案路徑 = String.format("%s/%s.%s", 圖片存放路徑, 檔案名, 副檔名);
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
