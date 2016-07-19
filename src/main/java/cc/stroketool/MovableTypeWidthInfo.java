package cc.stroketool;

/**
 * 儲存活字寬度相關資訊，方便固定寬度等處理。
 * 
 * @author Ihc
 */
class MovableTypeWidthInfo
{
	/** 活字粗細係數 */
	protected double 活字粗細係數;

	/**
	 * 建立活字寬度資訊
	 * 
	 * @param 活字粗細係數
	 *            調整工具計算的活字粗細係數
	 */
	MovableTypeWidthInfo(double 活字粗細係數)
	{
		this.活字粗細係數 = 活字粗細係數;
	}

	/**
	 * 取得活字粗細係數。
	 * 
	 * @return 活字粗細係數
	 */
	double 取得活字粗細係數()
	{
		return 活字粗細係數;
	}

}
