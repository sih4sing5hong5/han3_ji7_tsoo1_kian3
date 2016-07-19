package cc.char_indexingtool;

import cc.movabletype.ChineseCharacterMovableTypeTzu;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;
import cc.movabletype.ChineseCharCompositeMoveabletype;

/**
 * 活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>ChineseCharacterMovableType</code>）。
 * 
 * @author Ihc
 */
public interface ChineseCharacterTypeSetter
{
	/**
	 * 產生並初使化獨體活字
	 * 
	 * @param parent
	 *            此活字結構的上層活字
	 * @param chineseCharacterWen
	 *            要轉化的文（獨體）CharComponent
	 * @return 獨體活字
	 */
	public ChineseCharCompositeMoveabletype setWen(ChineseCharacterMovableTypeTzu parent,
			NonFinalCharComponent chineseCharacterWen);

	/**
	 * 產生並初使化合體活字
	 * 
	 * @param parent
	 *            此活字結構的上層活字
	 * @param chineseCharacterTzu
	 *            要轉化的字（合體）CharComponent
	 * @return 合體活字
	 */
	public ChineseCharCompositeMoveabletype setTzu(ChineseCharacterMovableTypeTzu parent,
			FinalCharComponent chineseCharacterTzu);
}
