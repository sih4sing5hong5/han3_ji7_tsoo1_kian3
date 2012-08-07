package cc.adjusting.bolder;

import java.awt.geom.GeneralPath;

import cc.moveable_type.rectangular_area.PathAction;
import cc.moveable_type.rectangular_area.Point2DWithVector;

public class SimplifyAction implements PathAction
{
	private GeneralPath generalPath;

	public SimplifyAction(int rule)
	{
		generalPath = new GeneralPath(rule);
	}

	@Override
	public void doActionOnMoveTo(double[] controlPoint)
	{
		generalPath.moveTo(controlPoint[0], controlPoint[1]);
		return;
	}

	@Override
	public void doActionOnLineTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		System.out.println(currentPoint + " " + controlPoint[0] + " "
				+ controlPoint[1]);
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
			generalPath.lineTo(controlPoint[0], controlPoint[1]);
		else
			System.out.println("OH");
		return;
	}

	@Override
	public void doActionOnQuadTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
				|| !currentPoint.areTheSameAs(controlPoint[2], controlPoint[3]))
			generalPath.quadTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3]);
		return;
	}

	@Override
	public void doActionOnCubicTo(double[] controlPoint)
	{
		Point2DWithVector currentPoint = new Point2DWithVector(
				generalPath.getCurrentPoint());
		if (!currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
				|| !currentPoint.areTheSameAs(controlPoint[2], controlPoint[3])
				|| !currentPoint.areTheSameAs(controlPoint[4], controlPoint[5]))
			generalPath.curveTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3], controlPoint[4],
					controlPoint[5]);
		return;
	}

	@Override
	public void doActionOnCloseTo(double[] controlPoint)
	{
		generalPath.closePath();
		return;
	}

	public GeneralPath getCurrnetPath()
	{
		return generalPath;
	}
}
