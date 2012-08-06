package cc.adjusting.bolder;

import java.awt.Stroke;

public class RadialBolder implements ChineseCharacterTypeBolder
{
	@Override
	public Stroke getStroke(double width)
	{
		return new RadialStroke(width);
	}

}
