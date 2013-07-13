package cc.連線服務;

import java.awt.Font;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.展開式免查詢;
import cc.core.展開式查詢工具;
import cc.core.異寫式查詢工具;
import cc.core.組字式部件正規化;
import cc.core.資料庫連線異寫式查詢;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.字型參考設定工具;
import cc.setting.piece.對照字體;
import cc.setting.piece.展開式查通用字型編號;
import cc.setting.piece.無愛查通用字型編號;
import cc.tool.database.PgsqlConnection;
import cc.tool.database.字串與控制碼轉換;

/**
 * 佮愛規的服務佮提供的字體攏總整合起來。
 * 
 * @author Ihc
 */
public class 組字服務 extends HttpServlet
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1224634082415129183L;
	/** 組宋體用的工具 */
	protected 組字介面 宋體組字工具;
	/** 組宋體粗體用的工具 */
	protected 組字介面 粗宋組字工具;
	/** 組楷體用的工具 */
	protected 組字介面 楷體組字工具;
	/** 組楷體粗體用的工具 */
	protected 組字介面 粗楷組字工具;

	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/** 建立一个組字的服務。 */
	public 組字服務()
	{
		連線 = new PgsqlConnection(PgsqlConnection.url, "Ihc", "983781");// 專利遏袂好進前袂使用著檢字表
		// TODO 換專門查的使用者，換讀取權限
		int 粗字型屬性 = Font.BOLD;
		int 普通字型屬性 = 0;
		int 字型大細 = 200;
		/** 定義異寫編號數字 */
		int[] 編號陣列 = 字串與控制碼轉換.轉換成控制碼("甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戍亥陰陽乾坤震巽坎離艮兌");

		展開式查詢工具 查詢方式 = new 展開式免查詢();
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()
		組字式部件正規化 正規化工具 = new 組字式部件正規化();
		異寫式查詢工具 異寫式查詢 = new 資料庫連線異寫式查詢(連線);
		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		展開式查通用字型編號 展開式查通用字型編號工具 = new 無愛查通用字型編號();
		// TODO 用資料庫查展開式的通用字型編號(連線) 無愛查通用字型編號()

		ChineseCharacterTypeSetter 宋體設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體.提著吳守禮注音摻宋體字體()
				.調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		宋體組字工具 = new 組字介面(查詢方式, 正規化工具, 異寫式查詢, 編號陣列, 宋體設定工具, 調整工具, 普通字型屬性, 字型大細);
		ChineseCharacterTypeSetter 粗宋設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻宋體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		粗宋組字工具 = new 組字介面(查詢方式, 正規化工具, 異寫式查詢, 編號陣列, 粗宋設定工具, 調整工具, 粗字型屬性, 字型大細);

		ChineseCharacterTypeSetter 楷體設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻楷體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		楷體組字工具 = new 組字介面(查詢方式, 正規化工具, 異寫式查詢, 編號陣列, 楷體設定工具, 調整工具, 普通字型屬性, 字型大細);

		ChineseCharacterTypeSetter 粗楷設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻楷體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		粗楷組字工具 = new 組字介面(查詢方式, 正規化工具, 異寫式查詢, 編號陣列, 粗楷設定工具, 調整工具, 粗字型屬性, 字型大細);
	}

	@Override
	/**
	 * 會依照使用的目錄，決定用的字體佮檔案類型。
	 * 用法：<code>/字體/組字式.檔案類型</code>
	 * 這馬干焦提供：<code>宋體</code>、<code>宋體粗體</code>、<code>楷體</code>、<code>楷體粗體</code>
	 * 檔案類型：<code>png</code>
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String 網址字串 = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		String[] 目錄 = 網址字串.split("/");
		boolean 遏袂做 = true;
		if (目錄.length == 3)
		{
			組字介面 組字工具 = null;
			if (目錄[1].equals("宋體"))
			{
				組字工具 = 宋體組字工具;
			}
			else if (目錄[1].equals("宋體粗體"))
			{
				組字工具 = 粗宋組字工具;
			}
			else if (目錄[1].equals("楷體"))
			{
				組字工具 = 楷體組字工具;
			}
			else if (目錄[1].equals("楷體粗體"))
			{
				組字工具 = 粗楷組字工具;
			}
			if (組字工具 != null)
			{
				int 位置 = -1;
				for (int i = 0; i < 目錄[2].length(); ++i)
					if (目錄[2].charAt(i) == '.')
						位置 = i;
				if (位置 != -1)
				{
					String 檔名 = 目錄[2].substring(0, 位置);
					String 附檔名 = 目錄[2].substring(位置 + 1);
					// if (!附檔名.equals("jpg"))//TODO jpg有問題
					附檔名 = "png";
					// System.err.println(附檔名);
					BufferedImage 字型圖片 = new BufferedImage(200, 200,
							BufferedImage.TYPE_INT_ARGB);
					組字工具.組字(檔名, 字型圖片.getGraphics());
					ImageIO.write(字型圖片, 附檔名, response.getOutputStream());
					遏袂做 = false;
				}
			}
		}
		// if (request.getParameter("a") != null)
		// {
		// }
		if (遏袂做)
		{
			/*　導向去別位 response.sendRedirect(網址字串);*/
			response.sendRedirect("http://xn--v0qr21b.xn--kpry57d");
			
//			response.setContentType("text/html");
//			response.setStatus(HttpServletResponse.SC_OK);
//			// // response.getOutputStream().write("<h1>Hello 我愛文莉</h1>");
//			// response.getOutputStream().write(
//			// "<h1>Hello 我愛文莉</h1>".getBytes("utf-8"));
//			response.getWriter().println("<h1>Hello 我愛文莉</h1>");
//			response.getWriter().println(目錄.length);
//			// response.getWriter().println(目錄[1]);
//			for (String a : ImageIO.getReaderFileSuffixes())
//			{
//				response.getWriter().println(a);
//			}
//			// String result = URLDecoder.decode(request.getRequestURI(),
//			// "UTF-8");
//			// response.getWriter().println(result);
		}
	}
}