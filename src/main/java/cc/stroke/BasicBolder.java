package cc.stroke;

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
	public Stroke getStroke(double width)
	{
		return new BasicStroke((float) width);
	}

}
