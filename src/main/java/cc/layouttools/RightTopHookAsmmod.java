package cc.layouttools;

import cc.movabletype.SeprateMovabletype;
import cc.stroketool.Point2DWithVector;

/**
 * 適用於外部部件有右上二邊並且右下有往內勾的活字接合，如「⿴勹日」為「旬」。在接合時，都固定外部活字，並將內部活字固定在左下縮放。
 * <p>
 * 注意：使用前要設定外部活字資字，否則會產生出無法預料的狀況！！
 * 
 * @author Ihc
 */

public class RightTopHookAsmmod extends ZoomAsmmod
{
	/** 內部活字縮放的參考基準點 */
	private Point2DWithVector 縮放基準點;
	/** 縮放控制點在活字最低點往上幾倍的活字粗細半徑 */
	private double 縮放點離最低點的半徑倍率;

	/**
	 * 建立右上下勾接合模組。
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 * @param 縮放點離最低點的半徑倍率
	 *            縮放控制點在活字最低點往上幾倍的活字粗細半徑
	 */
	public RightTopHookAsmmod(MergePieceAdjuster 調整工具, double 縮放點離最低點的半徑倍率)
	{
		super(調整工具);
		this.縮放點離最低點的半徑倍率 = 縮放點離最低點的半徑倍率;
	}

	/**
	 * 設定外部活字資訊
	 * 
	 * @param 外部活字
	 *            接下來變形時所要用的外部活字
	 */
	public void 設定外部活字資訊(SeprateMovabletype 外部活字)
	{
		縮放基準點 = 外部活字.揣上低的點();
		double 外部活字半徑 = 活字寬度();
		縮放基準點.subLocation(0, 外部活字半徑 * 縮放點離最低點的半徑倍率);
		return;
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		temporaryPiece.徙(縮放基準點.getX()
				- temporaryPiece.這馬字範圍().getMinX(), 縮放基準點.getY()
				- temporaryPiece.這馬字範圍().getMaxY());
		return;
	}
}
