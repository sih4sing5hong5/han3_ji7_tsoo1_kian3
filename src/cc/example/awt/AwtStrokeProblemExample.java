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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** A demonstration of writing custom Stroke classes */
public class AwtStrokeProblemExample extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1500, HEIGHT = 800; // Size of our example

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

	// These are the various stroke objects we'll demonstrate
	Stroke[] strokes = new Stroke[] { new BasicStroke(6.0f), // The standard,
			// predefined
			// stroke
			new NullStroke(), // A Stroke that does nothing
			new DoubleStroke(8.0f, 2.0f), // A Stroke that strokes twice
			new ControlPointsStroke(2.0f), // Shows the vertices & control
			// points
			new SloppyStroke(2.0f, 3.0f) // Perturbs the shape before stroking
	};

	/** Draw the example */
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		// Get a shape to work with. Here we'll use the letter B
		Font f = new Font("全字庫正宋體", Font.BOLD, 140);
		GlyphVector gv = f.createGlyphVector(g.getFontRenderContext(), "永應言木森");
		System.out.println(gv.getNumGlyphs());
		Area area = new Area(gv.getOutline());
//		AffineTransform affineTransform = new AffineTransform();
//		affineTransform.setToScale(.9, .9);
//		affineTransform.setToScale(1., 1.);
//		area.transform(affineTransform);
		// Set drawing attributes and starting position
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(20, 160);

		// Draw the shape once with each stroke
		for (int i = 0; i < strokes.length; i++)
		{
			g.setStroke(strokes[i]); // set the stroke
			g.draw(area); // draw the shape
			g.translate(0, 160); // move to the right
		}
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
		f.setContentPane(new AwtStrokeProblemExample());
		f.setSize(1500, 800);
		f.setVisible(true);
	}

}
