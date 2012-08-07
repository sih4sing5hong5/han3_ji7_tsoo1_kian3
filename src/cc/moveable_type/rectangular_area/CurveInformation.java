package cc.moveable_type.rectangular_area;

public class CurveInformation
{
	public Point2DWithVector getLinePoint(double x1, double y1, double x2,
			double y2, double t)
	{
		Point2DWithVector answer = new Point2DWithVector((1.0 - t) * x1 + t
				* x2, (1.0 - t) * y1 + t * y2);
		return answer;
	}

	public Point2DWithVector getQuadPoint(double x1, double y1, double x2,
			double y2, double x3, double y3, double t)
	{
		Point2DWithVector answer = new Point2DWithVector(getQuadPoint(x1, x2,
				x3, t), getQuadPoint(y1, y2, y3, t));
		return answer;
	}

	public Point2DWithVector getCubicPoint(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4, double t)
	{
		Point2DWithVector answer = new Point2DWithVector(getCubicPoint(x1, x2,
				x3, x4, t), getCubicPoint(y1, y2, y3, y4, t));
		return answer;
	}

	public Point2DWithVector getLineSlope(double x1, double y1, double x2,
			double y2, double t)
	{
		Point2DWithVector answer = new Point2DWithVector(x2 - x1, y2 - y1);
		answer.setUnit();
		return answer;
	}

	public Point2DWithVector getQuadSlope(double x1, double y1, double x2,
			double y2, double x3, double y3, double t)
	{
		Point2DWithVector answer = new Point2DWithVector(getQuadSlope(x1, x2,
				x3, t), getQuadSlope(y1, y2, y3, t));
		answer.setUnit();
		return answer;
	}

	public Point2DWithVector getCubicSlope(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4, double t)
	{
		Point2DWithVector answer = new Point2DWithVector(getCubicSlope(x1, x2,
				x3, x4, t), getCubicSlope(y1, y2, y3, y4, t));
		answer.setUnit();
		return answer;
	}

	private double getQuadPoint(double x1, double x2, double x3, double t)
	{
		// System.out.println("qq="+x1+" "+x2+" "+x3+" "+t);
		// System.out.println((2 * t - 2) * x1 + (2 - 4 * t) * x2 + 2 * t * x3);
		double u = 1 - t;
		return u * u * x1 + 2 * u * t * x2 + t * t * x3;
	}

	private double getCubicPoint(double x1, double x2, double x3, double x4,
			double t)
	{
		double u = 1 - t;
		return u * u * u * x1 + 3 * u * u * t * x2 + 3 * u * t * t * x3 + t * t
				* t * x4;
	}

	private double getQuadSlope(double x1, double x2, double x3, double t)
	{
		// System.out.println("qq="+x1+" "+x2+" "+x3+" "+t);
		// System.out.println((2 * t - 2) * x1 + (2 - 4 * t) * x2 + 2 * t * x3);
		return (2 * t - 2) * x1 + (2 - 4 * t) * x2 + 2 * t * x3;
	}

	private double getCubicSlope(double x1, double x2, double x3, double x4,
			double t)
	{
		return 3 * ((-t * t + 2 * t - 1) * x1 + (3 * t * t - 4 * t + 1) * x2
				+ (2 * t - 3 * t * t) * x3 + t * t * x4);
	}
}
