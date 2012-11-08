package cc.example.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.bolder.FunctinoalBasicBolder;
import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.piece.PieceMovableType;
import cc.printing.awt.piece.AwtForSinglePiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.MergePieceSetter;

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
public class AwtTestExample extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1440;
	/** 視窗高度 */
	static final int HEIGHT = 1050;
	/** 字型大小 */
	static final int TYPE_SIZE = 75;
	/** 每行字數 */
	static final int LINE_SIZE = 12;
	/** 測試漢字 */
	static private final String word = /* "    ⿰禾火秋⿰⿰火牙阝"; */"一百二十三尺"
			+ "秋漿國變務森" + "⿰禾火⿱將水⿴囗或" + "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木"
			+ "⿱立⿱日十⿱⿱立日十章輻⿰車⿱一⿱口田⿰⿱十⿱田十⿱一⿱口田" + "⿱口⿰口口⿱夕夕，"
			+ "⿴辶⿴宀⿱珤⿰隹⿰貝招⿴辶咼辶" + "⿴冖儿⿴冖几宀寶建⿴廴聿"
			+ "⿴厂猒⿴广夏⿴疒丙⿴尸古⿴戶方⿴户方⿴户 ⿱户　⿰户　" + "⿴气亥⿴气乃⿴气亞"
			+ "⿴冂⿱一口⿴冂⿱儿口⿴門一⿴門日⿴門月⿴鬥月⿴鬥市" + "⿱⿴乃又皿盈孕⿴乃子⿱乃子"
			+ "⿴凵乂⿴凵⿰幺⿰丨幺⿴凵⿰豕⿰丨豕" + "⿴勹日⿰⿴弓土畺⿴匚甲⿴⼖⿱口⿰口口" +
					"繞熱然煎焦烹煦無勳点狗狚";// */;
	/** 全字庫正宋體 */
	static final String 全字庫正宋體 = "全字庫正宋體";
	/** 全字庫正楷體 */
	static final String 全字庫正楷體 = "全字庫正楷體";
	/** 文泉驛正黑 */
	static final String 文泉驛正黑 = "文泉驛正黑";
	/** 文鼎中圓 */
	static final String 文鼎中圓 = "文鼎中圓";
	/** 超研澤中圓 */
	static final String 超研澤中圓 = "超研澤中圓";
	/** 測試用字體 */
	static private final String FontName = 全字庫正宋體;
	/** 測試用屬性 */
	static private final int FontStyle = Font.BOLD/* 0;// */;

	@Override
	public void paint(Graphics g1)
	{
		System.out.println("初使化～～ 時間：" + System.currentTimeMillis());
		Graphics2D graphics2D = (Graphics2D) g1;
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(WIDTH - TYPE_SIZE * 1.1, TYPE_SIZE);
		graphics2D.setStroke(new NullStroke());

		System.out.println("分析中～～ 時間：" + System.currentTimeMillis());
		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();

		System.out.println("設定中～～ 時間：" + System.currentTimeMillis());
		ChineseCharacterTypeSetter setter = new MergePieceSetter(
				FontName,
				FontStyle,
				TYPE_SIZE,
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}

		System.out.println("調整中～～ 時間：" + System.currentTimeMillis());
		MergePieceAdjuster adjuster = new MergePieceAdjuster(
				new FunctinoalBasicBolder(new Stroke[] {}, 0), 1e-1);// TODO
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
		return;
	}

	/**
	 * 主函式，設定相關視窗資訊。
	 * 
	 * @param args
	 *            呼叫引數
	 */
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.setContentPane(new AwtTestExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "漢字測試";
	}

	@Override
	public int getWidth()
	{
		return WIDTH;
	}

	@Override
	public int getHeight()
	{
		return HEIGHT;
	}
}
