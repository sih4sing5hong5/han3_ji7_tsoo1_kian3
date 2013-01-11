package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * 愛予設定工具揣字的介面。
 * 
 * @author Ihc
 */
public abstract class 字體介面
{
	// /** 活字字體的選項 */
	// private int 字體選項;
	// /** 活字的大小 */
	// private int 字體大小;
	public abstract 字體介面 調整字體大小(float 字體大小);

	public abstract 字體介面 調整字體選項(int 字體選項);

	public abstract 字體介面 調整字體參數(int 字體選項, float 字型大小);

	public boolean 是否可顯示該字型(int 控制碼)
	{
		return 是否可顯示該字型(控制碼, 0);
	}

	public abstract boolean 是否可顯示該字型(int 控制碼, int 字體編號);

	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼)
	{
		return 取得該字型(渲染選項, 控制碼, 0);
	}

	public abstract GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號);
}
