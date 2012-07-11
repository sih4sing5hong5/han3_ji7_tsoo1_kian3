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
import cc.moveable_type.piece.PieceMovableType;
import cc.printing.ChineseCharacterTypePrinter;
import cc.printing.awt.piece.AwtForPiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.piece.SimplePieceSetter;

/** A demonstration of writing custom Stroke classes */
public class AwtTestExample extends JPanel
{
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1400, HEIGHT = 900; // Size of our example
	static final int TYPE_SIZE = 200;
	static final int LINE_SIZE = 3;
	private String word = /* "⿰禾火";// */"秋漿國⿰禾火⿱將水⿴囗或⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木變務森攵力木";
	private String FontName = "全字庫正宋體";
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

	Stroke[] strokes = new Stroke[] { new BasicStroke(4.0f), // The standard,
	// predefined
	// stroke
	};

	/** Draw the example */
	public void paint(Graphics g1)
	{
//		Font f = new Font("全字庫正宋體", Font.BOLD, 140);
//		GlyphVector gv = f.createGlyphVector(
//				((Graphics2D) g1).getFontRenderContext(), "口");
		// // System.out.println(gv.getNumGlyphs());
		// // Shape shape = gv.getOutline();
		// // Rectangle rectangle2d = new Rectangle(0, 0, 1000, 200);
		// // Area aa = new Area(rectangle2d);
		// Area aa = new Area(gv.getOutline());
		// Rectangle2D.Double rectangle2d = new Rectangle2D.Double(),
		// rectangle2d2 = new Rectangle2D.Double(
		// 1, 1, 2, 3);
		// rectangle2d=(Rectangle2D.Double) gv.getOutline().getBounds2D();
		// RectangularArea rectangularArea = new RectangularArea(rectangle2d);
		// rectangularArea.getTerritory();
		// // rectangle2d.setFrame(rectangle2d);
		// System.out.println("re=" + rectangularArea.getTerritory().getX());
		// // rectangularArea.getTerritory().setFrame(rectangle2d2);
		// rectangle2d.setFrame(rectangle2d2);
		//
		// // aa.transform(new AffineTransform(5, 0,0, 5,0,0));
		// System.out.println("re=" + rectangularArea.getTerritory().getX());
		// // rectangle2d=aa.getBounds2D();
		// System.out.println("re=" + rectangularArea.getTerritory().getX());
		// GeneralPath gg = new GeneralPath(aa);
		// double circumference = 0.0;
		// double nowx = 0.0, nowy = 0.0;
		// SimplePolygon simplePolygon=new SimplePolygon();
		// double cs=0.0,as=0.0;
		// for (PathIterator pp = gg.getPathIterator(null); !pp.isDone(); pp
		// .next())
		// {
		// double[] coords = new double[6];
		// int type = pp.currentSegment(coords);
		// switch (type)
		// {
		// case PathIterator.SEG_CUBICTO:
		// System.out.println(coords[0] + " " + coords[1]);
		// System.out.println(coords[2] + " " + coords[3]);
		// System.out.println(coords[4] + " " + coords[5]);
		// simplePolygon.addPoint(coords[0] , coords[1]);
		// simplePolygon.addPoint(coords[2] , coords[3]);
		// simplePolygon.addPoint(coords[4] , coords[5]);
		// circumference += Point2D.distance(nowx, nowy, coords[0],
		// coords[1]);
		// circumference += Point2D.distance(coords[0], coords[1],
		// coords[2], coords[3]);
		// circumference += Point2D.distance(coords[2], coords[3],
		// coords[4], coords[5]);
		// nowx = coords[4];
		// nowy = coords[5];
		// System.out.println("cc=" + circumference);
		// break;
		// case PathIterator.SEG_QUADTO:
		// System.out.println(coords[0] + " " + coords[1]);
		// System.out.println(coords[2] + " " + coords[3]);
		// simplePolygon.addPoint(coords[0] , coords[1]);
		// simplePolygon.addPoint(coords[2] , coords[3]);
		// circumference += Point2D.distance(nowx, nowy, coords[0],
		// coords[1]);
		// circumference += Point2D.distance(coords[0], coords[1],
		// coords[2], coords[3]);
		// nowx = coords[2];
		// nowy = coords[3];
		// System.out.println("cc=" + circumference);
		// break;
		// case PathIterator.SEG_LINETO:
		// System.out.println("now=" + nowx + " " + nowy);
		// System.out.println(coords[0] + " " + coords[1]);
		// simplePolygon.addPoint(coords[0] , coords[1]);
		// circumference += Point2D.distance(nowx, nowy, coords[0],
		// coords[1]);
		// nowx = coords[0];
		// nowy = coords[1];
		// System.out.println("cc=" + circumference);
		// break;
		// case PathIterator.SEG_MOVETO:
		// System.out.println(coords[0] + " " + coords[1]);
		// simplePolygon.addPoint(coords[0] , coords[1]);
		// nowx = coords[0];
		// nowy = coords[1];
		// break;
		// case PathIterator.SEG_CLOSE:
		//
		// System.out.println("AAAAAAAAAAAAAAAAAAAAAAAa");
		// cs+=simplePolygon.getCircumference();
		// as+=simplePolygon.getRegionSize();
		// simplePolygon.clear();
		//
		// }
		// System.out.println("cs="+simplePolygon.getCircumference());
		// }
		//
		// System.out.println("-----------------------------");
		//
		// System.out.println("cc=" + circumference);
		// System.out.println("cs=" + cs+" as="+as);
		Graphics2D g = (Graphics2D) g1;
		// g.setStroke(new NullStroke());
//		g.translate(100, 100);
//		g.draw(new RectangularArea(gv.getOutline()));
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
		ChineseCharacterTypeSetter writer = new SimplePieceSetter(
				new FontRenderContext(new AffineTransform(),
						java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
						java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
				FontName, FontStyle, 100);
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i)
		{
			ccmvArray.add(ccArray.elementAt(i).typeset(writer));
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
