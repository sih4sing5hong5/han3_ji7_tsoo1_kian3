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
package cc.tool.database;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import cc.adjusting.bolder.NullStroke;
import cc.example.awt.AwtTestExample;

/**
 * 用來檢視字型裡有哪些字
 * 
 * @author Ihc
 */
public class 字體狀態觀察工具 extends AwtTestExample
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 字型大小 */
	static final int TYPE_SIZE = 400;
	/** 檔案位置 */
	String 檔案 = "/home/ihc/utf8/13.ttf";

	@Override
	public void paint(Graphics g1)
	{
		try
		{
//			System.out.println(Integer.toHexString(Character.codePointAt("",0)));
//			System.out.println(Character.codePointAt("", 0));
//			System.out.println(Integer.toHexString(Character.codePointAt("", 0)));
//			System.out.println(Character.toChars(0x8503));
			Graphics2D graphics2D = (Graphics2D) g1;
			Font fontㄉ = new Font("Sans", 0, 200);
			GlyphVector glyphv = fontㄉ.createGlyphVector(new FontRenderContext(
					new AffineTransform(),
					java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
					java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
					"HI");
			System.out.println("gl=" + glyphv.getNumGlyphs());
			graphics2D.translate(getWidth() - TYPE_SIZE * 1.1, TYPE_SIZE);
			// graphics2D.translate(300, 300);
			graphics2D.setStroke(new NullStroke());
			// graphics2D.draw(glyphv.getOutline());

			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(檔案));
			font = font.deriveFont(TYPE_SIZE * 1.0f);
			// font=fontㄉ;
			int cnt = 0;
			int which = 0;
//			for (int i = 0; i < 0xFFFFF; ++i)
			{
				int i=0x8503;
				i=0xf77d;
				i=0x280e9;
				i=0xf463;
				i=0xea6f;
				i=0xeec9;
				System.out.println(Character.toChars(i));
				System.out.println(font.canDisplay(i));
				if (font.canDisplay(i))
				{
					// if(which==0)
					which = i;
					char[] chars = Character.toChars(which);
					GlyphVector gv = font
							.createGlyphVector(
									new FontRenderContext(
											new AffineTransform(),
											java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
											java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT),
									chars);
					// System.out.println(gv.getOutline().getBounds2D().getWidth());
//					if (gv.getOutline().getBounds2D().getWidth() > 0.1)
					{
						// System.out.println("gv="+gv.getNumGlyphs());
						++cnt;
						final int pro = 1, line = 24;
//						if (cnt % pro == 0)
						{
							graphics2D.draw(gv.getOutline());
							graphics2D.translate(0, TYPE_SIZE);
							if (cnt % (line * pro) == 0)
								graphics2D.translate(-TYPE_SIZE, TYPE_SIZE
										* -line);
							System.out.println("i=" + i + " "
									+ Integer.toHexString(i));
						}
					}
				}
			}
			System.out.println("count=" + cnt);
		}
		catch (FontFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
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
		f.setContentPane(new 字體狀態觀察工具());
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);
	}
}
