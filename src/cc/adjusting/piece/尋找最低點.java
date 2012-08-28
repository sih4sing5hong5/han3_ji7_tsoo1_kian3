package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.Point2DWithVector;
import cc.moveable_type.rectangular_area.控制點反應動作;

public class 尋找最低點 implements 控制點反應動作
{
	private Point2DWithVector 最低點;

	public 尋找最低點()
	{
		最低點 = null;
	}

	@Override
	public void 新控制點(Point2DWithVector 控制點)
	{
		if (最低點 == null)
		{
			最低點 = 控制點;
		}
		else if (控制點.getY() > 最低點.getY())
			最低點 = 控制點;
		return;
	}

	/**
	 * 取得最低點
	 * 
	 * @return 最低點
	 */
	public Point2DWithVector 取得最低點()
	{
		return 最低點;
	}

	/**
	 * 是否存在最低點
	 * 
	 * @return 是否存在最低點
	 */
	public boolean 是否存在最低點()
	{
		return 最低點 != null;
	}

}
