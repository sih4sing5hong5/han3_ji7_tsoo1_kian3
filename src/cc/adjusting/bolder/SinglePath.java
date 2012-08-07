package cc.adjusting.bolder;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import cc.moveable_type.rectangular_area.CurveInformation;
import cc.moveable_type.rectangular_area.Point2DWithVector;

public class SinglePath
{
	private int type;
	private Point2DWithVector currentPoint;
	private double[] controlPoint;

	public SinglePath()
	{
		type = PathIterator.SEG_CLOSE;
		currentPoint = new Point2DWithVector();
		controlPoint = new double[6];
	}

	public SinglePath(int type, Point2D currentPoint, double[] controlPoint)
	{
		this.controlPoint = new double[6];
		setPath(type, currentPoint, controlPoint);
	}

	public void addTo(GeneralPath generalPath)
	{
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			generalPath.moveTo(controlPoint[0], controlPoint[1]);
			break;
		case PathIterator.SEG_LINETO:
			generalPath.lineTo(controlPoint[0], controlPoint[1]);
			break;
		case PathIterator.SEG_QUADTO:
			generalPath.quadTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3]);
			break;
		case PathIterator.SEG_CUBICTO:
			generalPath.curveTo(controlPoint[0], controlPoint[1],
					controlPoint[2], controlPoint[3], controlPoint[4],
					controlPoint[5]);
			break;
		case PathIterator.SEG_CLOSE:
			generalPath.closePath();
			break;
		}
	}

	public Point2DWithVector cutHead()
	{
		reverse();
		Point2DWithVector point2dWithVector = cutTail();
		reverse();
		return point2dWithVector;
	}

	public Point2DWithVector cutTail()
	{
		System.out.println("cut~~");
		Point2DWithVector newEnd = null;
		Point2DWithVector tailSlope = getSlope(1.0);
		if (tailSlope != null)
		{
			double newT = 1 - getPrecision()/* / tailSlope.getLength() */;
			// TODO 不一定《1

			Point2DWithVector headSlope = getSlope(0.0);
			System.out.println("newT=" + newT);
			CurveInformation curveInformation = new CurveInformation();
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				break;
			case PathIterator.SEG_LINETO:
				newEnd = curveInformation.getLinePoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						newT);
				controlPoint[0] = newEnd.getX();
				controlPoint[1] = newEnd.getY();
				break;
			case PathIterator.SEG_QUADTO:
				newEnd = curveInformation.getQuadPoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], newT);
				controlPoint[2] = newEnd.getX();
				controlPoint[3] = newEnd.getY();
				// TODO
				// controlPoint[0]＝controlPoint[2]－A*newSlope.x=currentPoint+B*headSlope.x
				// controlPoint[1]＝controlPoint[3]－A*newSlope.y=currentPoint+B*headSlope.y

				// controlPoint[2]-currentPoint=A*newSlope.x+B*headSlope.x
				// controlPoint[3]-currentPoint=A*newSlope.y+B*headSlope.y

				// controlPoint[2]-currentPoint=A*newSlope.x+B*headSlope.x X
				// headSlope.y
				// controlPoint[3]-currentPoint=A*newSlope.y+B*headSlope.y X
				// headSlope.x

				Point2DWithVector newSlope = curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], newT);
				double A = ((controlPoint[2] - currentPoint.getX()) * headSlope
						.getY())
						- (controlPoint[3] - currentPoint.getY())
						* headSlope.getX();
				A /= newSlope.getX() * headSlope.getY() - newSlope.getY()
						* headSlope.getX(); // TODO DIVIDE 0
				controlPoint[0] = controlPoint[2] - A * newSlope.getX();
				controlPoint[1] = controlPoint[3] - A * newSlope.getY();
				break;
			case PathIterator.SEG_CUBICTO:
				newEnd = curveInformation.getCubicPoint(currentPoint.getX(),
						currentPoint.getY(), controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], controlPoint[4],
						controlPoint[5], newT);
				// TODO
				controlPoint[4] = newEnd.getX();
				controlPoint[5] = newEnd.getY();
				break;
			case PathIterator.SEG_CLOSE:
				break;
			}

		}
		return newEnd;
	}

	private void reverse()
	{
		Point2DWithVector tmp = currentPoint;
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			break;
		case PathIterator.SEG_LINETO:
			currentPoint = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			controlPoint[0] = tmp.getX();
			controlPoint[1] = tmp.getY();
			break;
		case PathIterator.SEG_QUADTO:
			currentPoint = new Point2DWithVector(controlPoint[2],
					controlPoint[3]);
			controlPoint[2] = tmp.getX();
			controlPoint[3] = tmp.getY();
			break;
		case PathIterator.SEG_CUBICTO:
			currentPoint = new Point2DWithVector(controlPoint[4],
					controlPoint[5]);
			controlPoint[4] = tmp.getX();
			controlPoint[5] = tmp.getY();
			double t;
			t = controlPoint[0];
			controlPoint[0] = controlPoint[2];
			controlPoint[2] = t;
			t = controlPoint[1];
			controlPoint[1] = controlPoint[3];
			controlPoint[3] = t;
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return;
	}

	/**
	 * @return the type
	 */
	int getType()
	{
		return type;
	}

	/**
	 * @return the controlPoint
	 */
	double[] getControlPoint()
	{
		return controlPoint;
	}

	Point2DWithVector getLastPoint()
	{
		Point2DWithVector point2dWithVector = null;
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			point2dWithVector = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			break;
		case PathIterator.SEG_LINETO:
			point2dWithVector = new Point2DWithVector(controlPoint[0],
					controlPoint[1]);
			break;
		case PathIterator.SEG_QUADTO:
			point2dWithVector = new Point2DWithVector(controlPoint[2],
					controlPoint[3]);
			break;
		case PathIterator.SEG_CUBICTO:
			point2dWithVector = new Point2DWithVector(controlPoint[4],
					controlPoint[5]);
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return point2dWithVector;
	}

	public Point2DWithVector getSlope(double t)
	{
		Point2DWithVector point2dWithVector = null;
		CurveInformation curveInformation = new CurveInformation();
		switch (type)
		{
		case PathIterator.SEG_MOVETO:
			break;
		case PathIterator.SEG_LINETO:
			point2dWithVector = curveInformation.getLineSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], t);
			break;
		case PathIterator.SEG_QUADTO:
			point2dWithVector = curveInformation.getQuadSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], controlPoint[2], controlPoint[3], t);
			break;
		case PathIterator.SEG_CUBICTO:
			point2dWithVector = curveInformation.getCubicSlope(
					currentPoint.getX(), currentPoint.getY(), controlPoint[0],
					controlPoint[1], controlPoint[2], controlPoint[3],
					controlPoint[4], controlPoint[5], t);
			break;
		case PathIterator.SEG_CLOSE:
			break;
		}
		return point2dWithVector;
	}

	/**
	 * @param controlPoint
	 *            the controlPoint to set
	 */
	void setPath(int type, Point2D currentPoint, double[] controlPoint)
	{
		this.type = type;
		this.currentPoint = new Point2DWithVector(currentPoint);
		if (controlPoint != null)
			for (int i = 0; i < 6; ++i)
				this.controlPoint[i] = controlPoint[i];
		return;
	}

	void setPath(int type)
	{
		this.type = type;
		return;
	}

	public double getPrecision()
	{
		return 1e-1;//TODO 參數
	}
}
