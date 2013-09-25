package cc.moveable_type.rectangular_area;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.security.InvalidParameterException;
import java.util.Vector;

public class 分離活字 implements 活字單元
{
	/** 目前字體大細，所在 */
	private Vector<平面幾何> 字;
	/** 為著保留字體闊，若維護矩陣顛倒愛去顧平移 */
	private Vector<平面幾何> 原本字體;
	/** 這馬的範圍，是為著閃避「一字問題」 */
	private Rectangle2D.Double 這馬;
	/** 目標範圍，上尾印的時陣愛看印偌大 */
	private Rectangle2D.Double 目標;

	@Override
	public Rectangle2D.Double 目標範圍()
	{
		return 目標;
	}

	@Override
	public Rectangle2D.Double 字範圍()
	{
		for (平面幾何 平面 : 字)
			這馬.setRect(這馬.createUnion(平面.getBounds2D()));
		return 這馬;
	}

	@Override
	public void 設目標範圍(Rectangle2D 目標)
	{
		this.目標.setRect(目標);
	}

	@Override
	public void 設目標範圍所在(double x, double y)
	{
		this.目標.setRect(x, y, this.目標.getWidth(), this.目標.getHeight());
	}

	@Override
	public void 設目標範圍大細(double width, double height)
	{
		this.目標.setRect(this.目標.getX(), this.目標.getY(), width, height);
	}

	@Override
	public void 圖形重設()
	{
		字.clear();
		// for (平面幾何 平面 : 字)
		// {
		// 平面.圖形重設();
		// }
		return;
	}

	@Override
	public void 重設並組合活字(活字單元[] 活字物件)
	{
		圖形重設();
		for (活字單元 活字 : 活字物件)
		{
			if (活字 instanceof 分離活字)
				合併活字((分離活字) 活字);
			else
				throw new InvalidParameterException();
		}
	}

	@Override
	public void 徙(double x, double y)
	{
		這馬.setRect(這馬.getX() + x, 這馬.getY() + y, 這馬.getWidth(), 這馬.getHeight());
		for (平面幾何 幾何 : 字)
		{
			幾何.徙(x, y);
		}
		return;
	}

	@Override
	public void 徙轉原點()
	{
		Rectangle2D.Double 位移範圍 = 字範圍();
		徙(-位移範圍.getX(), -位移範圍.getY());
		return;
	}

	@Override
	public void 縮放(AffineTransform 縮放矩陣)
	{
		for (平面幾何 幾何 : 字)
		{
			幾何.縮放(縮放矩陣);
		}
		return;
	}

	@Override
	public void 合併活字(活字單元 活字物件)
	{
		合併活字((分離活字) 活字物件);
	}

	public void 合併活字(分離活字 活字物件)
	{
		字.addAll(活字物件.字);
		原本字體.addAll(活字物件.原本字體);
		return;
	}

	@Override
	public void 減去活字(活字單元 活字物件)
	{
		減去活字((分離活字) 活字物件);
	}

	public void 減去活字(分離活字 活字物件)
	{
		for (平面幾何 幾何 : 字)
			for (平面幾何 別个幾何 : 活字物件.字)
			{
				幾何.減去活字(別个幾何);
			}
		return;
	}

	@Override
	public void 畫佇(Graphics2D 布)
	{
		for (平面幾何 幾何 : 字)
			布.draw(幾何);
		return;
	}

	@Override
	public Point2DWithVector 揣上低的點()
	{
		Point2DWithVector 上低的點 = null;
		for (平面幾何 幾何 : 字)
		{
			Point2DWithVector 這个低點 = 幾何.揣上低的點();
			if (上低的點 == null || 上低的點.getY() > 這个低點.getY())
				上低的點 = 這个低點;
		}
		return 上低的點;
	}

	@Override
	public 平面幾何 目前的字體()
	{
		平面幾何 字體結果 = new 平面幾何();
		for (平面幾何 幾何 : 字)
			字體結果.合併活字(幾何);
		return 字體結果;
	}
}
