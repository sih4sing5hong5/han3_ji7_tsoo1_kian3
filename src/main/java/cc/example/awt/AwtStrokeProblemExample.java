package cc.example.awt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.stroke.NullStroke;


/**
 * 邊角問題重現。
 * 
 * @author Ihc
 */
public class AwtStrokeProblemExample extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1620;
	/** 視窗高度 */
	static final int HEIGHT = 1050;

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D graphics2D = (Graphics2D) g1;
		Font f = new Font("全字庫正宋體", Font.BOLD, 700);
		GlyphVector gv = f.createGlyphVector(graphics2D.getFontRenderContext(),
				"永森應言木變");
		System.out.println(gv.getNumGlyphs());
		Area area = new Area(gv.getOutline());
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(20, 700);
		for (int i = 10; i >= 0; i -= 2)
		{
			Color color=new Color((10-i)*0x151515);
			graphics2D.setColor(color);
			graphics2D.setStroke(new BasicStroke(i*10));
			graphics2D.draw(area);
			graphics2D.setStroke(new NullStroke());
			graphics2D.draw(area);
//			graphics2D.translate(10, 0);
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
		f.setContentPane(new AwtStrokeProblemExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "邊角問題重現";
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
