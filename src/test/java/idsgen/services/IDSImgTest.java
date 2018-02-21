package idsgen.services;

import static org.junit.Assert.assertEquals;
import idsrend.services.IDSrendService;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.Test;
import org.junit.Ignore;

public class IDSImgTest {
	protected final IDSrendService 宋體組字服務 = IDSrendService.預設組字服務();

	@Ignore @Test
	public void 意png() throws IOException {
		檢查png結果("意");
	}

	@Ignore @Test
	public void 立日心png() throws IOException {
		檢查png結果("⿳立日心");
	}

	@Ignore @Test
	public void 傳png() throws IOException {
		檢查png結果("⿰專");
	}

	@Ignore @Test
	public void 傳svg() throws IOException {
		檢查svg結果("⿰專");
	}

	@Ignore @Test
	public void 人因svg() throws IOException {
		檢查svg結果("⿰因");
	}

	@Ignore @Test
	public void 國svg() throws IOException {
		檢查svg結果("⿴囗或");
	}

	@Ignore @Test
	public void 招財進寶svg() throws IOException {
		檢查svg結果("⿺辶⿵宀⿱珤⿰隹⿰貝招");
	}

	private void 檢查png結果(String 組字式) throws IOException {
		ByteArrayOutputStream 輸出檔案 = new ByteArrayOutputStream();
		宋體組字服務.字組成png(組字式, 輸出檔案);
		String 檔案路徑 = String.format("/%s.%s", 組字式, "png");
		InputStream 檔案圖片 = this.getClass().getResourceAsStream(檔案路徑);
		for (byte 字元 : 輸出檔案.toByteArray()) {
			assertEquals((字元), (byte) (檔案圖片.read()));
		}
		assertEquals(檔案圖片.read(), -1);
		檔案圖片.close();
		return;
	}

	private void 檢查svg結果(String 組字式) throws IOException {
		ByteArrayOutputStream 輸出檔案 = new ByteArrayOutputStream();
		宋體組字服務.字組成svg(組字式, 輸出檔案);
		String 檔案路徑 = String.format("/%s.%s", 組字式, "svg");
		BufferedReader 檔案圖片 = new BufferedReader(new InputStreamReader(this
				.getClass().getResourceAsStream(檔案路徑)));
		BufferedReader 動態圖片 = new BufferedReader(new StringReader(
				輸出檔案.toString()));
		String 結果 = "";
		while (結果 != null) {
			結果 = 檔案圖片.readLine();
			assertEquals(結果, 動態圖片.readLine());
		}
		檔案圖片.close();
		動態圖片.close();
		return;
	}
}
