package cc.layouttools;

import cc.movabletype.SeprateMovabletype;

/**
 * 用利用二元搜尋來組合兩個活字的模組，定義相關的動作，二元搜尋的上下限，變形方式等等細節。
 * 
 * @author Ihc
 */
public abstract class BisearchPasteAsmMod
{
	/** 使用此模組的調整工具，並使用其自身合併相關函式 */
	protected MergePieceAdjuster 調整工具;

	/**
	 * 建立二元搜尋貼合模組。
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public BisearchPasteAsmMod(MergePieceAdjuster 調整工具)
	{
		this.調整工具 = 調整工具;
	}

	/**
	 * 初始化此模組。
	 * 
	 * @param rectangularAreas
	 *            要調整的物件活字
	 */
	public abstract void 初始化(SeprateMovabletype[] rectangularAreas);

	/**
	 * 取得二元搜尋的初始下限。
	 * 
	 * @return 二元搜尋的初始下限
	 */
	public double 下限初始值()
	{
		return 調整工具.getPrecision();
	}

	/**
	 * 取得二元搜尋的初始上限。
	 * 
	 * @return 二元搜尋的初始上限
	 */

	public abstract double 上限初始值();

	/**
	 * 取得調整活字的最低精準度。
	 * 
	 * @return 調整活字的最低精準度
	 */
	public double 取得精確度()
	{
		return 調整工具.getPrecision();
	}

	/**
	 * 判斷兩活字之間是否太接近。
	 * 
	 * @return 兩活字之間是否太接近
	 */
	public abstract boolean 活字是否太接近();

	/**
	 * 若活字間太接近，參數是否是要調大。
	 * 
	 * @return 參數是否是要調大
	 */
	public abstract boolean 太接近時參數變大();

	/**
	 * 依參數改變活字特性。
	 * 
	 * @param middleValue
	 *            調整用參數
	 */
	public abstract void 變形處理(double middleValue);

	/**
	 * 取得目前活字的寬度
	 * 
	 */
	public double 活字寬度()
	{
		return 調整工具.平均闊度();
	}

	/**
	 * 兩活字接觸的邊界長度，用來判斷是否不適合太接近，計算相斥值。
	 * 
	 * @return 兩活字接觸的邊界長度
	 */
	public abstract double 接觸邊長();

	/**
	 * 計算兩活字是否適合太接近，相斥值越大，距離要越遠越好
	 * 
	 * @return 兩活字相斥值
	 */
	public abstract double 活字相斥值();

	/** 最後收尾的設定以及調整 */
	public abstract void 最後處理();

	/**
	 * 取得經由此模組調整後活字物件
	 * 
	 * @return 調整後活字物件
	 */
	public abstract SeprateMovabletype[] 取得調整後活字物件();
}
