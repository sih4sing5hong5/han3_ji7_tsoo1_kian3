package idsgen.services;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import idsgen.services.IDSgenService;

public class IDSImgTest
{
	static final String 圖片存放路徑 = "組字圖片";
	protected final IDSgenService 宋體組字服務 = IDSgenService.預設組字服務();

	@Test
	public void 意png() throws IOException
	{
		檢查png結果("意");
	}

	@Test
	public void 立日心png() throws IOException
	{
		檢查png結果("⿳立日心");
	}

	@Test
	public void 傳png() throws IOException
	{
		檢查png結果("⿰專");
	}

	@Test
	public void 傳svg() throws IOException
	{
		檢查svg結果("⿰專");
	}

	@Test
	public void 人因svg() throws IOException
	{
		檢查svg結果("⿰因");
	}

	@Test
	public void 國svg() throws IOException
	{
		檢查svg結果("⿴囗或");
	}

	@Test
	public void 招財進寶svg() throws IOException
	{
		檢查svg結果("⿺辶⿵宀⿱珤⿰隹⿰貝招");
	}

	private void 檢查png結果(String 組字式) throws IOException
	{
		ByteArrayOutputStream 輸出檔案 = new ByteArrayOutputStream();
		宋體組字服務.字組成png(組字式, 輸出檔案);
		String 檔案路徑 = String.format("%s/%s.%s", 圖片存放路徑, 組字式, "png");
		InputStream 檔案圖片 = new FileInputStream(檔案路徑);
		for (byte 字元 : 輸出檔案.toByteArray())
		{
			assertEquals((字元), (byte) (檔案圖片.read()));
		}
		assertEquals(檔案圖片.read(), -1);
		檔案圖片.close();
		return;
	}

	private void 檢查svg結果(String 組字式) throws IOException
	{
		ByteArrayOutputStream 輸出檔案 = new ByteArrayOutputStream();
		宋體組字服務.字組成svg(組字式, 輸出檔案);
		String 檔案路徑 = String.format("%s/%s.%s", 圖片存放路徑, 組字式, "svg");
		InputStream 檔案圖片 = new FileInputStream(檔案路徑);
		for (byte 字元 : 輸出檔案.toByteArray())
		{
			assertEquals((字元), (byte) (檔案圖片.read()));
		}
		assertEquals(檔案圖片.read(), -1);
		檔案圖片.close();
		return;
	}
}
