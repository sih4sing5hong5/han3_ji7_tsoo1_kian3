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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.adjusting.image.SampleImageAdjuster;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterUtility;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.image.ImageMoveableType;
import cc.printing.awt.AwtForImagePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.setting.image.SimpleImageSetter;

/** A demonstration of writing custom Stroke classes */
public class AwtTestExample extends JPanel {
	private static final long serialVersionUID = 1L;
	static final int WIDTH = 1024, HEIGHT = 800; // Size of our example
	static final int TYPE_SIZE = 200;
	private String word = "囗囗囗⿰禾火⿱將水⿴囗或⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱木⿰木木變務森木攵力";
	private String FontName = "全字庫正宋體";
	private int FontStyle = Font.BOLD;

	public String getName() {
		return "Custom Strokes";
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	Stroke[] strokes = new Stroke[] { new BasicStroke(4.0f), // The standard,
	// predefined
	// stroke
	};

	/** Draw the example */
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		// Font f = new Font(FontName, FontStyle, 140);
		// Set drawing attributes and starting position
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(WIDTH - TYPE_SIZE - 20, 0);
		g.setStroke(new NullStroke());

		ChineseCharacterUtility ccUtility = new ChineseCharacterUtility(word);
		Vector<ChineseCharacter> ccArray = ccUtility.parseText();
		ChineseCharacterTypeSetter writer = new SimpleImageSetter();
		Vector<ChineseCharacterMovableType> ccmvArray = new Vector<ChineseCharacterMovableType>();
		for (int i = 0; i < ccArray.size(); ++i) {
			ccmvArray.add(ccArray.elementAt(i).typeset(writer));
		}
		Point model = new Point(TYPE_SIZE, TYPE_SIZE);
		SampleImageAdjuster sampleImageAdjuster = new SampleImageAdjuster();
		for (int i = 0; i < ccArray.size(); ++i) {
			((ImageMoveableType) ccmvArray.elementAt(i)).setRegion(model);
			ccmvArray.elementAt(i).adjust(sampleImageAdjuster);
		}
		System.out.println(ccArray.size());
		AwtForImagePrinter awtForImagePrinter = new AwtForImagePrinter(g,
				FontName, FontStyle);
		for (int i = 0; i < ccmvArray.size(); ++i) {
			ccmvArray.elementAt(i).print(awtForImagePrinter);
			g.translate(0, TYPE_SIZE);// move to the down
			if (i % 3 == 2)
				g.translate(-TYPE_SIZE * 1, -TYPE_SIZE * 3);// the new line
		}
		return;
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setContentPane(new AwtTestExample());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

}
