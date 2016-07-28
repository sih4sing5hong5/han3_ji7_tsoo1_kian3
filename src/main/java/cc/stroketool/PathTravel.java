package cc.stroketool;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

/**
 * 路徑循訪公用型態。將<code>GeneralPath</code>的每個線段繞個一次。
 * 
 * @author Ihc
 */
public class PathTravel
{
	/** 循訪路徑動作 */
	private PathAction pathAction;

	/**
	 * 建立路徑循訪物件。
	 * 
	 * @param pathAction
	 *            反應動作物件
	 */
	public PathTravel(PathAction pathAction)
	{
		this.pathAction = pathAction;
	}

	/**
	 * 循訪路徑。
	 * 
	 * @param generalPath
	 *            欲循訪的路徑
	 */
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
