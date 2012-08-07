package cc.adjusting.bolder;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * 使用<code>FunctionalBasicStroke</code>的物件活字筆劃加寬工具。
 * 
 * @author Ihc
 */
public class FunctinoalBasicBolder implements ChineseCharacterTypeBolder
{
	private Stroke[] functionalStroke;
	private int times;

	public FunctinoalBasicBolder()
	{
		this(null, 1);
	}

	public FunctinoalBasicBolder(int times)
	{
		this(null, times);
	}

	public FunctinoalBasicBolder(Stroke[] functionalStroke)
	{
		this(functionalStroke, 1);
	}

	public FunctinoalBasicBolder(Stroke[] functionalStroke, int times)
	{
		this.functionalStroke = functionalStroke;
		this.times = times;
	}

	@Override
	public Stroke getStroke(double width)
	{
		double smallWidth = width;
		if (times > 0)
			smallWidth /= times;
		return new FunctionalBasicStroke(functionalStroke, times, smallWidth);
	}
}
