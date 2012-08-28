package cc.adjusting.piece;

import java.awt.geom.GeneralPath;

import cc.moveable_type.rectangular_area.PathAction;
import cc.moveable_type.rectangular_area.PathTravel;
import cc.moveable_type.rectangular_area.ShapeInformation;
import cc.moveable_type.rectangular_area.控制點循訪;

/**
 * 適用於外部部件有右上二邊並且右下有往內勾的活字接合，如「⿴勹日」為「旬」。在接合時，都固定外部活字，並將內部活字固定在左下縮放。
 * 
 * @author Ihc
 */

public class 右上內勾接合模組 extends 縮放接合模組
{
	/**
	 * 建立右上下勾接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public 右上內勾接合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		尋找最低點 記錄 = new 尋找最低點();
		PathTravel pathTravel = new PathTravel(new 控制點循訪(記錄));
		pathTravel.travelOn(new GeneralPath(outsidePiece));// TODO 快取來加速
		temporaryPiece.moveBy(記錄.取得最低點().getX()
				- temporaryPiece.getBounds2D().getMinX(), 記錄.取得最低點().getY()
				- 調整工具.computePieceRadius(outsidePiece) * 4.0
				- temporaryPiece.getBounds2D().getMaxY());
		return;
	}
}
