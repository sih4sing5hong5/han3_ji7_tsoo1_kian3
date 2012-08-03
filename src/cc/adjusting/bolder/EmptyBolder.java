package cc.adjusting.bolder;

import java.awt.Stroke;

/**
 * 產生空白的物件活字筆劃加寬工具。
 * 
 * @author Ihc
 */
public class EmptyBolder implements ChineseCharacterTypeBolder
{
	/**
	 * 共用的空白筆劃工具
	 */
	static private final EmptyStroke EMPTY_STROKE;
	static
	{
		EMPTY_STROKE = new EmptyStroke();
	}

	@Override
	public Stroke getStroke(float width)
	{
		return EMPTY_STROKE;
	}
}
