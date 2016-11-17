package cc.layouttools;

import cc.movabletype.SeprateMovabletype;

/**
 * 利用二元搜尋來組合兩個活字，最後拉開兩個活字兩個寬度，以利事後調整。
 * 
 * @author Ihc
 */
public class BisearchPasteSpacingTool extends BisearchPasteAssembler
{
	/** 內部活字和外部活字最後的間距 */
	protected double 間隔距離;

	/**
	 * 建立二元搜尋間隔工具
	 * 
	 * @param 間隔距離
	 *            內部活字和外部活字最後的間距
	 */
	public BisearchPasteSpacingTool(double 間隔距離)
	{
		this.間隔距離 = 間隔距離;

	}

	@Override
	public void 執行(BisearchPasteAsmMod 模組, SeprateMovabletype[] 活字物件)
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
