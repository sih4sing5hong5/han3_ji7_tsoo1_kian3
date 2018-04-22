package cc.char_indexingtool;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;
import java.io.IOException;
import  javax.servlet.ServletContext;

/**
 * 佮幾仔个字體合做一个，方便存取佮判斷是毋是有這控制碼的字型。
 *
 * @author Ihc
 */
public class MergedFont extends CommonFont
{
	/** 字體的物件 */
	protected Font[] 字體集;
	/** 楷體字體下的所在 */
	static public final String[] 楷體字體位址表 = new String[] {
			"/font/CNS11643/TW-Kai-98_1.ttf",
			"/font/CNS11643/TW-Kai-Ext-B-98_1.ttf",
			"/font/CNS11643/TW-Kai-Plus-98_1.ttf",
			"/font/cdphanzi-2_7/cdpeudck.tte", };
	/** 宋體字體下的所在 */
	static public final String[] 宋體字體位址表 = new String[] {
			"/font/CNS11643/TW-Sung-98_1.ttf",
			"/font/CNS11643/TW-Sung-Ext-B-98_1.ttf",
			"/font/CNS11643/TW-Sung-Plus-98_1.ttf",
			"/font/cdphanzi-2_7/cdpeudc.tte", };
	/** 楷體字體的物件 */
	static private final MergedFont 楷體字體;
	/** 宋體字體的物件 */
	static private final MergedFont 宋體字體;
	static
	{
		 //ServletContext.getRealPath(/font/);
		楷體字體 = new MergedFont(楷體字體位址表);
		宋體字體 = new MergedFont(宋體字體位址表);
	}

	/** 予改變參數時的私有建構工具。 */
	protected MergedFont()
	{
	}

	/**
	 * 合併字體的準備工具。
	 *
	 * @param 字體位置
	 *            字體放的所在
	 */
	public MergedFont(String[] 字體位置)
	{
		字體集 = new Font[字體位置.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			try
			{
			    字體集[i] = Font.createFont(Font.TRUETYPE_FONT,
							this.getClass().getResourceAsStream(字體位置[i] )
							);

				// font
				// type
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
	}

	@Override
	public MergedFont 調整字體大小(float 字體大小)
	{
		MergedFont 調整結果 = new MergedFont();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體大小);
		}
		return 調整結果;
	}

	@Override
	public MergedFont 調整字體選項(int 字體選項)
	{
		MergedFont 調整結果 = new MergedFont();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項);
		}
		return 調整結果;
	}

	@Override
	public MergedFont 調整字體參數(int 字體選項, float 字體大小)
	{
		MergedFont 調整結果 = new MergedFont();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項, 字體大小);
		}
		return 調整結果;
	}

	@Override
	public boolean 有這个字型無(int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (Font 字體 : 字體集)
			{
				if (字體.canDisplay(控制碼))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public GlyphVector 提這个字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		if (字體編號 == 0)
		{
			for (Font 字體 : 字體集)
			{
				if (字體.canDisplay(控制碼))
				{
					return 字體.createGlyphVector(渲染選項, Character.toChars(控制碼));
				}
			}
		}
		return null;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的楷體字體。
	 *
	 * @return　對公家機關提來的楷體字體
	 */
	static public MergedFont 提著楷體字體()
	{
		return 楷體字體;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的宋體字體。
	 *
	 * @return　對公家機關提來的宋體字體
	 */
	static public MergedFont 提著宋體字體()
	{
		return 宋體字體;
	}
}
