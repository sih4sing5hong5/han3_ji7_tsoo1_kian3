package cc.example.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.image.SimpleImageAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.image.ImageMoveableType;
import cc.printing.awt.image.AwtForImagePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.image.SimpleImageSetter;

/**
 * 圖片活字範例。
 * 
 * <pre>
 * 活字型態：<code>ImageMoveableType</code>
 * 活字設定工具：<code>SimpleImageSetter</code>
 * 活字調整工具：<code>SimpleImageAdjuster</code>
 * 活字列印工具：<code>AwtForImagePrinter</code>
 * </pre>
 * 
 * @author Ihc
 */
public class AwtImageExample extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1420;
	/** 視窗高度 */
	static final int HEIGHT = 1050;
	/** 字型大小 */
	static final int TYPE_SIZE = 200;
	/** 測試漢字 */
	private String word = "秋漿國⿰禾火⿱將水⿴囗或⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木變務森攵力木";
	/** 測試用字體 */
	private String FontName = "全字庫正宋體";
	/** 測試用屬性 */
	private int FontStyle = Font.BOLD;

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(WIDTH - TYPE_SIZE - 20, 0);
		g.setStroke(new NullStroke());

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterTypeSetter setter = new SimpleImageSetter();
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter, null));
		}
		Point model = new Point(TYPE_SIZE, TYPE_SIZE);
		SimpleImageAdjuster sampleImageAdjuster = new SimpleImageAdjuster();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			((ImageMoveableType) ccmvArray.elementAt(i)).setRegion(model);
			ccmvArray.elementAt(i).adjust(sampleImageAdjuster);
		}
		System.out.println(ccArray.size());
		AwtForImagePrinter awtForImagePrinter = new AwtForImagePrinter(g,
				FontName, FontStyle);
		for (int i = 0; i < ccmvArray.size(); ++i)
		{
			ccmvArray.elementAt(i).print(awtForImagePrinter);
			g.translate(0, TYPE_SIZE);// move to the down
			if (i % 3 == 2)
				g.translate(-TYPE_SIZE * 1, -TYPE_SIZE * 3);// the new line
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
		f.setContentPane(new AwtImageExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "圖片活字範例";
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
