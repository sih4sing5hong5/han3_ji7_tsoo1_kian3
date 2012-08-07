package cc.adjusting.bolder;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * 
 * @author Ihc
 */
public class FunctionalBasicStroke implements Stroke
{
	private Stroke[] functionalStroke;
	int times;
	BasicStroke basicStroke;

	public FunctionalBasicStroke(Stroke[] functionalStroke, int times,
			double width)
	{
		this.functionalStroke = functionalStroke;
		this.times = times;
		basicStroke = new BasicStroke((float) width);
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
			p = basicStroke.createStrokedShape(p);
		return p;
	}

}
