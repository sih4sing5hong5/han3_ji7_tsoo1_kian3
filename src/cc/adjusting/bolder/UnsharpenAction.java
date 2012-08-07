package cc.adjusting.bolder;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import cc.moveable_type.rectangular_area.PathAction;
import cc.moveable_type.rectangular_area.Point2DWithVector;

public class UnsharpenAction implements PathAction
{
	private GeneralPath generalPath;
	private SinglePath previousPath;

	public UnsharpenAction(int rule)
	{
		generalPath = new GeneralPath(rule);
		previousPath = new SinglePath();
	}

	@Override
	public void doActionOnMoveTo(double[] controlPoint)
	{
		// generalPath.moveTo(controlPoint[0], controlPoint[1]);
		previousPath.setPath(PathIterator.SEG_MOVETO, new Point2DWithVector(),
				controlPoint);
		return;
	}

	@Override
	public void doActionOnLineTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			System.out.println("!");
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
			{
				System.out.println("!!");
				return;
			}
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_LINETO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnQuadTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
					&& currentPoint.areTheSameAs(controlPoint[2],
							controlPoint[3]))
				return;
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_QUADTO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnCubicTo(double[] controlPoint)
	{
		if (previousPath.getLastPoint() != null)
		{
			Point2DWithVector currentPoint = previousPath.getLastPoint();
			if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1])
					&& currentPoint.areTheSameAs(controlPoint[2],
							controlPoint[3])
					&& currentPoint.areTheSameAs(controlPoint[4],
							controlPoint[5]))
				return;
		}
		SinglePath currentPath = new SinglePath(PathIterator.SEG_CUBICTO,
				previousPath.getLastPoint(), controlPoint);
		checkAngleAndAddPath(currentPath);
		return;
	}

	@Override
	public void doActionOnCloseTo(double[] controlPoint)
	{
		previousPath.addTo(generalPath);
		previousPath.setPath(PathIterator.SEG_CLOSE);
		generalPath.closePath();
		return;
	}

	public GeneralPath getCurrnetPath()
	{
		return generalPath;
	}

	private void checkAngleAndAddPath(SinglePath currentPath)
	{
		Point2DWithVector previousSlope = previousPath.getSlope(1.0);
		Point2DWithVector currentSlope = currentPath.getSlope(0.0);
		System.out.println("@@ " + previousSlope + " " + currentSlope);
		if (previousSlope != null)
		{
			previousSlope.setUnit();
			currentSlope.setUnit();
			if (previousSlope.innerProduct(currentSlope) < 0.9)
			{
				System.out.println("@@");
				Point2DWithVector apex = previousPath.getLastPoint();
				previousPath.cutTail();
				Point2DWithVector tail = currentPath.cutHead();
				previousPath.addTo(generalPath);
				generalPath.quadTo(apex.getX(), apex.getY(), tail.getX(),
						tail.getY());
				// previousSlop
			}
			else
				previousPath.addTo(generalPath);
		}
		else
			previousPath.addTo(generalPath);
		previousPath = currentPath;
		return;
	}
}
