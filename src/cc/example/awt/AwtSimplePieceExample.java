package cc.example.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.adjusting.bolder.BasicBolder;
import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.SimplePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.漢字組建活字;
import cc.printing.ChineseCharacterTypePrinter;
import cc.printing.awt.piece.AwtForPiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.SimplePieceSetter;

/**
 * 主要測試的範例。
 * 
 * <pre>
 * 活字型態：<code>PieceMovableType</code>
 * 活字設定工具：<code>SimplePieceSetter</code>
 * 活字調整工具：<code>SimplePieceAdjuster</code>
 * 活字列印工具：<code>AwtForPiecePrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class AwtSimplePieceExample extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1420;
	/** 視窗高度 */
	static final int HEIGHT = 1050;
	/** 字型大小 */
	static final int TYPE_SIZE = 200;
	/** 每行字數 */
	static final int LINE_SIZE = 4;
	/** 測試漢字 */
	static private final String word = /* "    ⿰禾火秋⿰⿰火牙阝"; */"秋漿國一"
			+ "⿰禾火⿱將水⿴囗或⿴辶⿱宀⿱珤⿰隹⿰貝招" + "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木⿰車⿱一⿱口田" + "變務森輻"
			+ "攵力木五";// */;
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
	static private final int FontStyle = Font.BOLD;

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(WIDTH - TYPE_SIZE * 1.1, TYPE_SIZE);
		g.setStroke(new NullStroke());

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterTypeSetter setter = new SimplePieceSetter(
				FontName,
				FontStyle,
				TYPE_SIZE,
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		Vector<漢字組建活字> ccmvArray = new Vector<漢字組建活字>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}

		ChineseCharacterTypeAdjuster adjuster = new SimplePieceAdjuster(
				new BasicBolder(), 1e-1);
		for (int i = 0; i < ccArray.size(); ++i)
		{
			// ((PieceMovableType) ccmvArray.elementAt(i)).getPiece()
			// .setTerritoryDimension(TYPE_SIZE, TYPE_SIZE); // 直接由setter處理掉
			ccmvArray.elementAt(i).adjust(adjuster);
		}

		System.out.println(ccArray.size());
		ChineseCharacterTypePrinter printer = new AwtForPiecePrinter(g);
		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			ccmvArray.elementAt(i).print(printer);
			g.translate(0, TYPE_SIZE);// move to the down
			if (i % LINE_SIZE == LINE_SIZE - 1)
				g.translate(-TYPE_SIZE * 1.5, -TYPE_SIZE * LINE_SIZE);// the new
			// line
		}
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
		f.setContentPane(new AwtSimplePieceExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "物件活字範例";
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
