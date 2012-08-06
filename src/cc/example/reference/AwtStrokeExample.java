package cc.example.reference;

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
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.bolder.NullStroke;

/** 修改自http://www.java2s.com/Code/Java/2D-Graphics-GUI/CustomStrokes.htm */
public class AwtStrokeExample extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	static final int WIDTH = 1420;
	/** 視窗高度 */
	static final int HEIGHT = 1050;

	/** These are the various stroke objects we'll demonstrate */
	Stroke[] strokes = new Stroke[] { new BasicStroke(4.0f), // The standard,
			// predefined
			// stroke
			new NullStroke(), // A Stroke that does nothing
			new DoubleStroke(8.0f, 2.0f), // A Stroke that strokes twice
			new ControlPointsStroke(2.0f), // Shows the vertices & control
			// points
			new SloppyStroke(2.0f, 3.0f) // Perturbs the shape before stroking
	};

	@Override
	public void paint(Graphics g1)
	{
		Graphics2D g = (Graphics2D) g1;
		// Get a shape to work with. Here we'll use the letter B
		Font f = new Font("全字庫正宋體", Font.BOLD, 140);
		GlyphVector gv = f.createGlyphVector(g.getFontRenderContext(), "永應");
		System.out.println(gv.getNumGlyphs());
		Shape shape = gv.getOutline();

		// Set drawing attributes and starting position
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(20, 160);

		// Draw the shape once with each stroke
		for (int i = 0; i < strokes.length; i++)
		{
			g.setStroke(strokes[i]); // set the stroke
			g.draw(shape); // draw the shape
			g.translate(0, 160); // move to the right
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
		f.setContentPane(new AwtStrokeExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "筆劃使用範例";
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

// /**
// * This Stroke implementation does nothing. Its createStrokedShape() method
// * returns an unmodified shape. Thus, drawing a shape with this Stroke is the
// * same as filling that shape!
// */
// class NullStroke implements Stroke
// {
// public Shape createStrokedShape(Shape s)
// {
// return s;
// }
// }

/**
 * This Stroke implementation applies a BasicStroke to a shape twice. If you
 * draw with this Stroke, then instead of outlining the shape, you're outlining
 * the outline of the shape.
 */
class DoubleStroke implements Stroke
{
	/**
	 * 第一層筆劃
	 */
	BasicStroke stroke1;
	/**
	 * 第二層筆劃
	 */
	BasicStroke stroke2; // the two strokes to use

	/**
	 * 建立雙層筆劃
	 * 
	 * @param width1
	 *            第一層筆劃寬度
	 * @param width2
	 *            第二層筆劃寬度
	 */
	public DoubleStroke(float width1, float width2)
	{
		stroke1 = new BasicStroke(width1); // Constructor arguments specify
		stroke2 = new BasicStroke(width2); // the line widths for the strokes
	}

	@Override
	public Shape createStrokedShape(Shape s)
	{
		// Use the first stroke to create an outline of the shape
		Shape outline = stroke1.createStrokedShape(s);
		// Use the second stroke to create an outline of that outline.
		// It is this outline of the outline that will be filled in
		return stroke2.createStrokedShape(outline);
	}
}

/**
 * This Stroke implementation strokes the shape using a thin line, and also
 * displays the end points and Bezier curve control points of all the line and
 * curve segments that make up the shape. The radius argument to the constructor
 * specifies the size of the control point markers. Note the use of PathIterator
 * to break the shape down into its segments, and of GeneralPath to build up the
 * stroked shape.
 */

class ControlPointsStroke implements Stroke
{
	/** 控制點大小 */
	float radius; // how big the control point markers should be

	/**
	 * 建立顯示控制點的筆劃
	 * 
	 * @param radius
	 *            控制點大小
	 */
	public ControlPointsStroke(float radius)
	{
		this.radius = radius;
	}

	@Override
	public Shape createStrokedShape(Shape shape)
	{
		// Start off by stroking the shape with a thin line. Store the
		// resulting shape in a GeneralPath object so we can add to it.
		GeneralPath strokedShape = new GeneralPath(
				new BasicStroke(0.3f).createStrokedShape(shape));

		// Use a PathIterator object to iterate through each of the line and
		// curve segments of the shape. For each one, mark the endpoint and
		// control points (if any) by adding a rectangle to the GeneralPath
		float[] coords = new float[6];
		int cnt = 0;
		for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i
				.next())
		{
			int type = i.currentSegment(coords);
			switch (type)
			{
			case PathIterator.SEG_CUBICTO:
				markPoint(strokedShape, coords[4], coords[5]); // falls through
			case PathIterator.SEG_QUADTO:
				markPoint(strokedShape, coords[2], coords[3]); // falls through
			case PathIterator.SEG_MOVETO:
			case PathIterator.SEG_LINETO:
				markPoint(strokedShape, coords[0], coords[1]); // falls through
			case PathIterator.SEG_CLOSE:
				break;
			}
			++cnt;
		}
		System.out.println(cnt);
		return strokedShape;
	}

	/**
	 * 標上控制點 原註解：Add a small square centered at (x,y) to the specified path
	 * 
	 * @param path
	 *            要加上的路徑
	 * @param x
	 *            水平位置
	 * @param y
	 *            垂直位置
	 */

	void markPoint(GeneralPath path, float x, float y)
	{
		path.moveTo(x - radius, y - radius); // Begin a new sub-path
		path.lineTo(x + radius, y - radius); // Add a line segment to it
		path.lineTo(x + radius, y + radius); // Add a second line segment
		path.lineTo(x - radius, y + radius); // And a third
		path.closePath(); // Go back to last moveTo position
	}
}

/**
 * This Stroke implementation randomly perturbs the line and curve segments that
 * make up a Shape, and then strokes that perturbed shape. It uses PathIterator
 * to loop through the Shape and GeneralPath to build up the modified shape.
 * Finally, it uses a BasicStroke to stroke the modified shape. The result is a
 * "sloppy" looking shape.
 */
class SloppyStroke implements Stroke
{
	/** 要使用的筆劃 */
	BasicStroke stroke;
	/** 潦草度 */
	float sloppiness;

	/**
	 * 建立一個潦草筆劃
	 * 
	 * @param width
	 *            筆劃寬度
	 * @param sloppiness
	 *            潦草度
	 */
	public SloppyStroke(float width, float sloppiness)
	{
		this.stroke = new BasicStroke(width); // Used to stroke modified shape
		this.sloppiness = sloppiness; // How sloppy should we be?
	}

	@Override
	public Shape createStrokedShape(Shape shape)
	{
		GeneralPath newshape = new GeneralPath(); // Start with an empty shape

		// Iterate through the specified shape, perturb its coordinates, and
		// use them to build up the new shape.
		float[] coords = new float[6];
		for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i
				.next())
		{
			int type = i.currentSegment(coords);
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				perturb(coords, 2);
				newshape.moveTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_LINETO:
				perturb(coords, 2);
				newshape.lineTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_QUADTO:
				perturb(coords, 4);
				newshape.quadTo(coords[0], coords[1], coords[2], coords[3]);
				break;
			case PathIterator.SEG_CUBICTO:
				perturb(coords, 6);
				newshape.curveTo(coords[0], coords[1], coords[2], coords[3],
						coords[4], coords[5]);
				break;
			case PathIterator.SEG_CLOSE:
				newshape.closePath();
				break;
			}
		}

		// Finally, stroke the perturbed shape and return the result
		return stroke.createStrokedShape(newshape);
	}

	// Randomly modify the specified number of coordinates, by an amount
	// specified by the sloppiness field.
	/**
	 * 建立抖動位置
	 * 
	 * @param coords
	 *            預修改位置
	 * @param numCoords
	 *            點的數量
	 */
	void perturb(float[] coords, int numCoords)
	{
		for (int i = 0; i < numCoords; i++)
			coords[i] += (float) ((Math.random() * 2 - 1.0) * sloppiness);
	}
}
