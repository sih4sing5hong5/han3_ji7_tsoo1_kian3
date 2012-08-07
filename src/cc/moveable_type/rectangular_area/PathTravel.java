package cc.moveable_type.rectangular_area;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class PathTravel
{
	private PathAction pathAction;

	public PathTravel(PathAction pathAction)
	{
		this.pathAction = pathAction;
	}

	public void travelOn(GeneralPath generalPath)
	{
		double[] controlPoint = new double[6];
		for (PathIterator pathIterator = generalPath.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			int type = pathIterator.currentSegment(controlPoint);

			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				pathAction.doActionOnMoveTo(controlPoint);
				break;
			case PathIterator.SEG_LINETO:
				pathAction.doActionOnLineTo(controlPoint);
				break;
			case PathIterator.SEG_QUADTO:
				pathAction.doActionOnQuadTo(controlPoint);
				break;
			case PathIterator.SEG_CUBICTO:
				pathAction.doActionOnCubicTo(controlPoint);
				break;
			case PathIterator.SEG_CLOSE:
				pathAction.doActionOnCloseTo(controlPoint);
				break;
			}
		}
	}
}
