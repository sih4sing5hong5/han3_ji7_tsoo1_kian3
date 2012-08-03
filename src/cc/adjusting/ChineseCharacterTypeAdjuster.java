package cc.adjusting;

import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

/**
 * 活字調整工具。依活字結構（<code>ChineseCharacterMovableType</code>）本身的組合調整大小位置粗細…等等資訊。
 * <p>
 * SimplePiece是兩兩配對後定框，再調部件大小，但無法物件距離難貼近或拉開。
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
