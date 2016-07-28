package cc.movabletype;

import cc.layouttools.ChineseCharacterTypeAdjuster;
import cc.printtools.ChineseCharacterTypePrinter;
import idsrend.charcomponent.FinalCharComponent;

/**
 * 漢字活字樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterMovableTypeTzu</code>記錄底下活字的排版資訊。
 * 
 * @author Ihc
 */
public class ChineseCharacterMovableTypeTzu extends ChineseCharacterMovableType
{

	/**
	 * 底下的各個活字
	 */
	protected ChineseCharCompositeMoveabletype[] children;

	/**
	 * 以<code>ChineseCharacter</code>部件結構建立字活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterTzu
	 *            字部件結構
	 */
	public ChineseCharacterMovableTypeTzu(
			ChineseCharacterMovableTypeTzu parent,
			FinalCharComponent chineseCharacterTzu)
	{
		super(parent, chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.CompositionMethods().getNumberOfChildren();
		this.children = new ChineseCharacterMovableType[childrenSize];
	}

	@Override
	public void adjust(ChineseCharacterTypeAdjuster adjuster)
	{
		adjuster.adjustTzu(this);
		return;
	}

	@Override
	public void print(ChineseCharacterTypePrinter printer)
	{
		printer.printTzu(this);
		return;
	}

	/**
	 * 取得底下的各個部件
	 * 
	 * @return 底下的各個部件
	 */
	public ChineseCharCompositeMoveabletype[] getChildren()
	{
		return children;
	}
}
