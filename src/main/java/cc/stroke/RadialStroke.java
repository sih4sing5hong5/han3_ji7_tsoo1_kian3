package cc.stroke;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Vector;

import cc.stroketool.CurveInformation;
import cc.stroketool.Point2DWithVector;
import cc.stroketool.ShapeAnalyst;

/**
 * 物件活字邊長外移筆劃加寬工具。此工具仍有問題，每個邊往外調，不顧及前後的邊，字會不平滑。而且尚有許多問題尚末解決。因為時間因素放棄，
 * 待日後系統完成後再行考慮。
 * 
 * @author Ihc
 */
@Deprecated
public class RadialStroke implements Stroke
{
	/** 要移動的距離 */
	private final double width;

	/**
	 * 建立邊長外移筆劃加寬工具
	 * 
	 * @param width
	 *            要移動的距離
	 */
	public RadialStroke(double width)
	{
		this.width = width;
	}

	@Override
	public Shape createStrokedShape(Shape shape)
	{
		return createStrokedShape(new Area(shape));// Area保證他的順逆時針
	}

	/**
	 * 加寬傳入物件。把每段路徑切開，去處理。
	 * 
	 * @param area
	 *            物件
	 * @return 加寬完的物件
	 */
	public Shape createStrokedShape(Area area)
	{
		Area sum = new Area();// TODO change name
		double[] controlPoint = new double[6];
		Vector<Point2DWithVector> apexMoveVector = new Vector<Point2DWithVector>();
		// SimplePolygon simplePolygon = new SimplePolygon();
		CurveInformation curveInformation = new CurveInformation();
		GeneralPath generalPath = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		Point2D.Double startPoint = null;
		for (PathIterator pathIterator = area.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			int type = pathIterator.currentSegment(controlPoint);

			System.out.println("main=" + type + " " + controlPoint[0] + " "
					+ controlPoint[1] + " " + controlPoint[2] + " "
					+ controlPoint[3] + " " + controlPoint[4] + " "
					+ controlPoint[5]);
			Point2DWithVector currentPoint = null;
			if (generalPath.getCurrentPoint() != null)
				currentPoint = new Point2DWithVector(
						generalPath.getCurrentPoint());
			Point2DWithVector previous = new Point2DWithVector(), next = new Point2DWithVector();
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				apexMoveVector.add(new Point2DWithVector());
				generalPath.moveTo(controlPoint[0], controlPoint[1]);
				startPoint = new Point2D.Double(controlPoint[0],
						controlPoint[1]);
				break;
			case PathIterator.SEG_LINETO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				if (currentPoint.areTheSameAs(controlPoint[0], controlPoint[1]))
					break;
				previous.setLocation(curveInformation.getLineSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], 0.0));
				next.setLocation(curveInformation.getLineSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], 1.0));
				System.out.println("pre=" + previous);
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.lineTo(controlPoint[0], controlPoint[1]);
				break;
			case PathIterator.SEG_QUADTO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				previous.setLocation(curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], 0.0));
				next.setLocation(curveInformation.getQuadSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], 1.0));
				System.out.println("pre=" + previous);
				System.out.println("next=" + next);
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.quadTo(controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3]);
				break;
			case PathIterator.SEG_CUBICTO:
				// simplePolygon.addPoint(controlPoint[0], controlPoint[1]);
				// simplePolygon.addPoint(controlPoint[2], controlPoint[3]);
				// simplePolygon.addPoint(controlPoint[4], controlPoint[5]);
				// get斜率*2
				currentPoint.setLocation(generalPath.getCurrentPoint());
				previous.setLocation(curveInformation.getCubicSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], controlPoint[4], controlPoint[5], 0.0));
				next.setLocation(curveInformation.getCubicSlope(
						currentPoint.getX(), currentPoint.getY(),
						controlPoint[0], controlPoint[1], controlPoint[2],
						controlPoint[3], controlPoint[4], controlPoint[5], 1.0));
				apexMoveVector.lastElement().addLocation(previous);
				apexMoveVector.add(next);
				generalPath.curveTo(controlPoint[0], controlPoint[1],
						controlPoint[2], controlPoint[3], controlPoint[4],
						controlPoint[5]);
				break;
			case PathIterator.SEG_CLOSE:
				if (startPoint.equals(generalPath.getCurrentPoint()))
				{
					apexMoveVector.lastElement().addLocation(
							apexMoveVector.firstElement());
					apexMoveVector.firstElement().setLocation(
							apexMoveVector.lastElement());
				}
				else
				{
					currentPoint.setLocation(generalPath.getCurrentPoint());
					previous.setLocation(curveInformation.getLineSlope(
							currentPoint.getX(), currentPoint.getY(),
							startPoint.getX(), startPoint.getY(), 0.0));
					next.setLocation(curveInformation.getLineSlope(
							currentPoint.getX(), currentPoint.getY(),
							startPoint.getX(), startPoint.getY(), 1.0));
					next.addLocation(apexMoveVector.firstElement());
					apexMoveVector.lastElement().addLocation(previous);
					apexMoveVector.add(next);
					apexMoveVector.firstElement().setLocation(next);
				}
				for (int i = 0; i < apexMoveVector.size(); ++i)
				{
					apexMoveVector.elementAt(i).setUnit();
					apexMoveVector.elementAt(i).rotateRightAngle();
				}
				generalPath.closePath();
				System.out.println("＠＠1");
				new ShapeAnalyst(generalPath);
				System.out.println("＠＠2");

				Area modify = new Area(getBold(generalPath, apexMoveVector));
				sum.add(modify);

				generalPath.reset();
				break;
			}
			if (apexMoveVector.size() > 3)
				System.out.println("apex[3]=" + apexMoveVector.elementAt(3));
		}
		return sum;
	}

	/**
	 * 給一段路徑，把筆劃加寬
	 * 
	 * @param generalPath
	 *            一段路徑
	 * @param apexMoveVector
	 *            每組頭尾控制點要移動的方向
	 * @return 加寬後的路徑
	 */
	private GeneralPath getBold(GeneralPath generalPath,
			Vector<Point2DWithVector> apexMoveVector)
	{
		for (int i = 0; i < apexMoveVector.size(); ++i)
		{
			apexMoveVector.elementAt(i).multiple(width);
			System.out.println("i=" + i + " "
					+ apexMoveVector.elementAt(i).getX() + " "
					+ apexMoveVector.elementAt(i).getY());
		}
		double[] controlPoint = new double[6];
		int index = 0;
		GeneralPath target = new GeneralPath();
		// target.moveTo(1, 2);
		// target.moveTo(2, 3);
		System.out.println("AA1");
		Point2DWithVector originCurrentPoint = new Point2DWithVector();
		Point2DWithVector targetCurrentPoint = new Point2DWithVector();
		for (PathIterator pathIterator = generalPath.getPathIterator(null); !pathIterator
				.isDone(); pathIterator.next())
		{
			// System.out.println("fun");
			int type = pathIterator.currentSegment(controlPoint);
			System.out.println("fun=" + type + " " + controlPoint[0] + " "
					+ controlPoint[1] + " " + controlPoint[2] + " "
					+ controlPoint[3] + " " + controlPoint[4] + " "
					+ controlPoint[5]);
			// System.out.println("P1");
			// ShapeAnalyst shapeAnalyst = new ShapeAnalyst(target);
			// System.out.println("P2");
			// target.getCurrentPoint();
//			Point2DWithVector previous = new Point2DWithVector(), next = new Point2DWithVector();
			Point2DWithVector targetDifference;
			Point2DWithVector originDifference;
			switch (type)
			{
			case PathIterator.SEG_MOVETO:
				Point2DWithVector move = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				move.addLocation(controlPoint[0], controlPoint[1]);
				target.moveTo((float) move.getX(), (float) move.getY());
				System.out.println("moveto=" + move.getX() + ' ' + move.getY());
				originCurrentPoint
						.setLocation(controlPoint[0], controlPoint[1]);
				targetCurrentPoint.setLocation(move);
				break;
			case PathIterator.SEG_LINETO:
				Point2DWithVector line = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				line.addLocation(controlPoint[0], controlPoint[1]);
				System.out.println("lineto=" + line.getX() + " " + line.getY());
				target.lineTo(line.getX(), line.getY());
				originCurrentPoint
						.setLocation(controlPoint[0], controlPoint[1]);
				targetCurrentPoint.setLocation(line);
				break;
			case PathIterator.SEG_QUADTO:
				Point2DWithVector quad = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				quad.addLocation(controlPoint[2], controlPoint[3]);
				originDifference = new Point2DWithVector(controlPoint[2],
						controlPoint[3]);
				originDifference.subLocation(originCurrentPoint);
				targetDifference = new Point2DWithVector(quad);
				targetDifference.subLocation(targetCurrentPoint);
				Point2DWithVector q0 = new Point2DWithVector(controlPoint[0],
						controlPoint[1]);
				Point2DWithVector q0Difference = new Point2DWithVector(q0);
				q0Difference.subLocation(originCurrentPoint);

				System.out.println("q0=" + q0);
				System.out.println("q0Difference=" + q0Difference);
				System.out.println("targetDifference=" + targetDifference);
				System.out.println("originDifference=" + originDifference);
				q0Difference.multipleByPolarSystem(targetDifference);
				// System.out.println("new1 q0Difference="+q0Difference);
				q0Difference.divideByPolarSystem(originDifference);

				System.out.println("new2 q0Difference=" + q0Difference);
				q0.setLocation(originCurrentPoint);
				q0.addLocation(q0Difference);

				System.out.println("quadto=" + q0.getX() + " " + q0.getY()
						+ " " + quad.getX() + " " + quad.getY());
				//
				q0.setLocation(apexMoveVector.elementAt(index - 1));
				q0.addLocation(apexMoveVector.elementAt(index));
				q0.multiple(0.5);
				q0.addLocation(controlPoint[0], controlPoint[1]);
				//
				target.quadTo(q0.getX(), q0.getY(), quad.getX(), quad.getY());

				originCurrentPoint
						.setLocation(controlPoint[2], controlPoint[3]);
				targetCurrentPoint.setLocation(quad);
				break;
			case PathIterator.SEG_CUBICTO:
				Point2DWithVector cubic = new Point2DWithVector(
						apexMoveVector.elementAt(index));
				cubic.addLocation(controlPoint[4], controlPoint[5]);
				originDifference = new Point2DWithVector(controlPoint[4],
						controlPoint[5]);
				originDifference.subLocation(originCurrentPoint);
				targetDifference = new Point2DWithVector(cubic);
				targetDifference.subLocation(targetCurrentPoint);
				Point2DWithVector c0 = new Point2DWithVector(controlPoint[0],
						controlPoint[1]);
				Point2DWithVector c0Difference = new Point2DWithVector(c0);
				Point2DWithVector c1 = new Point2DWithVector(controlPoint[2],
						controlPoint[3]);
				Point2DWithVector c1Difference = new Point2DWithVector(c1);
				c0Difference.subLocation(originCurrentPoint);
				c1Difference.subLocation(originCurrentPoint);

				c0Difference.multipleByPolarSystem(targetDifference);
				c0Difference.divideByPolarSystem(originDifference);
				c1Difference.multipleByPolarSystem(targetDifference);
				c1Difference.divideByPolarSystem(originDifference);
				c0.setLocation(originCurrentPoint);
				c0.addLocation(c0Difference);
				c1.setLocation(originCurrentPoint);
				c1.addLocation(c1Difference);
				while (c0.equals(c0))
					System.err.println("XDDDDDDD");

				target.curveTo(c0.getX(), c0.getY(), c1.getX(), c1.getY(),
						cubic.getX(), cubic.getY());

				originCurrentPoint
						.setLocation(controlPoint[4], controlPoint[5]);
				targetCurrentPoint.setLocation(cubic);
				break;
			case PathIterator.SEG_CLOSE:
				target.closePath();
				break;
			}
			index++;
		}
		System.out.println("AA2");
		// System.out.println("M1");
		// ShapeAnalyst shapeAnalyst = new ShapeAnalyst(target);
		// System.out.println("M2");
		return target;
	}
}
