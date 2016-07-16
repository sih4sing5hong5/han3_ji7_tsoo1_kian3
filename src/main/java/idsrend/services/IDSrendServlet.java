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

import 漢字組建.組字式代換工具.IDSnormalizer;
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
			//System.out.println("PathInfo="+request.getPathInfo());
				 String 網址字串 = URLDecoder.decode(request.getPathInfo(), "UTF-8")//在通用的應用程式用request.getRequestURI()會取到servlet path本身
				.substring(1);
		if (是舊網址(網址字串))// TODO ==字體
		{
			String[] 目錄 = 網址字串.split("/", 2);
			String 新網址 = String.format("/%s?%s=%s",
					URLEncoder.encode(目錄[1], "UTF-8"),
					URLEncoder.encode("字體", "UTF-8"),
					URLEncoder.encode(目錄[0], "UTF-8"));

			System.out.println(網址字串);
			System.out.println(目錄[0]);
			System.out.println(目錄[1]);
			System.out.println(新網址);
			System.out.println("XXXDDDDD---------------------");
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
		switch (request.getParameter("字體"))
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

		int 位置 = 網址字串.length();
		for (int i = 0; i < 網址字串.length(); ++i)
			if (網址字串.charAt(i) == '.')
				位置 = i;
		String 組字式 = 網址字串.substring(0, 位置);
		System.out.println("組字式="+組字式);
		String 附檔名 = 網址字串.substring(位置 + 1);
		if (!附檔名.equals("svg"))// TODO 只支援png、svg，其他先用png
			附檔名 = "png";
		if (附檔名.equals("png"))
		{
			組字工具.字組成png(組字式, response.getOutputStream());
		}
		else
		// svg
		{
			組字工具.字組成svg(組字式, response.getOutputStream());
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
