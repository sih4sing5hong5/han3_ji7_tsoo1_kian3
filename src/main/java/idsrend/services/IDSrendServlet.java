package idsrend.services;

import java.awt.Font;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup_byDB;
import cc.char_indexingtool.ChineseCharacterTypeSetter;
import cc.char_indexingtool.FontRefSettingTool;
import cc.layouttools.MergePieceAdjuster;
import cc.char_indexingtool.FontCorrespondTable;
import cc.char_indexingtool.CommonFontNoSearch;
import cc.char_indexingtool.CommonFontNoSearchbyDB;
import cc.stroke.FunctinoalBasicBolder;
import cc.stroketool.MkeSeparateMovableType_Bolder;
import cc.tool.database.PgsqlConnection;
import idsrend.CharComponentStructureAdjuster.IDSnormalizer;

import java.lang.System;

/**
 * 佮愛規的服務佮提供的字體攏總整合起來。
 *
 * @author Ihc
 */
public class IDSrendServlet extends HttpServlet
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1224634082415129183L;
	/** 組宋體用的工具 */
	protected IDSrendService 宋體組字工具;
	/** 組宋體粗體用的工具 */
	protected IDSrendService 粗宋組字工具;
	/** 組楷體用的工具 */
	protected IDSrendService 楷體組字工具;
	/** 組楷體粗體用的工具 */
	protected IDSrendService 粗楷組字工具;

	/** 產生圖形傳予組字介面畫。毋過無X11、圖形介面就袂使用 */
	// GraphicsConfiguration 系統圖畫設定;
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;

	/** 組字出來的字型解析度 */
	int 字型大細;

	/** 建立一个組字的服務。 */
	public IDSrendServlet()
	{
		
		// 系統圖畫設定 = GraphicsEnvironment.getLocalGraphicsEnvironment()
		// .getDefaultScreenDevice().getDefaultConfiguration();

		連線 = new PgsqlConnection();
		// TODO 換專門查的使用者，換讀取權限
		
		ExpSequenceLookup 查詢方式 = new ExpSequenceLookup_byDB(連線);
		// TODO ExpSequenceLookup_byDB(連線) ExpSequenceNoLookup()
		IDSnormalizer 正規化工具 = new IDSnormalizer();
		MergePieceAdjuster 調整工具 = new MergePieceAdjuster(
		// new FunctinoalBasicBolder(new Stroke[] {}, 01),
				1e-1, 5);
		CommonFontNoSearch 展開式查通用字型編號工具 = new CommonFontNoSearchbyDB(連線);
		// TODO CommonFontNoSearchbyDB(連線) NonLookingupCommonFontNo()
		
		int 粗字型屬性 = Font.BOLD;
		int 普通字型屬性 = 0;
		字型大細 = 200;
		MkeSeparateMovableType_Bolder 活字加粗 = new MkeSeparateMovableType_Bolder(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		ChineseCharacterTypeSetter 宋體設定工具 = new FontRefSettingTool(展開式查通用字型編號工具, FontCorrespondTable
				.提著吳守禮注音摻宋體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		宋體組字工具 = new IDSrendService(查詢方式, 正規化工具, 宋體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);
		ChineseCharacterTypeSetter 粗宋設定工具 = new FontRefSettingTool(展開式查通用字型編號工具, FontCorrespondTable
				.提著吳守禮注音摻宋體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		粗宋組字工具 = new IDSrendService(查詢方式, 正規化工具, 粗宋設定工具, 調整工具, 活字加粗, 粗字型屬性, 字型大細);

		ChineseCharacterTypeSetter 楷體設定工具 = new FontRefSettingTool(展開式查通用字型編號工具, FontCorrespondTable
				.提著吳守禮注音摻楷體字體().調整字體參數(普通字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		楷體組字工具 = new IDSrendService(查詢方式, 正規化工具, 楷體設定工具, 調整工具, 活字加粗, 普通字型屬性, 字型大細);

		ChineseCharacterTypeSetter 粗楷設定工具 = new FontRefSettingTool(展開式查通用字型編號工具, FontCorrespondTable
				.提著吳守禮注音摻楷體字體().調整字體參數(粗字型屬性, 字型大細), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);

		粗楷組字工具 = new IDSrendService(查詢方式, 正規化工具, 粗楷設定工具, 調整工具, 活字加粗, 粗字型屬性, 字型大細);
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
		response.setHeader("Cache-Control", "public, max-age=31536000");
		response.setHeader("Server", "han3_ji7_tsoo1_kian3");
		
		String 網址字串 = URLDecoder.decode(request.getPathInfo(), "UTF-8");//在通用的應用程式用request.getRequestURI()會取到servlet path本身
		網址字串 = 網址字串.length() != 0 ? 網址字串.substring(1): "";
		
		if (是舊網址(網址字串))// TODO ==字體
		{
			String[] 目錄 = 網址字串.split("/", 2);
			String 新網址 = String.format("/%s?%s=%s",
					URLEncoder.encode(目錄[1], "UTF-8"),
					URLEncoder.encode("字體", "UTF-8"),
					URLEncoder.encode(目錄[0], "UTF-8"));
			/*
			System.out.println(網址字串);
			System.out.println(目錄[0]);
			System.out.println(目錄[1]);
			System.out.println(新網址);
			System.out.println("XXXDDDDD---------------------");
			*/
			
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			// response.setHeader("Location", response.encodeRedirectURL(新網址));
			response.setHeader("Location", 新網址);
			// response.sendRedirect(String.format("/%s?字體=%s", 目錄[2], 目錄[1]));
			return;
		}
		IDSrendService 組字工具 = 宋體組字工具;
		//System.out.println("網址字串="+網址字串);
		//System.out.println("輸入的uri="+request.getRequestURI());	
		//System.out.println("字體是"+request.getParameter("字體"));
		String 字體 = request.getParameter("字體");
		switch (字體 != null ? 字體 : "")
		{
		case "宋體":
			組字工具 = 宋體組字工具;
			break;
		case "宋體粗體":
			組字工具 = 粗宋組字工具;
			break;
		case "楷體":
			組字工具 = 楷體組字工具;
			break;
		case "楷體粗體":
			組字工具 = 粗楷組字工具;
			break;
		default:
			組字工具 = 宋體組字工具;
			break;
		}
		
		// TODO 對於空白網址字串可返回 index.html 之類的頁面
		int 位置 = 網址字串.lastIndexOf('.');
		if (位置 == -1)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Filename extension missing.");
			return;
		}
		
		String 組字式 = 網址字串.substring(0, 位置);
		System.out.println("組字式="+組字式);
		
		String 附檔名 = 網址字串.substring(位置 + 1);
		if (附檔名.equalsIgnoreCase("svg"))
		{
			response.setHeader("Content-Type", "image/svg+xml;charset=utf-8");
			組字工具.字組成svg(組字式, response.getOutputStream());
		}
		else // if (副檔名.equalsIgnoreCase("png")) // TODO 只支援png、svg，其他先用png
		{
			response.setHeader("Content-Type", "image/png");
			組字工具.字組成png(組字式, response.getOutputStream());
		}
	}

	private boolean 是舊網址(String 網址字串)
	{
		String[] 目錄 = 網址字串.split("/");
		if (目錄.length == 2)// TODO ==字體
		{
			switch (目錄[0])
			{
			case "宋體":
			case "宋體粗體":
			case "楷體":
			case "楷體粗體":
				return true;
			default:
				return false;
			}
		}
		return false;
	}
}
