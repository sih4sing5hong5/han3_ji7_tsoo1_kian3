package idsrend.charcomponent;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.char_indexingtool.ChineseCharacterTypeSetter;
import cc.movabletype.ChineseCharCompositeMoveabletype;

/**
 * 漢字部件樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterTzu</code>記錄底下部件的組合方式。
 * 
 * @author Ihc
 */
public class FinalCharComponent extends CharComponent
{
	/**
	 * 部件的組合方式
	 */
	private final CompositionMethods type;
	/**
	 * 底下的各個部件
	 */
	private final CharComponent[] children;

	@Override
	public boolean 是文部件()
	{
		return false;
	}

	@Override
	public boolean 是字部件()
	{
		return true;
	}

	/**
	 * 取得部件的組合方式
	 * 
	 * @return 部件的組合方式
	 */
	public CompositionMethods CompositionMethods()
	{
		return type;
	}

	/**
	 * 取得底下的各個部件
	 * 
	 * @return 底下的各個部件
	 */
	public CharComponent[] 底下元素()
	{
		return children;
	}

	@Override
	public int Unicode編號()
	{
		return CompositionMethods().toCodePoint();
	}

	/**
	 * 初使化一个新的字部件。
	 * 
	 * @param 面頂彼个字部件
	 *            樹狀結構面頂彼个字部件
	 * @param 控制碼
	 *            這个字部件的組合符號統一碼控制碼
	 */
	/**
	 * 建立一个字部件。
	 * 
	 * @param parent
	 *            上一層的部件結構。若上層是樹狀的樹根，傳入null
	 * @param codePoint
	 *            組合符號的Unicode編碼
	 */
	public FinalCharComponent(int codePoint)
	{
		if (!CompositionMethods.isCombinationType(codePoint))
			throw new IllegalArgumentException("這不是部件組合符號!!");
		type = CompositionMethods.toCombinationType(codePoint);
		children = new CharComponent[type.getNumberOfChildren()];
	}

	public FinalCharComponent(String 組字式)
	{
		this(組字式.codePointAt(0));
	}

	@Override
	public ChineseCharCompositeMoveabletype typeset(
			ChineseCharacterTypeSetter chineseCharacterTypeSetter,
			ChineseCharacterMovableTypeTzu parent)
	{
		return chineseCharacterTypeSetter.setTzu(parent, this);
	}

	@Override
	public String 樹狀結構組字式()
	{
		StringBuilder 組字式 = new StringBuilder();
		組字式.append(部件組字式());
		for (CharComponent 子部件 : 底下元素())
		{
			組字式.append(子部件.樹狀結構組字式());
		}
		return 組字式.toString();
	}
}
