package cc.layouttools;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.ChineseCharacterMovableTypeWen;

/**
 * 活字調整工具。依活字結構（<code>ChineseCharacterMovableType</code>）本身的組合調整大小位置粗細…等等資訊。
 * <p>
 * 
 * @author Ihc
 */
public interface ChineseCharacterTypeAdjuster
{
	/**
	 * 調整獨體活字
	 * 
	 * @param chineseCharacterMovableTypeWen
	 *            欲調整之獨體活字
	 */
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen);

	/**
	 * 調整合體活字
	 * 
	 * @param chineseCharacterMovableTypeTzu
	 *            欲調整之合體活字
	 */
	public void adjustTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu);
}
