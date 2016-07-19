package cc.movabletype;

import cc.layouttools.ChineseCharacterTypeAdjuster;
import cc.printtools.ChineseCharacterTypePrinter;

/**
 * 上基本的物件。原本是<code>ChineseCharacterMovableType</code>
 * 傷低，但是活字介面型態需要一个閣較低一層，所以閣新增一層予逐家用。
 * 
 * @author Ihc
 */
public interface ChineseCharCompositeMoveabletype
{
	/**
	 * 調整活字排法。用<code>ChineseCharacterTypeAdjuster</code>
	 * (活字調整工具)來調整ChineseCharacterMovableType(活字)。
	 * 
	 * @param adjuster
	 *            欲採用的活字調整工具
	 */
	public abstract void adjust(ChineseCharacterTypeAdjuster adjuster);

	/**
	 * 列印活字。用<code>ChineseCharacterTypePrinter</code>
	 * (活字列印工具)來列印ChineseCharacterMovableType(活字)。
	 * 
	 * @param printer
	 *            欲採用的活字列印工具
	 */
	public abstract void print(ChineseCharacterTypePrinter printer);
}
