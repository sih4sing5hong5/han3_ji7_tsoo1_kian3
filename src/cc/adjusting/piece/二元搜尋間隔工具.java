package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.分離活字;

/**
 * 利用二元搜尋來組合兩個活字，最後拉開兩個活字兩個寬度，以利事後調整。
 * 
 * @author Ihc
 */
public class 二元搜尋間隔工具 extends 二元搜尋貼合工具
{
	/** 內部活字和外部活字最後的間距 */
	protected double 間隔距離;

	/**
	 * 建立二元搜尋間隔工具
	 * 
	 * @param 間隔距離
	 *            內部活字和外部活字最後的間距
	 */
	public 二元搜尋間隔工具(double 間隔距離)
	{
		this.間隔距離 = 間隔距離;

	}

	@Override
	public void 執行(二元搜尋貼合模組 模組, 分離活字[] 活字物件)
	{
		模組.初始化(活字物件);

		double mininumValue = 模組.下限初始值(), maxinumValue = 模組.上限初始值();
		while (mininumValue + 模組.取得精確度() < maxinumValue)
		{
			double middleValue = 0.5 * (mininumValue + maxinumValue);
			模組.變形處理(middleValue);
			if (模組.活字是否太接近() ^ 模組.太接近時參數變大())
				maxinumValue = middleValue;
			else
				mininumValue = middleValue;
		}

		double 活字寬度 = 模組.活字寬度();

		mininumValue += 活字寬度 * 間隔距離 * 縮放參數(模組);
		if (mininumValue < 模組.下限初始值())
			mininumValue = 模組.下限初始值();
		if (mininumValue > 模組.上限初始值())
			mininumValue = 模組.上限初始值();

		模組.變形處理(mininumValue);
		模組.最後處理();
		return;
	}
}
