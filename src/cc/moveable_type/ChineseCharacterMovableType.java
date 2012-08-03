package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.ChineseCharacter;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 儲存漢字活字樹狀結構。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterMovableType</code>為
 * <code>ChineseCharacterMovableTypeWen</code>及
 * <code>ChineseCharacterMovableTypeTzu</code> 的共用介面，方便以後活字的調整。
 * 
 * @author Ihc
 */
public abstract class ChineseCharacterMovableType
{
	/**
	 * 產生活字的部件結構
	 */
	private final ChineseCharacter chineseCharacter;
	/**
	 * 指向上一層的活字結構
	 */
	private final ChineseCharacterMovableTypeTzu parent;

	/**
	 * 以<code>ChineseCharacter</code>部件結構建立部件結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacter
	 *            部件結構
	 */
	public ChineseCharacterMovableType(ChineseCharacterMovableTypeTzu parent,ChineseCharacter chineseCharacter)
	{
		this.chineseCharacter = chineseCharacter;
		this.parent = parent;
	}

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

	/**
	 * 取得活字的部件結構
	 * 
	 * @return 活字的部件結構
	 */
	public ChineseCharacter getChineseCharacter()
	{
		return chineseCharacter;
	}

	/**
	 * 取得上一層部件結構。
	 * 
	 * @return 上一層部件結構
	 */
	public ChineseCharacterMovableType getParent()
	{
		return parent;
	}

//	/**
//	 * 設定上一層部件結構
//	 * 
//	 * @param parent
//	 *            上一層部件結構
//	 */
//	public void setParent(ChineseCharacterMovableType parent)
//	{
//		this.parent = parent;
//	}
}
