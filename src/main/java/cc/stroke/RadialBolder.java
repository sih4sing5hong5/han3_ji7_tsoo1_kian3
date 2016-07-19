package cc.stroke;

import java.awt.Stroke;

/**
 * 物件活字邊長外移筆劃加寬工具。此工具仍有問題，每個邊往外調，不顧及前後的邊，字會不平滑。而且尚有許多問題尚末解決。因為時間因素放棄，
 * 待日後系統完成後再行考慮。
 * 
 * @author Ihc
 */
@Deprecated
public class RadialBolder implements ChineseCharacterTypeBolder
{
	@Override
	public Stroke getStroke(double width)
	{
		return new RadialStroke(width);
	}

}
