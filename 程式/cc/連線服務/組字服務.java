/*******************************************************************************
 * 著作權所有 (C) 民國102年 意傳文化科技
 * 開發者：薛丞宏
 * 網址：http://意傳.台灣
 * 字型提供：
 * 	全字庫授權說明
 * 		© 2012 中華民國行政院研究發展考核委員會。本字型檔採用創用CC「姓名標示－禁止改作」3.0臺灣版授權條款釋出。您可以在不變更字型內容之條件下，重製、散布及傳輸本字型檔之著作內容。惟應保留本字型名稱及著作權聲明。
 * 		http://www.cns11643.gov.tw/AIDB/copyright.do
 * 		
 * 	「中央研究院漢字部件檢字系統」2.65版釋出聲明
 * 		……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
 * 		http://cdp.sinica.edu.tw/cdphanzi/declare.htm
 * 		
 * 	吳守禮台語注音字型：
 * 		©2012從宜工作室：吳守禮、吳昭新，以CC01.0通用(CC01.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
 * 		http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
 * 		
 * 本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主：
 * 	１．得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
 * 	２．任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼；
 * 	３．將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼
 * 
 * 此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。
 * 
 * 漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。
 * 
 * 謝謝您的使用與推廣～～
 ******************************************************************************/
package cc.連線服務;

import java.awt.Font;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import 漢字組建.部件結構調整工具.組字式結構正規化工具;
import cc.tool.database.PgsqlConnection;
import cc.排版工具.MergePieceAdjuster;
import cc.揀字工具.ChineseCharacterTypeSetter;
import cc.揀字工具.字型參考設定工具;
import cc.揀字工具.對照字體;
import cc.揀字工具.展開式查通用字型編號;
import cc.揀字工具.用資料庫查展開式的通用字型編號;
import cc.筆觸.FunctinoalBasicBolder;
import cc.筆觸工具.分離活字加粗;
import cc.部件結構調整工具.展開式查詢工具;
import cc.部件結構調整工具.資料庫連線展開式查詢;

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

	/** 產生圖形傳予組字介面畫。毋過無X11、圖形介面就袂使用 */
	// GraphicsConfiguration 系統圖畫設定;
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/** 組字出來的字型解析度 */
	int 字型大細;

	/** 建立一个組字的服務。 */
	public 組字服務()
	{
		// 系統圖畫設定 = GraphicsEnvironment.getLocalGraphicsEnvironment()
		// .getDefaultScreenDevice().getDefaultConfiguration();

		連線 = new PgsqlConnection();
		// TODO 換專門查的使用者，換讀取權限
		int 粗字型屬性 = Font.BOLD;
		int 普通字型屬性 = 0;
		字型大細 = 200;

		展開式查詢工具 查詢方式 = new 資料庫連線展開式查詢(連線);
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()
		組字式結構正規化工具 正規化工具 = new 組字式結構正規化工具();
		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
		// new FunctinoalBasicBolder(new Stroke[] {}, 01),
				1e-1, 5);
		展開式查通用字型編號 展開式查通用字型編號工具 = new 用資料庫查展開式的通用字型編號(連線);
		// TODO 用資料庫查展開式的通用字型編號(連線) 無愛查通用字型編號()
		分離活字加粗 活字加粗 = new 分離活字加粗(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		ChineseCharacterTypeSetter 宋體設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻宋體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		宋體組字工具 = new 組字介面(查詢方式, 正規化工具, 宋體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);
		ChineseCharacterTypeSetter 粗宋設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻宋體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		粗宋組字工具 = new 組字介面(查詢方式, 正規化工具, 粗宋設定工具, 調整工具, 活字加粗, 粗字型屬性, 字型大細);

		ChineseCharacterTypeSetter 楷體設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻楷體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		楷體組字工具 = new 組字介面(查詢方式, 正規化工具, 楷體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);

		ChineseCharacterTypeSetter 粗楷設定工具 = new 字型參考設定工具(展開式查通用字型編號工具, 對照字體
				.提著吳守禮注音摻楷體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		粗楷組字工具 = new 組字介面(查詢方式, 正規化工具, 粗楷設定工具, 調整工具, 活字加粗, 粗字型屬性, 字型大細);
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
					if (!附檔名.equals("svg"))// TODO 只支援png、svg，其他先用png
						附檔名 = "png";
					if (附檔名.equals("png"))
					{
						// System.err.println(附檔名);
						BufferedImage 字型圖片 =
						// 系統圖畫設定.createCompatibleImage(字型大細,
						// 字型大細, Transparency.TRANSLUCENT);
						new BufferedImage(字型大細, 字型大細,
								BufferedImage.TYPE_INT_ARGB);
						組字工具.組字(檔名, 字型圖片.getGraphics());
						ImageIO.write(字型圖片, 附檔名, response.getOutputStream());
						遏袂做 = false;
					}
					else
					// svg
					{
						DOMImplementation domImpl = GenericDOMImplementation
								.getDOMImplementation();

						// Create an instance of org.w3c.dom.Document.
						String svgNS = "http://www.w3.org/2000/svg";
						Document document = domImpl.createDocument(svgNS,
								"svg", null);

						boolean useCSS = true; // we want to use CSS style
												// attributes
						// Create an instance of the SVG Generator.
						SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
						組字工具.組字(檔名, svgGenerator);
						OutputStreamWriter svgOutput = new java.io.OutputStreamWriter(
								response.getOutputStream(), "UTF-8");
						svgGenerator.stream(svgOutput, useCSS);

						遏袂做 = false;
					}
				}
			}
		}
		// if (request.getParameter("a") != null)
		// {
		// }
		if (遏袂做)
		{
			/* 　導向去別位 response.sendRedirect(網址字串); */
			response.sendRedirect("http://xn--v0qr21b.xn--kpry57d");

			// response.setContentType("text/html");
			// response.setStatus(HttpServletResponse.SC_OK);
			// // // response.getOutputStream().write("<h1>Hello 我愛文莉</h1>");
			// // response.getOutputStream().write(
			// // "<h1>Hello 我愛文莉</h1>".getBytes("utf-8"));
			// response.getWriter().println("<h1>Hello 我愛文莉</h1>");
			// response.getWriter().println(目錄.length);
			// // response.getWriter().println(目錄[1]);
			// for (String a : ImageIO.getReaderFileSuffixes())
			// {
			// response.getWriter().println(a);
			// }
			// // String result = URLDecoder.decode(request.getRequestURI(),
			// // "UTF-8");
			// // response.getWriter().println(result);
		}
	}
}
