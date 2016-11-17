package cc.layouttools;

import cc.movabletype.SeprateMovabletype;

/**
 * 利用二元搜尋來組合兩個活字，並且依據兩個活字之間筆劃的關係，調整兩活字的間距。
 * 
 * @author Ihc
 */
public class BisearchPasteAssembler
{

	// public BisearchPasteAssembler()
	// {
	// this.模組 = null;
	// }
	//
	// /**
	// * 建立二元搜尋貼合工具
	// *
	// * @param 模組
	// * 要執行的二元搜尋貼合模組
	// * @throws IllegalArgumentException
	// * 若模組為null，則丟出此例外
	// */
	// public BisearchPasteAssembler(BisearchPasteAsmMod 模組) throws IllegalArgumentException
	// {
	// this.模組 = 模組;
	// if (模組 == null)
	// throw new IllegalArgumentException();
	// }

	/**
	 * 用初使化設定的模組，來產生調整後的各活字物件
	 * 
	 * @param 模組
	 *            要執行的二元搜尋貼合模組
	 * @param 活字物件
	 *            要調整的活字物件
	 */
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
		模組.變形處理(mininumValue - 活字寬度 * 2.6 * 縮放參數(模組));

		double 活字相斥值 = 模組.活字相斥值();
		// TODO 人工參數
		if (活字相斥值 > 1.6)
			mininumValue += 活字寬度 * 3.0 * 縮放參數(模組);
		else if (活字相斥值 > 0.8)
			mininumValue += 0.0;
		else
			mininumValue += -活字寬度 * 1.2 * 縮放參數(模組);

		if (mininumValue < 模組.下限初始值())
			mininumValue = 模組.下限初始值();
		if (mininumValue > 模組.上限初始值())
			mininumValue = 模組.上限初始值();

		模組.變形處理(mininumValue);
		模組.最後處理();
		return;
	}

	/**
	 * 判斷模組在縮放時，參數要增加還是要減少。
	 * 
	 * @param 模組
	 *            要執行的二元搜尋貼合模組
	 * @return 若是參數和大小成正比就回傳1.0，若成反比回傳-1.0
	 */
	protected double 縮放參數(BisearchPasteAsmMod 模組)
	{
		if (模組.太接近時參數變大())
			return 1.0;
		return -1.0;
	}
}
