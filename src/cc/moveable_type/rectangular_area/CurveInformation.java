package cc.moveable_type.rectangular_area;

public class CurveInformation
{
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

	private double getQuadSlope(double x1, double x2, double x3, double t)
	{
//		System.out.println("qq="+x1+" "+x2+" "+x3+" "+t);
//		System.out.println((2 * t - 2) * x1 + (2 - 4 * t) * x2 + 2 * t * x3);
		return (2 * t - 2) * x1 + (2 - 4 * t) * x2 + 2 * t * x3;
	}

	private double getCubicSlope(double x1, double x2, double x3, double x4,
			double t)
	{
		return 3 * ((-t * t + 2 * t - 1) * x1 + (3 * t * t - 4 * t + 1) * x2
				+ (2 * t - 3 * t * t) * x3 + t * t * x4);
	}
}
