package cc.printtools;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.ChineseCharacterMovableTypeWen;

/**
 * 活字列印工具。接收活字結構（<code>ChineseCharacterMovableType</code>），並列印出來。
 * 
 * @author Ihc
 */
public interface ChineseCharacterTypePrinter
{
	/**
	 * 列印獨體活字
	 * 
	 * @param chineseCharacterMovableTypeWen
	 *            獨體活字
	 */
	void printWen(ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen);

	/**
	 * 列印合體活字
	 * 
	 * @param chineseCharacterMovableTypeTzu
	 *            合體活字
	 */
	void printTzu(ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu);
}
