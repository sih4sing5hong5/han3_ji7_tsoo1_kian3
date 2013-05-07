package cc.連線服務;

import java.awt.Font;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	組字介面 組字工具;

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

		組字工具 = new 組字介面(查詢方式, 正規化工具, 設定工具, 調整工具, 字型屬性, 字型大細);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getParameter("a") != null)
		{
			BufferedImage 字型圖片 = new BufferedImage(200, 200,
					BufferedImage.TYPE_INT_ARGB);
			組字工具.組字(字型圖片.getGraphics(), request.getParameter("a"));
			ImageIO.write(字型圖片, "png", response.getOutputStream());
		}
		else
		{
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			// // response.getOutputStream().write("<h1>Hello 我愛文莉</h1>");
			// //
			// response.getOutputStream().write(
			// "<h1>Hello 我愛文莉</h1>".getBytes("utf-8"));
			response.getWriter().println("<h1>Hello 我愛文莉</h1>");
			// response.getWriter().println(request.getParameterMap());
			// response.getWriter().println(request.getParameter("a"));
			// System.out.println("SSSSSSSSSSss");
			//
			// (System.out).write(request.getParameter("a").getBytes());
			// System.out.println("aaaaaa");
			// //
			// response.getOutputStream().write(request.getParameter("a").getBytes());
		}
	}
}