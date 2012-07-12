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
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.adjusting.piece.ShapeInformation;
import cc.adjusting.piece.SimplePieceAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.piece.PieceMovableType;
import cc.printing.ChineseCharacterTypePrinter;
import cc.printing.awt.piece.AwtForPiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.SimplePieceSetter;

/** A demonstration of writing custom Stroke classes */
public class AwtTestExample extends JPanel
{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1420, HEIGHT = 1050; // Size of our example
	static final int TYPE_SIZE = 200;
	static final int LINE_SIZE = 4;
	private String word = /* "⿰禾火秋"; // */"秋漿國一" + "⿰禾火⿱將水⿴囗或二"
			+ "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木三" + "變務森四" + "攵力木五";// */;
	static final String SUNG_FONT = "全字庫正宋體";
	static final String KAI_FONT = "全字庫正楷體";
	static final String BLACK_FONT = "文泉驛正黑";
	static final String 文鼎中圓 = "文鼎中圓";
	static private final String FontName = SUNG_FONT;
	private int FontStyle = Font.BOLD;

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

	/** Draw the example */
	public void paint(Graphics g1)
	{
		boolean ok = false;
		if (ok)
		{
			Graphics2D g = (Graphics2D) g1;
			final int TEST = 144;
			Font f = new Font("全字庫正宋體", Font.BOLD, TEST);
			GlyphVector gv = f
					.createGlyphVector(
							((Graphics2D) g1).getFontRenderContext(),
							"一二三變務森國龜龘"/* ⿻ab!" */);
			// System.out.println(gv.getNumGlyphs());
			// Shape shape = gv.getGlyphLogicalBounds(0);
			System.out.println(gv.getGlyphLogicalBounds(0));
			System.out.println(gv.getGlyphOutline(0).getBounds());

			g.setStroke(new NullStroke());
			g.translate(0, 144);
			for (int i = 0; i < gv.getNumGlyphs(); ++i)
			{
				g.translate(1, 0);
				g.setColor(Color.GRAY);
				g.draw(gv.getGlyphLogicalBounds(i));
				g.setColor(Color.LIGHT_GRAY);
				g.draw(gv.getGlyphVisualBounds(i));
				g.setColor(Color.BLACK);
				g.draw(gv.getGlyphOutline(i));// TODO

				System.out.println((new Area(gv.getGlyphLogicalBounds(i)))
						.getBounds2D());
				System.out.println((new Area(gv.getGlyphVisualBounds(i)))
						.getBounds2D());
				// System.out.println(gv.getGlyphPosition(i));
				ShapeInformation shapeInformation = new ShapeInformation(
						new Area(gv.getGlyphOutline(i)));
				System.out.println(i + "="
						+ shapeInformation.getApproximativeRegion()
						/ shapeInformation.getApproximativeCircumference()
						/ TEST + "="
						+ shapeInformation.getApproximativeRegion() + "/"
						+ shapeInformation.getApproximativeCircumference()
						+ "/" + TEST);
			}
		}
		// Font f = new Font(FontName, FontStyle, 140);
		// Set drawing attributes and starting position
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(WIDTH - TYPE_SIZE * 1.1, 0);
		// g.translate(500, 500);
		g.setStroke(new NullStroke());

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterTypeSetter setter = new SimplePieceSetter(
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
				FontName, FontStyle, TYPE_SIZE);// TODO
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(setter));
		}
		// for (int i = 0; i < ccArray.size(); ++i)
		// {
		// if(((AreaMovableType) ccmvArray.elementAt(i)).getArea()!=null)
		// g.draw(((AreaMovableType) ccmvArray.elementAt(i)).getArea());
		// }
		// Shape shape=((AreaMovableType)ccmvArray.elementAt(0)).getArea();
		// System.out.println(shape.getBounds2D().getX()+" "+shape.getBounds2D().getY()+" "+shape.getBounds2D().getHeight());
		// Area area=new
		// Area(((AreaMovableType)ccmvArray.elementAt(0)).getArea());
		// System.out.println(area.getBounds2D().getX()+" "+area.getBounds2D().getY()+" "+area.getBounds2D().getHeight());

		ChineseCharacterTypeAdjuster adjuster = new SimplePieceAdjuster();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			((PieceMovableType) ccmvArray.elementAt(i)).getPiece()
					.setTerritoryDimension(TYPE_SIZE, TYPE_SIZE); // TODO 模組化
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
		f.setContentPane(new AwtTestExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

}
