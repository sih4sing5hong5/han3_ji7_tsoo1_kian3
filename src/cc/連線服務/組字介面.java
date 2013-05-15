package cc.連線服務;

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
import cc.core.ChineseCharacterFormatException;
import cc.core.ChineseCharacterUtility;
import cc.core.展開式查詢工具;
import cc.core.漢字序列分析工具;
import cc.core.組字式部件;
import cc.core.組字式部件正規化;
import cc.core.組字式部件組字式建立工具;
import cc.core.資料庫連線展開式查詢;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.piece.PieceMovableType;
import cc.printing.awt.piece.AwtForSinglePiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.字型參考設定工具;
import cc.setting.piece.整合字體;
import cc.setting.piece.用資料庫查展開式的通用字型編號;
import cc.tool.database.PgsqlConnection;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 主要測試的範例。
 * 
 * <pre>
 * 活字型態：<code>PieceMovableType</code>
 * 活字設定工具：<code>MergePieceSetter</code>
 * 活字調整工具：<code>MergePieceAdjuster</code>
 * 活字列印工具：<code>AwtForSinglePiecePrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class 組字介面
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 測試用字體 */
	// static final String 測試字體 = 全字庫正宋體;
	展開式查詢工具 查詢方式;

	組字式部件正規化 正規化工具;
	組字式部件組字式建立工具 組字式建立工具;
	ChineseCharacterTypeSetter 設定工具;
	MergePieceAdjuster 調整工具;
	/** 測試用屬性 */
	final int 字型屬性;
	final int 字型大細;

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
	}

	public String 組字(Graphics g1, String word)
	{
		if(word.length()>=100)
			word=word.substring(0, 100);
		Profiler 看時工具 = new Profiler("組字 " + word);
		看時工具.setLogger(記錄工具);

		看時工具.start("初使化");
		// 記錄工具.debug(MarkerFactory.getMarker("@@"),
		// "初使化～～ 時間：" + System.currentTimeMillis());

		Graphics2D graphics2D = (Graphics2D) g1;
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(0, 字型大細 * 0.83);
		graphics2D.setStroke(new NullStroke());

		看時工具.start("分析中");
		// 記錄工具.debug("分析中～～ 時間：" + System.currentTimeMillis());

		漢字序列分析工具 序列分析工具 = new 漢字序列分析工具(word, 查詢方式);
		ChineseCharacter 部件;
		try
		{
			部件 = 序列分析工具.parseCharacter(null);
		}
		catch (ChineseCharacterFormatException e)
		{
			// TODO Auto-generated catch block
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
		AwtForSinglePiecePrinter 列印工具 = new AwtForSinglePiecePrinter(graphics2D);

		列印工具.printPiece(調整工具.format((PieceMovableType) 活字));

		看時工具.stop().log();
		return 組字部件.提到組字式();
	}
}
