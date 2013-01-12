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
	/**
	 * 調整字體的字型大細。
	 * 
	 * @param 字體大小
	 *            欲調整到偌大
	 * @return　調整好的字體
	 */
	public abstract 字體介面 調整字體大小(float 字體大小);

	/**
	 * 選擇字體的性質。
	 * 
	 * @param 字體選項
	 *            欲調整的的性質
	 * @return　調整好的字體
	 */
	public abstract 字體介面 調整字體選項(int 字體選項);

	/**
	 * 改變字體的參數。
	 * 
	 * @param 字體選項
	 *            欲調整的的性質
	 * @param 字型大小
	 *            欲調整到偌大
	 * @return　調整好的字體
	 */
	public abstract 字體介面 調整字體參數(int 字體選項, float 字型大小);

	/**
	 * 判斷這个字體的預設字體有法度顯示這个控制號碼無。
	 * 
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @return 字體敢有這个控制碼的字型無
	 */
	public boolean 是否可顯示該字型(int 控制碼)
	{
		return 是否可顯示該字型(控制碼, 0);
	}

	/**
	 * 判斷這个字體的預設字體有法度顯示這个控制號碼無。
	 * 
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @param 字體編號
	 *            選擇佗一个字體
	 * @return 字體敢有這个控制碼的字型無
	 */
	public abstract boolean 是否可顯示該字型(int 控制碼, int 字體編號);

	/**
	 * 提到預設字體的控制號碼字型。
	 * 
	 * @param 渲染選項
	 *            活字的渲染屬性
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @return 字體敢有這个控制碼的字型無
	 */
	public GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼)
	{
		return 取得該字型(渲染選項, 控制碼, 0);
	}

	/**
	 * 提到特地字體的控制號碼字型。
	 * 
	 * @param 渲染選項
	 *            活字的渲染屬性
	 * @param 控制碼
	 *            欲判斷的控制號碼
	 * @param 字體編號
	 *            選擇佗一个字體
	 * @return 字體敢有這个控制碼的字型無
	 */
	public abstract GlyphVector 取得該字型(FontRenderContext 渲染選項, int 控制碼, int 字體編號);
}
