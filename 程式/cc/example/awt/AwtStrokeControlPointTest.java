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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cc.example.reference.ControlPointsStroke;
import cc.筆觸.NullStroke;
import cc.筆觸.SimplifyStroke;
import cc.筆觸工具.ShapeAnalyst;

/**
 * 加寬工具效果分析，可用來觀察控制點。
 * 
 * @author Ihc
 */
public class AwtStrokeControlPointTest extends JPanel
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
				"一");
		System.out.println(gv.getNumGlyphs());
		Area area = new Area(gv.getOutline());
		// area = new Area(new Rectangle2D.Double(100, 200, 100, 200));
		// area = new Area(new QuadCurve2D.Double(0.0, 0.0, 100.0, 0.0, 100,
		// 100));
		// area = new Area(new CubicCurve2D.Double(0.0, 0.0, 100.0, 0.0, 100,
		// 100,
		// 0, 100));
		GeneralPath generalPath=new GeneralPath();
		generalPath.moveTo(626.671875 ,-295.90625);
		generalPath.lineTo(583.53125 ,-246.203125);
		generalPath.lineTo(113.703125 ,-246.203125);
		generalPath.quadTo(69.29687500042495 ,-246.203125 ,10.140625001132197, -260.51562499972607);
		generalPath.lineTo(10.140625000118241, -260.51562499972607 );
		generalPath.lineTo(38.953125, -193.765625);
		generalPath.quadTo(74.484375 ,-197.5625 ,113.703125 ,-197.5625);
		generalPath.closePath();
		/*
		0 main=0 626.671875 -295.90625 0.0 0.0 0.0 0.0
		1 main=1 583.53125 -246.203125 0.0 0.0 0.0 0.0
		2 main=1 113.703125 -246.203125 0.0 0.0 0.0 0.0
		3 main=2 69.29687500042495 -246.203125 10.140625001132197 -260.51562499972607 0.0 0.0
		4 main=1 10.140625000118241 -260.51562499972607 10.140625001132197 -260.51562499972607 0.0 0.0
		5 main=1 38.953125 -193.765625 10.140625001132197 -260.51562499972607 0.0 0.0
		6 main=2 74.484375 -197.5625 113.703125 -197.5625 0.0 0.0
		*/
//		area=new Area(generalPath);
		graphics2D.setColor(Color.RED);
		graphics2D.setStroke(new ControlPointsStroke(5));
		graphics2D.translate(10, 610);
		Stroke stroke = new SimplifyStroke();
		System.out.println("--1");
		 new ShapeAnalyst(area);
		System.out.println("--2");
//		 area = new Area(stroke.createStrokedShape(area));
		  new ShapeAnalyst(area);
		  
		System.out.println("--3");
		graphics2D.draw(area);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(750, 000);
//		Area a = area;
		for (int i = 10; i >= 10; i -= 2)
		{
			Color color = new Color((10 - i) * 0x101000 + 0xff);
			graphics2D.setColor(color);
			graphics2D.setStroke(new NullStroke());
//			graphics2D.setStroke(new RadialStroke(i * 5));
			graphics2D.setStroke(new ControlPointsStroke(5));
//			graphics2D.setStroke(new SimplifyStroke(5));
			stroke = new SimplifyStroke();
//			area = new Area(stroke.createStrokedShape(area));
			graphics2D.draw(stroke.createStrokedShape(area));
//			graphics2D.draw(area);
			// graphics2D.translate(10, 0);
		}
		Color color = new Color(12 * 0x101000 + 0xff);
		graphics2D.setColor(color);
		// graphics2D.setStroke(new NullStroke());
//		graphics2D.draw(a);
		System.out.println("XD");
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
		f.setContentPane(new AwtStrokeControlPointTest());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}

	@Override
	public String getName()
	{
		return "加寬工具效果分析";
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
