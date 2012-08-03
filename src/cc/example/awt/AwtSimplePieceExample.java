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
import cc.adjusting.piece.SimplePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.printing.ChineseCharacterTypePrinter;
import cc.printing.awt.piece.AwtForPiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.SimplePieceSetter;

public class AwtSimplePieceExample extends JPanel
{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1420, HEIGHT = 1050;
	static final int TYPE_SIZE = 200;
	static final int LINE_SIZE = 4;
	private String word = /* "⿰禾火秋"; // */"秋漿國一" + "⿰禾火⿱將水⿴囗或二"
			+ "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木三" + "變務森四" + "攵力木五";// */;
	static final String SUNG_FONT = "全字庫正宋體";
	static final String KAI_FONT = "全字庫正楷體";
	static final String BLACK_FONT = "文泉驛正黑";
	static final String 文鼎中圓 = "文鼎中圓";
	static private final String FontName = 文鼎中圓;
	private int FontStyle = Font.BOLD;

	public String getName()
	{
		return "AwtSimplePieceExample";
	}

	public int getWidth()
	{
		return WIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}

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
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
				FontName, FontStyle, TYPE_SIZE);
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter,null));
		}

		ChineseCharacterTypeAdjuster adjuster = new SimplePieceAdjuster();
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

	public static void main(String[] a)
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
}
