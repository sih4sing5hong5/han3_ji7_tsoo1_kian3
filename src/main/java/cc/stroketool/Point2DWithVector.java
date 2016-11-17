package cc.stroketool;

import java.awt.geom.Point2D;

/**
 * 二維向量。找不到內建的函式就自己做了～～
 * 
 * @author Ihc
 */
public class Point2DWithVector extends Point2D
{
	/** 甲座標 */
	private double x;
	/** 乙座標 */
	private double y;
	/** 原點 */
	static private final Point2DWithVector origin = new Point2DWithVector();

	/**
	 * 建立二維向量。
	 */
	public Point2DWithVector()
	{
		x = y = 0.0;
	}

	/**
	 * 建立二維向量。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public Point2DWithVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 建立二維向量。
	 * 
	 * @param point2d
	 *            參考座標
	 */
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

	/**
	 * 設定座標。
	 * 
	 * @param point2d
	 *            座標
	 */
	public void setLocation(Point2D point2d)
	{
		setLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 和另一向量相加。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public void addLocation(double x, double y)
	{
		this.x += x;
		this.y += y;
		return;
	}

	/**
	 * 和另一向量相加。
	 * 
	 * @param point2d
	 *            欲相加的向量
	 */
	public void addLocation(Point2D point2d)
	{
		addLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 和另一向量相減。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 */
	public void subLocation(double x, double y)
	{
		addLocation(-x, -y);
		return;
	}

	/**
	 * 和另一向量相減。
	 * 
	 * @param point2d
	 *            欲相減的向量
	 */
	public void subLocation(Point2D point2d)
	{
		subLocation(point2d.getX(), point2d.getY());
		return;
	}

	/**
	 * 變成單位向量。
	 */
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

	/**
	 * 逆時針轉九十度。
	 */
	public void rotateRightAngle()

	{
		double a = this.x, b = this.y;
		this.x = -b;
		this.y = a;
		return;
	}

	/**
	 * 座標放大。
	 * 
	 * @param scaler
	 *            放大倍數
	 */
	public void multiple(double scaler)
	{
		this.x *= scaler;
		this.y *= scaler;
		return;
	}

	/**
	 * 和另一向量內積。
	 * 
	 * @param point2dWithVector
	 *            欲內積的向量
	 * @return 內積值
	 */
	public double innerProduct(Point2DWithVector point2dWithVector)
	{
		return x * point2dWithVector.x + y * point2dWithVector.y;
	}

	/**
	 * 和另一向量相乘。
	 * 
	 * @param point2dWithVector
	 *            欲相乘的向量
	 */
	public void multipleByPolarSystem(Point2DWithVector point2dWithVector)
	{
		double a = this.x, b = this.y;
		this.x = a * point2dWithVector.x - b * point2dWithVector.y;
		this.y = a * point2dWithVector.y + b * point2dWithVector.x;
		return;
	}

	/**
	 * 除以另一向量。
	 * 
	 * @param point2dWithVector
	 *            欲除以的向量
	 */
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

	/**
	 * 給兩向量，判斷是否相同。
	 * 
	 * @param point2dWithVector
	 *            欲與之判斷的向量
	 * @return 向量是否相同
	 */
	public boolean areTheSameAs(Point2DWithVector point2dWithVector)
	{
		return areTheSameAs(point2dWithVector.x, point2dWithVector.y);
	}

	/**
	 * 給兩向量，判斷是否相同。
	 * 
	 * @param x
	 *            甲座標
	 * @param y
	 *            乙座標
	 * @return 向量是否相同
	 */
	public boolean areTheSameAs(double x, double y)
	{
		if (distance(x, y) > getPrecision())
			return false;
		return true;
	}

	/**
	 * 取得向量長度。
	 * 
	 * @return 向量長度
	 */
	public double getLength()
	{
		return distance(origin);
	}

	/**
	 * 取得向量誤差容許度。
	 * 
	 * @return 向量誤差容許度
	 */
	public double getPrecision()
	{
		return 1e-6;
	}
}
