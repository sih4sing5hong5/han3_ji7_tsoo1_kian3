package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public interface 字體介面
{
	public 字體介面 調整字體大小(float 字體大小);

	public 字體介面 調整字體選項(int 字體選項);

	public 字體介面 調整字體參數(int 字體選項, float 字型大小);

	public boolean 是否可顯示該字型(int 控制碼);

	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼);
}
