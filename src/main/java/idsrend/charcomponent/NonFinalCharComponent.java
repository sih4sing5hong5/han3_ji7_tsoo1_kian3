package idsrend.charcomponent;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.char_indexingtool.ChineseCharacterTypeSetter;
import cc.movabletype.ChineseCharCompositeMoveabletype;

/**
 * 漢字部件樹狀結構的葉子。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。 <code>ChineseCharacterWen</code>
 * 記錄使用的部件。
 * 
 * @author Ihc
 */
public class NonFinalCharComponent extends CharComponent
{
	/**
	 * 部件的Unicode編碼
	 */
	private final int codePoint;

	/**
	 * 初使化一个新的文部件。
	 * 
	 * @param 面頂彼个字部件
	 *            樹狀結構面頂彼个字部件
	 * @param 控制碼
	 *            這个文部件字的統一碼控制碼
	 */
	/**
	 * 建立一個文部件
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層為樹狀的樹根，傳入null
	 * @param codePoint
	 *            部件的Unicode編碼
	 */
	public NonFinalCharComponent(int codePoint)
	{
		this.codePoint = codePoint;
	}

	public NonFinalCharComponent(String 組字式)
	{
		this(組字式.codePointAt(0));
	}

	@Override
	public boolean 是文部件()
	{
		return true;
	}

	@Override
	public boolean 是字部件()
	{
		return false;
	}

	@Override
	public String 樹狀結構組字式()
	{
		return 部件組字式();
	}

	/**
	 * 取得部件Unicode編碼
	 * 
	 * @return 部件Unicode編碼
	 */
	public int Unicode編號()
	{
		return codePoint;
	}

	@Override
	public ChineseCharCompositeMoveabletype typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent)
	{
		return chineseCharacterTypeSetter.setWen(parent, this);
	}
}
