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

import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
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
public class AwtTestExample extends Awt測試樣板
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
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
//		word = "國⿴囗或";
		System.out.println("初使化～～ 時間：" + System.currentTimeMillis());
		Graphics2D graphics2D = (Graphics2D) g1;
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(WIDTH - TYPE_SIZE * 1.1, TYPE_SIZE);
		graphics2D.setStroke(new NullStroke());

		System.out.println("分析中～～ 時間：" + System.currentTimeMillis());
		ChineseCharacterUtility ccUtility = new 漢字序列分析工具(word, new 資料庫連線展開式查詢());
		// TODO 資料庫連線展開式查詢() 展開式免查詢()
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();

		組字式部件正規化 正規化工具 = new 組字式部件正規化();
		組字式部件組字式建立工具 組字式建立工具 = new 組字式部件組字式建立工具();
		for (ChineseCharacter 部件 : ccArray)
		{
			// System.out.println("XD");
			組字式部件 組字部件 = (組字式部件) 部件;
			組字部件.建立組字式(組字式建立工具);
//			System.out.println(組字部件.提到組字式());
			正規化工具.正規化(部件);
			// System.out.println("XD1");
			組字部件.建立組字式(組字式建立工具);
//			System.out.println(組字部件.提到組字式());
		}

		System.out.println("設定中～～ 時間：" + System.currentTimeMillis());
		// ChineseCharacterTypeSetter setter = new MergePieceSetter(
		// 測試字體,
		// 測試屬性,
		// TYPE_SIZE,
		// new FontRenderContext(new AffineTransform(),
		// java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
		// java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));

		ChineseCharacterTypeSetter setter = new 字型參考設定工具(
				new 用資料庫查展開式的通用字型編號(),
				整合字體.提著宋體字體().調整字體參數(測試屬性, TYPE_SIZE),
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		Vector<漢字組建活字> ccmvArray = new Vector<漢字組建活字>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}

		System.out.println("調整中～～ 時間：" + System.currentTimeMillis());
		MergePieceAdjuster adjuster = new MergePieceAdjuster(
				new FunctinoalBasicBolder(new Stroke[] {}, 01), 1e-1);// TODO
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.elementAt(i).adjust(adjuster);
		}

		System.out.println("列印中～～ 時間：" + System.currentTimeMillis());
		AwtForSinglePiecePrinter printer = new AwtForSinglePiecePrinter(
				graphics2D);
		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			// ccmvArray.elementAt(i).print(printer); //TODO
			// 以後printer沒用處或專門負責排版？
			printer.printPiece(adjuster.format((PieceMovableType) ccmvArray
					.elementAt(i)));
			graphics2D.translate(0, TYPE_SIZE);// move to the down
			if (i % LINE_SIZE == LINE_SIZE - 1)
				graphics2D.translate(-TYPE_SIZE * 1.2, -TYPE_SIZE * LINE_SIZE);// the
																				// new
			// line
		}
		System.out.println("結束了～～ 時間：" + System.currentTimeMillis());
		System.out.println();
//		System.out.println(整合字體.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
//				.有這个字型無(25110, 0));
//		System.out.println("---------");
//		通用字型號碼 字型號碼=new 通用字型號碼(25110, 0);
//		System.out.println(整合字體.提著楷體字體().調整字體參數(測試屬性, TYPE_SIZE)
//				.有這个字型無(字型號碼));
		return;
	}
}
