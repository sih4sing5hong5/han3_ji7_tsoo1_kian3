/*******************************************************************************
 * 著作權所有 (C) 民國102年 意傳文化科技
 * 開發者：薛丞宏
 * 網址：http://意傳.台灣
 * 字型提供：
 * 	全字庫授權說明
 * 		© 2012 中華民國行政院研究發展考核委員會。本字型檔採用創用CC「姓名標示－禁止改作」3.0臺灣版授權條款釋出。您可以在不變更字型內容之條件下，重製、散布及傳輸本字型檔之著作內容。惟應保留本字型名稱及著作權聲明。
 * 		http://www.cns11643.gov.tw/AIDB/copyright.do
 * 		
 * 	「中央研究院漢字部件檢字系統」2.65版釋出聲明
 * 		……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
 * 		http://cdp.sinica.edu.tw/cdphanzi/declare.htm
 * 		
 * 	吳守禮台語注音字型：
 * 		©2012從宜工作室：吳守禮、吳昭新，以CC01.0通用(CC01.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
 * 		http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
 * 		
 * 本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主：
 * 	１．得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
 * 	２．任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼；
 * 	３．將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼
 * 
 * 此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。
 * 
 * 漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。
 * 
 * 謝謝您的使用與推廣～～
 ******************************************************************************/
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
