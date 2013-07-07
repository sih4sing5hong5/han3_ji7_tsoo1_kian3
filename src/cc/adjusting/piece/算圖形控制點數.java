package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.Point2DWithVector;
import cc.moveable_type.rectangular_area.控制點反應動作;

/**
 * 找出<code>GeneralPath</code>中最低（Y值最大）控制點的控制點反應動作。
 * 
 * @author Ihc
 */
public class 算圖形控制點數 implements 控制點反應動作
{
	/** 目前所找到的最低控制點 */private int cnt=0;

	/** 建立尋找最低點的反應物件。 */
	public 算圖形控制點數()
	{cnt=0;
	}

	/** 清空紀錄並重設。 */
	public void 重設()
	{
		cnt=0;
	}

	@Override
	public void 新控制點(Point2DWithVector 控制點)
	{cnt++;
	}

	/**
	 * 取得最低點
	 * 
	 * @return 最低點
	 */
	public int 取得最低點()
	{
		return cnt;
	}

}
