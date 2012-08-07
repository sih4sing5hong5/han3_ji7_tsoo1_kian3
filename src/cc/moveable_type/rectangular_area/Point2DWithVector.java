package cc.moveable_type.rectangular_area;

import java.awt.geom.Point2D;

public class Point2DWithVector extends Point2D
{
	private double x, y;
	static private final Point2DWithVector origin = new Point2DWithVector();

	public Point2DWithVector()
	{
		x = y = 0.0;
	}

	public Point2DWithVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point2DWithVector(Point2D point2d)
	{
		this(point2d.getX(), point2d.getY());
	}

	@Override
	public double getX()
	{
		return x;
	}

	@Override
	public double getY()
	{
		return y;
	}

	@Override
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
		return;
	}

	public void setLocation(Point2D point2d)
	{
		setLocation(point2d.getX(), point2d.getY());
		return;
	}

	public void addLocation(double x, double y)
	{
		this.x += x;
		this.y += y;
		return;
	}

	public void addLocation(Point2D point2d)
	{
		addLocation(point2d.getX(), point2d.getY());
		return;
	}

	public void subLocation(double x, double y)
	{
		addLocation(-x, -y);
		return;
	}

	public void subLocation(Point2D point2d)
	{
		subLocation(point2d.getX(), point2d.getY());
		return;
	}

	public void setUnit()
	{
		double distance = Point2D.distance(0.0, 0.0, x, y);
		if (distance > 0.0)
		{
			this.x /= distance;
			this.y /= distance;
		}
		return;
	}

	public void rotateRightAngle()

	{
		double a = this.x, b = this.y;
		this.x = -b;
		this.y = a;
		return;
	}

	public void multiple(double scaler)
	{
		this.x *= scaler;
		this.y *= scaler;
		return;
	}

	public double innerProduct(Point2DWithVector point2dWithVector)
	{
		return x * point2dWithVector.x + y * point2dWithVector.y;
	}

	public void multipleByPolarSystem(Point2DWithVector point2dWithVector)
	{
		double a = this.x, b = this.y;
		this.x = a * point2dWithVector.x - b * point2dWithVector.y;
		this.y = a * point2dWithVector.y + b * point2dWithVector.x;
		return;
	}

	public void divideByPolarSystem(Point2DWithVector point2dWithVector)
	{
		if (!point2dWithVector.equals(origin))
		{
			double a = this.x, b = this.y;
			this.x = a * point2dWithVector.x + b * point2dWithVector.y;
			this.y = -a * point2dWithVector.y + b * point2dWithVector.x;
			double distanceSq = origin.distanceSq(point2dWithVector);
			multiple(1.0 / distanceSq);
		}
		return;
	}

	@Override
	public String toString()
	{
		return "[Point2DWithVector,x=" + x + ",y=" + y + "]";
	}

	public boolean areTheSameAs(Point2DWithVector point2dWithVector)
	{
		return areTheSameAs(point2dWithVector.x, point2dWithVector.y);
	}

	public boolean areTheSameAs(double x, double y)
	{
		if (distance(x, y) > getPrecision())
			return false;
		return true;
	}

	public double getLength()
	{
		return distance(origin);
	}

	public double getPrecision()
	{
		return 1e-6;
	}
}
