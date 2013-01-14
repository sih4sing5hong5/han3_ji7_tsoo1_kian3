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

import cc.adjusting.area.SimpleAreaAdjuster;
import cc.adjusting.bolder.NullStroke;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.area.AreaMovableType;
import cc.moveable_type.area.AreaTool;
import cc.printing.awt.area.AwtForAreaPrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.area.SimpleAreaSetter;

/**
 * 區塊活字範例。
 * 
 * <pre>
 * 活字型態：<code>AwtForAreaPrinter</code>
 * 活字設定工具：<code>SimpleAreaSetter</code>
 * 活字調整工具：<code>SimpleAreaAdjuster</code>
 * 活字列印工具：<code>AwtForAreaPrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class AwtAreaExample extends JPanel
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
	static final int LINE_SIZE = 3;
	/** 測試漢字 */
	private String word = /* "⿰禾火";// */"秋漿國⿰禾火⿱將水⿴囗或⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木變務森攵力木";
	/** 測試用字體 */
	private String FontName = "全字庫正宋體";
	/** 測試用屬性 */
	private int FontStyle = Font.BOLD;

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		// Font f = new Font(FontName, FontStyle, 140);
		// Set drawing attributes and starting position
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(WIDTH - TYPE_SIZE, 0);
		// g.translate(500, 500);
		g.setStroke(new NullStroke());

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterTypeSetter setter = new SimpleAreaSetter(
				FontName,
				FontStyle,
				100,
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		Vector<漢字組建活字> ccmvArray = new Vector<漢字組建活字>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}
		
		SimpleAreaAdjuster sampleImageAdjuster = new SimpleAreaAdjuster();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			AreaTool.adjustSize(
					((AreaMovableType) ccmvArray.elementAt(i)).getBound(),
					TYPE_SIZE, TYPE_SIZE);
			ccmvArray.elementAt(i).adjust(sampleImageAdjuster);
		}
		System.out.println(ccArray.size());
		AwtForAreaPrinter awtForImagePrinter = new AwtForAreaPrinter(g);
		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			ccmvArray.elementAt(i).print(awtForImagePrinter);
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
		f.setContentPane(new AwtAreaExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{// TODO
		return "範例";
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
