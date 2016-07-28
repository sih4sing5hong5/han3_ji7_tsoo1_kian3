package cc.example.awt;

/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.stroke.NullStroke;
import cc.stroketool.ShapeInformation;

/** A demonstration of writing custom Stroke classes */
public class AwtTypesettingTest extends JPanel
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
	String word = /* "⿰禾火秋"; // */"秋漿國一" + "⿰禾火⿱將水⿴囗或二" + "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木三"
			+ "變務森四" + "攵力木五";// */;
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

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		final int TEST = 144;
		Font f = new Font(文泉驛正黑, Font.BOLD, TEST);
		GlyphVector gv = f
				.createGlyphVector(((Graphics2D) g1).getFontRenderContext(),
						"一二三變⿴務⿰意⿱國a,龜龘"/* ⿻ab!" */);
		// System.out.println(gv.getNumGlyphs());
		// Shape shape = gv.getGlyphLogicalBounds(0);
		System.out.println(gv.getGlyphLogicalBounds(0));
		System.out.println(gv.getGlyphOutline(0).getBounds());
		Rectangle2D.Double lDouble = new Rectangle2D.Double(0, 0, 1000, 1);

		g.setStroke(new NullStroke());
		g.translate(0, 144);
		g.setColor(Color.RED);
		g.draw(lDouble);
		for (int i = 0; i < gv.getNumGlyphs(); ++i)
		{
			g.translate(1, 0);
			g.setColor(Color.GRAY);
			g.draw(gv.getGlyphLogicalBounds(i));
			g.setColor(Color.LIGHT_GRAY);
			g.draw(gv.getGlyphVisualBounds(i));
			g.setColor(Color.BLACK);
			g.draw(gv.getGlyphOutline(i));// TODO 忘記要幹麻了＠＠

			System.out.println((new Area(gv.getGlyphLogicalBounds(i)))
					.getBounds2D());
			System.out.println((new Area(gv.getGlyphVisualBounds(i)))
					.getBounds2D());
			System.out.println((new Area(gv.getGlyphVisualBounds(i)))
					.getBounds2D().getY()
					+ (new Area(gv.getGlyphVisualBounds(i))).getBounds2D()
							.getHeight() / 2);
			// System.out.println(gv.getGlyphPosition(i));
			ShapeInformation shapeInformation = new ShapeInformation(new Area(
					gv.getGlyphOutline(i)));
			System.out.println(i + "="
					+ shapeInformation.getApproximativeRegion()
					/ shapeInformation.getApproximativeCircumference() / TEST
					+ "=" + shapeInformation.getApproximativeRegion() + "/"
					+ shapeInformation.getApproximativeCircumference() + "/"
					+ TEST);
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
		f.setContentPane(new AwtTypesettingTest());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "排版測試";
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
