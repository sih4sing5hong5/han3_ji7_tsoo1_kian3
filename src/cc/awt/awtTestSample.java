package cc.awt;

/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.writer.ChineseCharacterWriter;
import cc.writer.SimpleWriter;

/** A demonstration of writing custom Stroke classes */
public class awtTestSample extends JPanel
{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1500, HEIGHT = 800; // Size of our example
	private String word = "⿰木火";

	public String getName()
	{
		return "Custom Strokes";
	}

	public int getWidth()
	{
		return WIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}

	Stroke[] strokes = new Stroke[] { new BasicStroke(4.0f), // The standard,
	// predefined
	// stroke
	};

	/** Draw the example */
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		// Font f = new Font("全字庫正宋體", Font.BOLD, 140);
		// GlyphVector gv = f.createGlyphVector(g.getFontRenderContext(), "永應");
		// System.out.println(gv.getNumGlyphs());
		// Shape shape = gv.getOutline();

		// Set drawing attributes and starting position
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(20, 160);

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterWriter writer = new SimpleWriter();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccArray.elementAt(i).generateByWriter(writer);
		}
		System.out.println(ccArray.size());
		for (int i = 0; i < ccArray.size(); ++i)
		{
		}

		// // Draw the shape once with each stroke
		// for (int i = 0; i < strokes.length; i++)
		// {
		// g.setStroke(strokes[i]); // set the stroke
		// g.draw(shape); // draw the shape
		// g.translate(0, 160); // move to the right
		// }
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
		f.setContentPane(new awtTestSample());
		f.setSize(1500, 800);
		f.setVisible(true);
	}

}
