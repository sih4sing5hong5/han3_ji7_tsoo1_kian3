package cc.tool.database;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import cc.example.awt.AwtTestExample;
import cc.stroke.NullStroke;

/**
 * 用來檢視字型裡有哪些字
 * 
 * @author Ihc
 */
public class FontStatusObserver extends AwtTestExample
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 字型大小 */
	static final int TYPE_SIZE = 400;
	/** 檔案位置 */
	String 檔案 = "/home/ihc/utf8/13.ttf";

	@Override
	public void paint(Graphics g1)
	{
		try
		{
//			System.out.println(Integer.toHexString(Character.codePointAt("",0)));
//			System.out.println(Character.codePointAt("", 0));
//			System.out.println(Integer.toHexString(Character.codePointAt("", 0)));
//			System.out.println(Character.toChars(0x8503));
			Graphics2D graphics2D = (Graphics2D) g1;
			Font fontㄉ = new Font("Sans", 0, 200);
			GlyphVector glyphv = fontㄉ.createGlyphVector(new FontRenderContext(
					new AffineTransform(),
					java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
					java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
					"HI");
			System.out.println("gl=" + glyphv.getNumGlyphs());
			graphics2D.translate(getWidth() - TYPE_SIZE * 1.1, TYPE_SIZE);
			// graphics2D.translate(300, 300);
			graphics2D.setStroke(new NullStroke());
			// graphics2D.draw(glyphv.getOutline());

			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(檔案));
			font = font.deriveFont(TYPE_SIZE * 1.0f);
			// font=fontㄉ;
			int cnt = 0;
			int which = 0;
//			for (int i = 0; i < 0xFFFFF; ++i)
			{
				int i=0x8503;
				i=0xf77d;
				i=0x280e9;
				i=0xf463;
				i=0xea6f;
				i=0xeec9;
				System.out.println(Character.toChars(i));
				System.out.println(font.canDisplay(i));
				if (font.canDisplay(i))
				{
					// if(which==0)
					which = i;
					char[] chars = Character.toChars(which);
					GlyphVector gv = font
							.createGlyphVector(
									new FontRenderContext(
											new AffineTransform(),
											java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
											java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
									chars);
					// System.out.println(gv.getOutline().getBounds2D().getWidth());
//					if (gv.getOutline().getBounds2D().getWidth() > 0.1)
					{
						// System.out.println("gv="+gv.getNumGlyphs());
						++cnt;
						final int pro = 1, line = 24;
//						if (cnt % pro == 0)
						{
							graphics2D.draw(gv.getOutline());
							graphics2D.translate(0, TYPE_SIZE);
							if (cnt % (line * pro) == 0)
								graphics2D.translate(-TYPE_SIZE, TYPE_SIZE
										* -line);
							System.out.println("i=" + i + " "
									+ Integer.toHexString(i));
						}
					}
				}
			}
			System.out.println("count=" + cnt);
		}
		catch (FontFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
		f.setContentPane(new FontStatusObserver());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}
}
