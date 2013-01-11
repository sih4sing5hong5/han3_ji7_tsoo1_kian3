package cc.setting.piece;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;
import java.io.IOException;

/**
 * 佮幾仔个字體合做一个，方便存取佮判斷是毋是有這控制碼的字型。
 * 
 * @author Ihc
 */
public class 合併字體 extends 字體介面
{
	/** 　字體的物件 */
	private Font[] 字體集;
	/** 楷體字體下的所在 */
	static public final String[] 楷體字體位址表 = new String[] {
			"./字體/全字庫-101_11_28/TW-Kai-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Kai-Ext-B-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Kai-Plus-98_1.ttf",
			"./字體/漢字構形資料庫-2_7/cdpeudck.tte", };
	/** 宋體字體下的所在 */
	static public final String[] 宋體字體位址表 = new String[] {
			"./字體/全字庫-101_11_28/TW-Sung-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Sung-Ext-B-98_1.ttf",
			"./字體/全字庫-101_11_28/TW-Sung-Plus-98_1.ttf",
			"./字體/漢字構形資料庫-2_7/cdpeudc.tte", };
	/** 楷體字體的物件 */
	static private final 合併字體 楷體字體;
	/** 宋體字體的物件 */
	static private final 合併字體 宋體字體;
	static
	{
		楷體字體 = new 合併字體(楷體字體位址表);
		宋體字體 = new 合併字體(宋體字體位址表);
	}

	/** 予改變參數時的私有建構工具。 */
	private 合併字體()
	{
	}

	/**
	 * 合併字體的準備工具。
	 * 
	 * @param 字體位置
	 *            字體放的所在
	 */
	public 合併字體(String[] 字體位置)
	{
		字體集 = new Font[字體位置.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			try
			{
				字體集[i] = Font.createFont(Font.TRUETYPE_FONT, new File(字體位置[i]));// TODO
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
	public 字體介面 調整字體大小(float 字體大小)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體大小);
		}
		return 調整結果;
	}

	@Override
	public 字體介面 調整字體選項(int 字體選項)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項);
		}
		return 調整結果;
	}

	@Override
	public 字體介面 調整字體參數(int 字體選項, float 字體大小)
	{
		合併字體 調整結果 = new 合併字體();
		調整結果.字體集 = new Font[this.字體集.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			調整結果.字體集[i] = this.字體集[i].deriveFont(字體選項, 字體大小);
		}
		return 調整結果;
	}

	@Override
	public boolean 是否可顯示該字型(int 控制碼, int 字體編號)
	{
		for (Font 字體 : 字體集)
		{
			if (字體.canDisplay(控制碼))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		for (Font 字體 : 字體集)
		{
			if (字體.canDisplay(控制碼))
			{
				return 字體.createGlyphVector(渲染選項, Character.toChars(控制碼));
			}
		}
		return null;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的楷體字體。
	 * 
	 * @return　對公家機關提來的楷體字體
	 */
	static public 合併字體 提著楷體字體()
	{
		return 楷體字體;
	}

	/**
	 * 提著系統佮全字庫、構形資料庫公家機關提來的宋體字體。
	 * 
	 * @return　對公家機關提來的宋體字體
	 */
	static public 合併字體 提著宋體字體()
	{
		return 宋體字體;
	}
}
