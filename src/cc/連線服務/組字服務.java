package cc.連線服務;

import java.awt.Font;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.展開式查詢工具;
import cc.core.組字式部件正規化;
import cc.core.資料庫連線展開式查詢;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.字型參考設定工具;
import cc.setting.piece.整合字體;
import cc.setting.piece.用資料庫查展開式的通用字型編號;
import cc.tool.database.PgsqlConnection;

public class 組字服務 extends HttpServlet
{
	組字介面 宋體組字工具;

	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	public 組字服務() throws IOException
	{
		連線 = new PgsqlConnection(PgsqlConnection.url, "Ihc", "983781");// TODO
		// 換專門查的使用者，換讀取權限
		int 字型屬性 = Font.BOLD;
		int 字型大細 = 200;

		展開式查詢工具 查詢方式 = new 資料庫連線展開式查詢(連線);
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()

		組字式部件正規化 正規化工具 = new 組字式部件正規化();

		ChineseCharacterTypeSetter 設定工具 = new 字型參考設定工具(
				new 用資料庫查展開式的通用字型編號(連線),
				整合字體.提著宋體字體().調整字體參數(字型屬性, 字型大細),
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
				new FunctinoalBasicBolder(new Stroke[] {}, 0), 1e-1);// TODO

		宋體組字工具 = new 組字介面(查詢方式, 正規化工具, 設定工具, 調整工具, 字型屬性, 字型大細);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String 網址字串 = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		String[] 目錄 = 網址字串.split("/");
		boolean 遏袂做 = true;
		if (目錄.length == 3 && 目錄[1].equals("宋體"))
		{
			String[] 檔案 = 目錄[2].split("\\.");
			if (檔案.length == 2)
			{
				String 檔名 = 檔案[0];
				String 附檔名 = 檔案[1];
				if (!附檔名.equals("jpg"))//TODO jpg有問題
					附檔名 = "png";
				System.err.println(附檔名);
				BufferedImage 字型圖片 = new BufferedImage(200, 200,
						BufferedImage.TYPE_INT_ARGB);
				宋體組字工具.組字(字型圖片.getGraphics(), 檔名);
				ImageIO.write(字型圖片, 附檔名, response.getOutputStream());
				遏袂做 = false;
			}
		}
		// if (request.getParameter("a") != null)
		// {
		// }
		if (遏袂做)
		{
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			// // response.getOutputStream().write("<h1>Hello 我愛文莉</h1>");
			// response.getOutputStream().write(
			// "<h1>Hello 我愛文莉</h1>".getBytes("utf-8"));
			response.getWriter().println("<h1>Hello 我愛文莉</h1>");
			response.getWriter().println(目錄.length);
			response.getWriter().println(目錄[1]);
			// String result = URLDecoder.decode(request.getRequestURI(),
			// "UTF-8");
			// response.getWriter().println(result);
		}
	}

	private String 八位元統一碼網址轉做字串(String 網址)
	{
		StringBuilder 新網址 = new StringBuilder();
		for (int i = 0; i < 網址.length(); ++i)
			if (網址.charAt(i) == '%')
			{
				if (i + 2 < 網址.length())
				{
					Integer.parseInt(網址.substring(i + 1, i + 3), 16);
				}
				else
					return 網址;
			}
			else
				網址.charAt(i);
		return 網址;
	}
}