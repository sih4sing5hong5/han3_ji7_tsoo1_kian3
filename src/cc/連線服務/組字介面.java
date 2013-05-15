package cc.連線服務;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.slf4j.Logger;
import org.slf4j.profiler.Profiler;

import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterFormatException;
import cc.core.展開式查詢工具;
import cc.core.漢字序列分析工具;
import cc.core.組字式部件;
import cc.core.組字式部件正規化;
import cc.core.組字式部件組字式建立工具;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.piece.PieceMovableType;
import cc.printing.awt.piece.AwtForSinglePiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 共組字規的系統整理起來，紲落來若欲用，就會當自由選擇欲用的工具。
 * 
 * @author Ihc
 */
public class 組字介面
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 看使用者提供的部件，是毋是愛先換做展開式抑是按怎 */
	protected 展開式查詢工具 查詢方式;
	/** 決定有需要正規化無佮按怎正規化的物件 */
	protected 組字式部件正規化 正規化工具;
	/** 設定前先建立逐的部件，伊佮下跤的部件所代表的組字式 */
	protected 組字式部件組字式建立工具 組字式建立工具;
	/** 依據部件佮字體的性質，共部件提來產生活字 */
	protected ChineseCharacterTypeSetter 設定工具;
	/** 佮頭拄仔產生的活字，組合閣共調整 */
	protected MergePieceAdjuster 調整工具;
	/** 主要是控制字體是毋是粗體 */
	protected final int 字型屬性;
	/** 字體愛偌大 */
	protected final int 字型大細;
	/** 限制組字式長度，予儂無法度惡意攻擊 */
	protected final int 組字式上大長度;

	/**
	 * 建立一个組字介面，並決定所有屬性。
	 * 
	 * @param 查詢方式
	 *            看使用者提供的部件，是毋是愛先換做展開式抑是按怎
	 * @param 正規化工具
	 *            決定有需要正規化無佮按怎正規化的物件
	 * @param 設定工具
	 *            依據部件佮字體的性質，共部件提來產生活字
	 * @param 調整工具
	 *            佮頭拄仔產生的活字，組合閣共調整
	 * @param 字型屬性
	 *            主要是控制字體是毋是粗體
	 * @param 字型大細
	 *            字體愛偌大
	 */
	public 組字介面(展開式查詢工具 查詢方式, 組字式部件正規化 正規化工具, ChineseCharacterTypeSetter 設定工具,
			MergePieceAdjuster 調整工具, int 字型屬性, int 字型大細)
	{
		this.查詢方式 = 查詢方式;
		this.正規化工具 = 正規化工具;
		this.設定工具 = 設定工具;
		this.調整工具 = 調整工具;
		this.字型屬性 = 字型屬性;
		this.字型大細 = 字型大細;

		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());
		組字式建立工具 = new 組字式部件組字式建立工具();
		組字式上大長度 = 20;
	}

	/**
	 * 組字而且畫出來。
	 * 
	 * @param 組字式
	 *            使用者要求的組字式
	 * @param 欲畫的所在
	 *            畫字體的所在
	 * @return 實際畫的組字式
	 */
	public String 組字(String 組字式, Graphics 欲畫的所在)
	{
		if (組字式.length() >= 組字式上大長度)
			組字式 = 組字式.substring(0, 組字式上大長度);
		Profiler 看時工具 = new Profiler("組字 " + 組字式);
		看時工具.setLogger(記錄工具);

		看時工具.start("初使化");
		// 記錄工具.debug(MarkerFactory.getMarker("@@"),
		// "初使化～～ 時間：" + System.currentTimeMillis());

		看時工具.start("分析中");
		// 記錄工具.debug("分析中～～ 時間：" + System.currentTimeMillis());

		漢字序列分析工具 序列分析工具 = new 漢字序列分析工具(組字式, 查詢方式);
		ChineseCharacter 部件;
		try
		{
			部件 = 序列分析工具.parseCharacter(null);
		}
		catch (ChineseCharacterFormatException e)
		{
			// TODO 看欲按怎處理，硬顯示，抑是傳連結毋著？
			e.printStackTrace();
			return "";
		}

		組字式部件 組字部件 = (組字式部件) 部件;
		// 組字部件.建立組字式(組字式建立工具);
		// 記錄工具.debug(組字部件.提到組字式());
		正規化工具.正規化(部件);
		組字部件.建立組字式(組字式建立工具);
		// 記錄工具.debug(組字部件.提到組字式());

		看時工具.start("設定中");
		// 記錄工具.debug("設定中～～ 時間：" + System.currentTimeMillis());

		漢字組建活字 活字 = 部件.typeset(設定工具, null);

		看時工具.start("調整中");
		// 記錄工具.debug("調整中～～ 時間：" + System.currentTimeMillis());

		活字.adjust(調整工具);

		看時工具.start("列印中");
		// 記錄工具.debug("列印中～～ 時間：" + System.currentTimeMillis());
		Graphics2D 字型圖版 = (Graphics2D) 欲畫的所在;
		字型圖版.setColor(Color.black);
		字型圖版.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		字型圖版.translate(0, 字型大細 * 0.83);// TODO 閣愛研究按怎調整
		字型圖版.setStroke(new NullStroke());

		AwtForSinglePiecePrinter 列印工具 = new AwtForSinglePiecePrinter(字型圖版);

		列印工具.printPiece(調整工具.format((PieceMovableType) 活字));

		看時工具.stop().log();
		return 組字部件.提到組字式();
	}
}
