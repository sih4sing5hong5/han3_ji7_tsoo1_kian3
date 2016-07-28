package cc.movabletype;

import cc.layouttools.ChineseCharacterTypeAdjuster;
import cc.printtools.ChineseCharacterTypePrinter;
import idsrend.charcomponent.NonFinalCharComponent;

/**
 * 漢字活字樹狀結構的葉子。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterMovableTypeWen</code> 記錄活字的排版資訊。
 * 
 * @author Ihc
 */
public class ChineseCharacterMovableTypeWen extends ChineseCharacterMovableType
{
	/**
	 * 以<code>ChineseCharacter</code>部件結構建立文活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterWen
	 *            文部件結構
	 */
	public ChineseCharacterMovableTypeWen(
			ChineseCharacterMovableTypeTzu parent,
			NonFinalCharComponent chineseCharacterWen)
	{
		super(parent, chineseCharacterWen);
	}

	@Override
	public void adjust(ChineseCharacterTypeAdjuster adjuster)
	{
		adjuster.adjustWen(this);
		return;
	}

	@Override
	public void print(ChineseCharacterTypePrinter printer)
	{
		printer.printWen(this);
		return;
	}
}
