package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class 整合字體 extends 字體介面
{
	合併字體[] 合併字體集;

	private 整合字體()
	{
	}

	public 整合字體(String[][] 字體位置)
	{
		合併字體集 = new 合併字體[字體位置.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			合併字體集[i] = new 合併字體(字體位置[i]);
		}
	}

	public 字體介面 調整字體大小(float 字體大小)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體大小(字體大小);
		}
		return 調整結果;
	}

	public 字體介面 調整字體選項(int 字體選項)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體選項(字體選項);
		}
		return 調整結果;
	}

	public 字體介面 調整字體參數(int 字體選項, float 字型大小)
	{
		整合字體 調整結果 = new 整合字體();
		調整結果.合併字體集 = new 合併字體[this.合併字體集.length];
		for (int i = 0; i < 合併字體集.length; ++i)
		{
			調整結果.合併字體集[i] = (合併字體) this.合併字體集[i].調整字體參數(字體選項, 字型大小);
		}
		return 調整結果;
	}

	public boolean 是否可顯示該字型(int 控制碼, int 字體編號)
	{
		return 合併字體集[字體編號].是否可顯示該字型(控制碼);
	}

	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號)
	{
		return 合併字體集[字體編號].取得該字型(渲染選項, 控制碼);
	}
}
