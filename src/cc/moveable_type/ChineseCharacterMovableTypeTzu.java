package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 漢字活字樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterMovableTypeTzu</code>記錄底下活字的排版資訊。
 * 
 * @author Ihc
 */
public class ChineseCharacterMovableTypeTzu extends ChineseCharacterMovableType
{
	/**
	 * 以<code>ChineseCharacter</code>部件結構建立字活字結構
	 * 
	 * @param chineseCharacter
	 *            部件結構
	 */
	public ChineseCharacterMovableTypeTzu(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
	}

	/**
	 * 底下的各個活字
	 */
	protected ChineseCharacterMovableType[] children;

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
	 * @return 底下的各個部件
	 */
	public ChineseCharacterMovableType[] getChildren()
	{
		return children;
	}

	/**
	 * 設定底下的各個部件
	 * @param children 底下的各個部件
	 */
	public void setChildren(ChineseCharacterMovableType[] children)
	{
		this.children = children;
	}
}
