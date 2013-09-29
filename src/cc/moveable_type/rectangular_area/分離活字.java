package cc.moveable_type.rectangular_area;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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

	public 分離活字()
	{
		字 = new Vector<平面幾何>();
		原本字體 = new Vector<平面幾何>();
		這馬 = new Rectangle.Double();
		目標 = new Rectangle.Double();
	}

	public 分離活字(平面幾何 幾何)
	{
		this();
		字.add(幾何);
		原本字體.add(幾何);
		字範圍();
	}

	public 分離活字(活字單元 活字)
	{
		this((分離活字) 活字);
	}

	public 分離活字(分離活字 活字)
	{
		字 = new Vector<平面幾何>();
		for (平面幾何 活字的字 : 活字.字)
			字.add(new 平面幾何(活字的字));
		原本字體 = new Vector<平面幾何>(活字.原本字體);
		這馬 = new Rectangle.Double();
		這馬.setRect(活字.這馬);
		目標 = new Rectangle.Double();
		目標.setRect(活字.目標);
	}

	@Override
	public Rectangle2D.Double 目標範圍()
	{
		return 目標;
	}

	private Rectangle2D.Double 圖形範圍()
	{
		if (字.isEmpty())
			return null;
		Rectangle2D.Double 圖形 = new Rectangle2D.Double();
		圖形.setRect(字.firstElement().getBounds2D());
		for (平面幾何 平面 : 字)
			圖形.setRect(圖形.createUnion(平面.getBounds2D()));
		return 圖形;
	}

	// @Override
	// public Rectangle2D.Double 字範圍()
	// {
	// 這馬.setRect(這馬.createUnion(圖形範圍()));
	// return 這馬;
	// }
	@Override
	public Rectangle2D.Double 字範圍()
	{
		for (平面幾何 平面 : 字)
			這馬.setRect(這馬.createUnion(平面.getBounds2D()));
		return 這馬;
	}

	public void 這馬字範圍照圖形範圍()
	{
		這馬.setRect(圖形範圍());
		return;
	}

	public void 切掉字範圍()
	{
		// System.out.println("切掉字範圍");
		// System.out.println(字範圍());
		// System.out.println(目標範圍());
		// System.out.println(圖形範圍());
		Rectangle2D.Double 新位置 = 字範圍();
		Rectangle2D.Double 圖形位置 = 圖形範圍();
		if (圖形位置 != null)
		{
			if (新位置.getHeight() > 目標.getHeight())
			{
				這馬.setRect(這馬.getX(),
						// 這馬.getY(),
						圖形位置.getCenterY() - 目標.getHeight() / 2.0,
						這馬.getWidth(), 目標.getHeight());
			}
			if (新位置.getWidth() > 目標.getWidth())
			{
				這馬.setRect(圖形位置.getCenterX() - 目標.getWidth() / 2.0, 這馬.getY(),
						目標.getWidth(), 這馬.getHeight());
			}
		}
		字範圍();
		// System.out.println(字範圍());
	}

	@Override
	public void 設字範圍(Rectangle2D 目標)
	{
		// TODO Auto-generated method stub
		這馬 = new Rectangle.Double();
		這馬.setRect(目標);
		字範圍();
		return;
	}

	@Override
	public void 重設字範圍()
	{
		// TODO Auto-generated method stub
		這馬 = new Rectangle.Double();
		字範圍();
		return;
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
		這馬.setRect(0, 0, 0, 0);
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
		切掉字範圍();
		徙轉原點();
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
		}// TODO 字範圍
			// Point2D.Double 角 = new Point2D.Double(這馬.getX(), 這馬.getY());
		// 縮放矩陣.transform(角, 角);
		// Point2D.Double 闊 = new Point2D.Double(這馬.getWidth(), 這馬.getHeight());
		// 縮放矩陣.transform(闊, 闊);
		// 這馬.setRect(角.getX(), 角.getY(), 闊.getX(), 闊.getY());
		這馬.setRect(縮放矩陣.createTransformedShape(這馬).getBounds2D());
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
		這馬.setRect(這馬.createUnion(活字物件.字範圍()));
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
