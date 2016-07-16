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
package cc.example.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.MarkerFactory;
import org.slf4j.profiler.Profiler;

import 漢字組建.組字式代換工具.IDSnormalizer;
import cc.movabletype.PieceMovableType;
import cc.movabletype.SeprateMovabletype;
import cc.char_indexingtool.FontRefSettingTool;
import cc.char_indexingtool.IntegratedFont;
import cc.layouttools.MergePieceAdjuster;
import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup_byDB;
import cc.char_indexingtool.CommonFontNoSearchbyDB;
import cc.log.IDSGenLogToolpack;
import cc.movabletype.ChineseCharCompositeMoveabletype;
import cc.printtools.AwtForSinglePiecePrinter;
import cc.stroke.FunctinoalBasicBolder;
import cc.stroke.NullStroke;
import cc.stroketool.MkeSeparateMovableType_Bolder;
import cc.tool.database.PgsqlConnection;
import idsrend.charcomponent.CharComponent;
import idsrend.parser.IDSParser;

/**
 * 主要測試的範例。
 * 
 * <pre>
 * 活字型態：<code>PieceMovableType</code>
 * 活字設定工具：<code>FontRefSettingTool</code>
 * 活字調整工具：<code>MergePieceAdjuster</code>
 * 活字列印工具：<code>AwtForSinglePiecePrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class AwtTestExample extends AwtTestTemplate
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 佮資料庫的連線 */
	protected PgsqlConnection 連線;
	/** 測試用字體 */
	static final String 測試字體 = 全字庫正宋體;
	/** 測試用屬性 */
	static final int 測試屬性 = Font.BOLD/* 0;// */;

	/**
	 * 主函式，設定相關視窗資訊。
	 * 
	 * @param args
	 *            呼叫引數
	 */
	public static void main(String[] args)
	{
		run(new AwtTestExample());
		return;
	}

	@Override
	public void paint(Graphics g1)
	{
		// 換讀取權限
		記錄工具 = new IDSGenLogToolpack().記錄工具(getClass());
		Profiler 看時工具 = new Profiler(getName());
		看時工具.setLogger(記錄工具);

		// word = "⿰因⿰⿴囗或";
		// word =
		// "⿳⿳⿳˙ㄆㄨˊ⿳⿳ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚˋ⿳⿳⿳ㄅㄧㄚ˪⿳⿳⿳ㄅㄧㄚㆷ⿳⿳⿳ㄅㄧㄚˊ⿳⿳⿳ㄅㄧㄚ˫⿳⿳⿳⿳ㄅㄧㄚ㆐ㆷ⿳⿳⿳⿳ㄅㄧㄚㆷ㆐⿳⿳⿳˙ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚ^"
		// + "⿳⿳⿳˙ㄆㄨˊ⿳ㆠㄧ⿳⿳ㆠㄧˋ⿳⿳ㆠㄧ˪⿳⿳ㆠㄧㆷ⿳⿳ㆠㄧˊ⿳⿳ㆠㄧ˫⿳⿳⿳ㆠㄧ㆐ㆷ⿳⿳⿳ㆠㄧㆷ㆐⿳⿳˙ㆠㄧ⿳⿳ㆠㄧ^"
		// + "⿳⿳⿳˙ㄆㄨˊ⿳⿳⿳˙ㄆㄨˊㆬ⿳ㆬ ⿳ㆬˋ⿳ㆬ˪⿳ㆬㆷ⿳ㆬˊ⿳ㆬ˫⿳⿳ㆬ㆐ㆷ⿳⿳ㆬㆷ㆐⿳˙ㆬ⿳ㆬ^"
		// + "⿰⿰⿱⿱⿱我薛丞宏愛⿱文莉⿰真的⿰真的⿰⿻真甲的⿰⿻真乙的"
		// + "ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙ "
		// + "ㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦ ㄧㄨㄩ ㄪㄫㄬ ㄭㄮ "
		// + "ㆠㆡㆢㆣ ㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳ ㆴㆵㆶㆷ ㄅㄉㄍㄎㄏ ˊˇˋ˙˪˫㆐";
		// word = "意意意意意意意意意⿱攵力⿱⿰⿰糹言糹攵⿰糹言⿰言糹⿰⿰糹言糹言糹";
		// word = "⿰丨丨丨⿱⿰⿰糹言糹攵⿰⿰糹言糹攵";
		連線 = new PgsqlConnection();
		看時工具.start("初使化");
		記錄工具.debug(MarkerFactory.getMarker("@@"),
				"初使化～～ 時間：" + System.currentTimeMillis());

		Graphics2D graphics2D = (Graphics2D) g1;
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(WIDTH - TYPE_SIZE * 1.1, TYPE_SIZE);
		graphics2D.setStroke(new NullStroke());

		看時工具.start("分析中");
		記錄工具.debug("分析中～～ 時間：" + System.currentTimeMillis());
		ExpSequenceLookup 查詢方式 = new ExpSequenceLookup_byDB(連線);
		// TODO ExpSequenceLookup_byDB(連線) ExpSequenceNoLookup()
		IDSParser ccUtility = new IDSParser(word, 查詢方式);
		Vector<CharComponent> ccArray = ccUtility.解析();

		IDSnormalizer 正規化工具 = new IDSnormalizer();
		for (CharComponent CharComponent : ccArray)
		{
			CharComponent 組字部件 = (CharComponent) CharComponent;
			組字部件.樹狀結構組字式();
			// 記錄工具.debug(組字部件.提到組字式());
			正規化工具.正規化(CharComponent);
			組字部件.樹狀結構組字式();
			// 記錄工具.debug(組字部件.提到組字式());
		}

		看時工具.start("設定中");
		記錄工具.debug("設定中～～ 時間：" + System.currentTimeMillis());
		MkeSeparateMovableType_Bolder 活字加粗 = new MkeSeparateMovableType_Bolder(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		FontRefSettingTool setter = new FontRefSettingTool(new CommonFontNoSearchbyDB(連線), IntegratedFont.提著宋體字體()
				.調整字體參數(測試屬性, TYPE_SIZE), new FontRenderContext(
				new AffineTransform(),
				java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT), 活字加粗);
		// setter = new MergePieceSetter(
		// 全字庫正宋體,
		// 測試屬性,
		// TYPE_SIZE,
		// new FontRenderContext(new AffineTransform(),
		// java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
		// java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		Vector<ChineseCharCompositeMoveabletype> ccmvArray = new Vector<ChineseCharCompositeMoveabletype>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}

		看時工具.start("調整中");
		記錄工具.debug("調整中～～ 時間：" + System.currentTimeMillis());
		MergePieceAdjuster adjuster = new MergePieceAdjuster(
				活字加粗.getPrecision(), setter.平均闊度());
		// TODO new FunctinoalBasicBolder(new Stroke[] {}, 0),
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.elementAt(i).adjust(adjuster);
		}

		看時工具.start("四角中");
		記錄工具.debug("四角中～～ 時間：" + System.currentTimeMillis());
		Vector<SeprateMovabletype> 活字陣列 = new Vector<SeprateMovabletype>();

		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			活字陣列.add(adjuster.format((PieceMovableType) ccmvArray.elementAt(i)));
		}
		看時工具.start("加粗中");
		記錄工具.debug("加粗中～～ 時間：" + System.currentTimeMillis());
		for (SeprateMovabletype 活字 : 活字陣列)
		{
			活字加粗.加粗(活字);
		}
		看時工具.start("列印中");
		記錄工具.debug("列印中～～ 時間：" + System.currentTimeMillis());
		AwtForSinglePiecePrinter printer = new AwtForSinglePiecePrinter(
				graphics2D);
		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			// ccmvArray.elementAt(i).print(printer); //TODO
			// 以後printer沒用處或專門負責排版？
			printer.printPiece(活字陣列.elementAt(i));
			// printer.printPiece(((PieceMovableType) ccmvArray
			// .elementAt(i)).getPiece());
			/** 徙較下跤一寡 */
			graphics2D.translate(0, TYPE_SIZE);
			/** 換一逝 */
			if (i % LINE_SIZE == LINE_SIZE - 1)
				graphics2D.translate(-TYPE_SIZE * 1.2, -TYPE_SIZE * LINE_SIZE);
		}
		記錄工具.debug("結束了～～ 時間：" + System.currentTimeMillis());
		記錄工具.debug(" ");
		看時工具.stop().log();
		// 記錄工具.debug(IntegratedFont.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
		// .有這个字型無(25110, 0));
		// 記錄工具.debug("---------");
		// CommonFontNo 字型號碼=new CommonFontNo(25110);
		// 記錄工具.debug(IntegratedFont.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
		// .有這个字型無(字型號碼));
		return;
	}
}
