package idsgen.onlineservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class NetServiceTest
{
	static final String 圖片存放路徑 = "組字圖片";;
	static final String 主機 = "localhost";
	static final String 連接埠 = "8080";

	@Test
	public void 宋體png() throws IOException
	{
		比較網路結果("宋體", "意", "意", "png");
	}

	@Test
	public void 宋體粗體png() throws IOException
	{
		比較網路結果("宋體粗體", "意", "意宋體粗體", "png");
	}

	@Test
	public void 楷體png() throws IOException
	{
		比較網路結果("楷體", "意", "意楷體", "png");
	}

	@Test
	public void 楷體粗體png() throws IOException
	{
		比較網路結果("楷體粗體", "意", "意楷體粗體", "png");
	}

	@Test
	public void 宋體svg() throws IOException
	{
		比較網路結果("宋體", "意", "意宋體", "svg");
	}

	@Test
	public void 宋體粗體svg() throws IOException
	{
		比較網路結果("宋體粗體", "意", "意宋體粗體", "svg");
	}

	@Test
	public void 楷體svg() throws IOException
	{
		比較網路結果("楷體", "意", "意楷體", "svg");
	}

	@Test
	public void 楷體粗體svg() throws IOException
	{
		比較網路結果("楷體粗體", "意", "意楷體粗體", "svg");
	}

	@Test
	public void 預設宋體png() throws IOException
	{
		比較網路結果("字體", "意", "意", "png");
	}

	@Test
	public void 宋體png轉址() throws IOException
	{
		舊網址轉址("宋體", "意", "意", "png");
	}

	@Test
	public void 宋體粗體png轉址() throws IOException
	{
		舊網址轉址("宋體粗體", "意", "意宋體粗體", "png");
	}

	@Test
	public void 楷體png轉址() throws IOException
	{
		舊網址轉址("楷體", "意", "意楷體", "png");
	}

	@Test
	public void 楷體粗體png轉址() throws IOException
	{
		舊網址轉址("楷體粗體", "意", "意楷體粗體", "png");
	}

	@Test
	public void 宋體svg轉址() throws IOException
	{
		舊網址轉址("宋體", "意", "意宋體", "svg");
	}

	@Test
	public void 宋體粗體svg轉址() throws IOException
	{
		舊網址轉址("宋體粗體", "意", "意宋體粗體", "svg");
	}

	@Test
	public void 楷體svg轉址() throws IOException
	{
		舊網址轉址("楷體", "意", "意楷體", "svg");
	}

	@Test
	public void 楷體粗體svg轉址() throws IOException
	{
		舊網址轉址("楷體粗體", "意", "意楷體粗體", "svg");
	}

	private void 舊網址轉址(String 字體, String 組字式, String 檔案名, String 副檔名)
			throws IOException
	{
		String 原本網路路徑 = String.format("http://%s:%s/%s/%s.%s", 主機, 連接埠, 字體,
				組字式, 副檔名);
		HttpURLConnection 原本網路連線 = (HttpURLConnection) new URL(原本網路路徑)
				.openConnection();
		原本網路連線.setInstanceFollowRedirects(false);
		原本網路連線.setRequestMethod("GET");
		assertTrue(原本網路連線.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM);
	}

	private void 比較網路結果(String 字體, String 組字式, String 檔案名, String 副檔名)
			throws IOException
	{
		String 新網路路徑 = String.format("http://%s:%s/%s.%s?字體=%s", 主機, 連接埠, 組字式,
				副檔名, 字體);
		String 檔案路徑 = String.format("%s/%s.%s", 圖片存放路徑, 檔案名, 副檔名);
		HttpURLConnection conn = (HttpURLConnection) new URL(新網路路徑)
				.openConnection();
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
