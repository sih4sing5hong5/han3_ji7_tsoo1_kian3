package cc.layouttools;

import cc.stroketool.Point2DWithVector;
import cc.stroketool.ControlPointReaction;

/**
 * 找出<code>GeneralPath</code>中最低（Y值最大）控制點的控制點反應動作。
 * 
 * @author Ihc
 */
public class FindLowestPoint implements ControlPointReaction
{
	/** 目前所找到的最低控制點 */
	private Point2DWithVector 最低點;

	/** 建立尋找最低點的反應物件。 */
	public FindLowestPoint()
	{
		最低點 = null;
	}

	/** 清空紀錄並重設。 */
	public void 重設()
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
	 * 是否存在最低點。
	 * 
	 * @return 是否存在最低點
	 */
	public boolean 是否存在最低點()
	{
		return 最低點 != null;
	}
}
