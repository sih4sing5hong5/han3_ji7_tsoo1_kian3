package cc.adjusting.bolder;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * 使用<code>BasicStroke</code>的物件活字筆劃加寬工具。
 * 
 * @author Ihc
 */
public class BasicBolder implements ChineseCharacterTypeBolder
{
	@Override
	public Stroke getStroke(float width)
	{
		return new BasicStroke(width);
	}

}
