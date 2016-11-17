package cc.stroke;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import cc.stroketool.CurveInformation;
import cc.stroketool.Point2DWithVector;

/**
 * 路徑線段。並包含相關處理函式。
 * 
 * @author Ihc
 */
public class SinglePath
{
	/** 線段型態 */
	private int type;
	/** 線段起始點 */
	private Point2DWithVector currentPoint;
	/** 線段控制點 */
	private double[] controlPoint;

	/** 建立線段物件 */
	public SinglePath()
	{
		type = PathIterator.SEG_CLOSE;
		currentPoint = new Point2DWithVector();
		controlPoint = new double[6];
	}

	/**
	 * 建立線段物件。
	 * 
	 * @param type
	 *            線段型態
	 * @param currentPoint
	 *            線段起始點
	 * @param controlPoint
	 *            線段控制點
	 */
	public SinglePath(int type, Point2D currentPoint, double[] controlPoint)
	{
		this.controlPoint = new double[6];
		setPath(type, currentPoint, controlPoint);
	}

	/**
	 * 把線段加到路徑中。
	 * 
	 * @param generalPath
	 *            目標路徑
	 */
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

	/**
	 * 將線段前部份切掉一小部份以利路徑圓滑工具使用。
	 * 
	 * @return 新的啟始點
	 */
	public Point2DWithVector cutHead()
	{
		reverse();
		Point2DWithVector point2dWithVector = cutTail();
		reverse();
		return point2dWithVector;
	}

	/**
	 * 將線段後部份切掉一小部份以利路徑圓滑工具使用。
	 * 
	 * @return 新的結束點
	 */
	public Point2DWithVector cutTail()
	{
		System.out.println("cut~~");
		Point2DWithVector newEnd = null;
		Point2DWithVector tailSlope = getSlope(1.0);
		if (tailSlope != null)
		{
			double newT = 1 - getLengthRatio()/* / tailSlope.getLength() */;
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

	/** 將線段前後點順序顛倒。 */
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
	 * 取得線段型態。
	 * 
	 * @return 線段型態
	 */
	int getType()
	{
		return type;
	}

	/**
	 * 取得線段控制點。
	 * 
	 * @return 線段控制點
	 */
	double[] getControlPoint()
	{
		return controlPoint;
	}

	/**
	 * 取得線段最後一個控制點。
	 * 
	 * @return 最後一個控制點
	 */
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

	/**
	 * 取得線段該點斜率。
	 * 
	 * @param t
	 *            參數
	 * @return 線段斜率
	 */
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
	 * 設定線段屬性。
	 * 
	 * @param type
	 *            線段控制點
	 * @param currentPoint
	 *            線段啟始點
	 * @param controlPoint
	 *            線段控制點
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

	/**
	 * 設定線段型態。
	 * 
	 * @param type
	 *            線段型態
	 */
	void setPath(int type)
	{
		this.type = type;
		return;
	}

	/**
	 * 取得圓滑長度比例。
	 * 
	 * @return 圓滑長度比例
	 */
	public double getLengthRatio()
	{
		return 1e-1;// TODO 參數
	}
}
