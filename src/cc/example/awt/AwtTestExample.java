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

import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.core.展開式查詢工具;
import cc.core.漢字序列分析工具;
import cc.core.異寫式代換工具;
import cc.core.異寫式查詢工具;
import cc.core.組字式部件;
import cc.core.組字式部件正規化;
import cc.core.組字式部件組字式建立工具;
import cc.core.資料庫連線展開式查詢;
import cc.core.資料庫連線異寫式查詢;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.分離活字加粗;
import cc.printing.awt.piece.AwtForSinglePiecePrinter;
import cc.setting.piece.字型參考設定工具;
import cc.setting.piece.整合字體;
import cc.setting.piece.用資料庫查展開式的通用字型編號;
import cc.tool.database.PgsqlConnection;
import cc.tool.database.字串與控制碼轉換;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 主要測試的範例。
 * 
 * <pre>
 * 活字型態：<code>PieceMovableType</code>
 * 活字設定工具：<code>字型參考設定工具</code>
 * 活字調整工具：<code>MergePieceAdjuster</code>
 * 活字列印工具：<code>AwtForSinglePiecePrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class AwtTestExample extends Awt測試樣板
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
	/** 定義異寫編號數字 */
	int[] 編號陣列 = 字串與控制碼轉換.轉換成控制碼("甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戍亥陰陽乾坤震巽坎離艮兌");

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
		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());
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
		連線 = new PgsqlConnection(PgsqlConnection.url, "Ihc", "983781");// TODO
																		// 換專門查的使用者
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
		展開式查詢工具 查詢方式 = new 資料庫連線展開式查詢(連線);
		// TODO 資料庫連線展開式查詢(連線) 展開式免查詢()
		ChineseCharacterUtility ccUtility = new 漢字序列分析工具(word, 查詢方式);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();

		組字式部件正規化 正規化工具 = new 組字式部件正規化();
		組字式部件組字式建立工具 組字式建立工具 = new 組字式部件組字式建立工具();
		for (ChineseCharacter 部件 : ccArray)
		{
			組字式部件 組字部件 = (組字式部件) 部件;
			組字部件.建立組字式(組字式建立工具);
			// 記錄工具.debug(組字部件.提到組字式());
			正規化工具.正規化(部件);
			組字部件.建立組字式(組字式建立工具);
			// 記錄工具.debug(組字部件.提到組字式());
		}

		異寫式查詢工具 異寫式查詢 = new 資料庫連線異寫式查詢(連線);
		異寫式代換工具 異寫式代換 = new 異寫式代換工具(編號陣列, 異寫式查詢);
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccArray.set(i, 異寫式代換.代換(ccArray.elementAt(i)));
		}

		看時工具.start("設定中");
		記錄工具.debug("設定中～～ 時間：" + System.currentTimeMillis());
		分離活字加粗 活字加粗 = new 分離活字加粗(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);
		字型參考設定工具 setter = new 字型參考設定工具(new 用資料庫查展開式的通用字型編號(連線), 整合字體.提著宋體字體()
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
		Vector<漢字組建活字> ccmvArray = new Vector<漢字組建活字>();
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
		Vector<分離活字> 活字陣列 = new Vector<分離活字>();

		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			活字陣列.add(adjuster.format((PieceMovableType) ccmvArray.elementAt(i)));
		}
		看時工具.start("加粗中");
		記錄工具.debug("加粗中～～ 時間：" + System.currentTimeMillis());
		for (分離活字 活字 : 活字陣列)
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
		// 記錄工具.debug(整合字體.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
		// .有這个字型無(25110, 0));
		// 記錄工具.debug("---------");
		// 通用字型號碼 字型號碼=new 通用字型號碼(25110);
		// 記錄工具.debug(整合字體.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
		// .有這个字型無(字型號碼));
		return;
	}
}
