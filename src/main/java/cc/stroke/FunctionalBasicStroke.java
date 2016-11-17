package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;

/**
 * 多功能筆劃加寬工具。可以在加寬前對物件做一些處理，而且分多次加寬，減少問題。
 * 
 * @author Ihc
 */
public class FunctionalBasicStroke implements Stroke
{
	/** 筆劃加寬前的預先處理 */
	private Stroke[] functionalStroke;
	/** 筆劃加寬的分段數 */
	int times;
	/** 筆劃加寬工貝 */
	SolidBasicStroke solidBasicStroke;

	/**
	 * 建立多功能筆劃加寬器。
	 * 
	 * @param functionalStroke
	 *            筆劃加寬前的預先處理
	 * @param times
	 *            筆劃加寬的分段數
	 * @param width
	 *            筆劃加寬工貝
	 */
	public FunctionalBasicStroke(Stroke[] functionalStroke, int times,
			double width)
	{
		this.functionalStroke = functionalStroke;
		this.times = times;
		solidBasicStroke = new SolidBasicStroke(width);
	}

	@Override
	public Shape createStrokedShape(Shape p)
	{
		if (functionalStroke != null)
		{
			for (int i = 0; i < functionalStroke.length; ++i)
				p = functionalStroke[i].createStrokedShape(p);
		}
		for (int i = 0; i < times; ++i)
			p = solidBasicStroke.createStrokedShape(p);
		return p;
	}
}
