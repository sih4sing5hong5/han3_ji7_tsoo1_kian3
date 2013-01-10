package cc.setting.piece;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;
import java.io.IOException;

public class 合併字體 implements 字體介面
{
	private Font[] 字體集;

	// /** 活字字體的名稱 */
	// private String fontName;
	// /** 活字字體的選項 */
	// private int 字體選項;
	// /** 活字的大小 */
	// private int 字體大小;

	private 合併字體()
	{
	}

//	public 合併字體(String[] 字體位置, int 字體選項, int 字體大小)
//	{
//		this(字體位置);
//		for (int i = 0; i < 字體集.length; ++i)
//			字體集[i] = 字體集[i].deriveFont(字體選項, 字體大小);
//	}
//
//	public 合併字體(String[] 字體位置, int 字體選項)
//	{
//		this(字體位置);
//		for (int i = 0; i < 字體集.length; ++i)
//			字體集[i] = 字體集[i].deriveFont(字體選項);
//	}
//
//	public 合併字體(String[] 字體位置, float 字體大小)
//	{
//		this(字體位置);
//		for (int i = 0; i < 字體集.length; ++i)
//			字體集[i] = 字體集[i].deriveFont(字體大小);
//	}

	public 合併字體(String[] 字體位置)
	{
		字體集 = new Font[字體位置.length];
		for (int i = 0; i < 字體集.length; ++i)
		{
			try
			{
				字體集[i] = Font.createFont(Font.TRUETYPE_FONT, new File(字體位置[i]));// TODO
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
	public boolean 是否可顯示該字型(int 控制碼)
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
	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼)
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
}
