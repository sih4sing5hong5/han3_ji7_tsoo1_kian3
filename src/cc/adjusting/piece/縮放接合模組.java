package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.活字單元;

/**
 * 適用於靠縮放合併的模組，若是兩活字是靠縮放比例來當參數調整，就可適用此型態。
 * 
 * @author Ihc
 */
public abstract class 縮放接合模組 extends 二元搜尋貼合模組
{
	/** 外部的活字物件 */
	protected 活字單元 outsidePiece;
	/** 內部的活字物件 */
	protected 活字單元 insidePiece;
	/** 暫存變化後的內部活字物件 */
	protected 活字單元 temporaryPiece;

	/**
	 * 建立縮放接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具 ，並使用其自身合併相關函式
	 */
	public 縮放接合模組(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 初始化(活字單元[] rectangularAreas)
	{
		outsidePiece = rectangularAreas[0];
		// insidePiece = 調整工具.getPieceWithSquareTerritory(rectangularAreas[1]);
		insidePiece = (rectangularAreas[1]);
		return;
	}

	@Override
	public double 上限初始值()
	{
		return insidePiece.字範圍().getHeight();
	}

	@Override
	public void 變形處理(double middleValue)
	{
		temporaryPiece = new 分離活字(insidePiece);
		AffineTransform affineTransform = 調整工具.getAffineTransform(middleValue
				/ insidePiece.字範圍().getHeight());
		temporaryPiece.縮放(affineTransform);
//		調整工具.shrinkPieceByFixingStroke(temporaryPiece, affineTransform);
		return;
	}

	@Override
	public boolean 活字是否太接近()
	{
		return 調整工具.areIntersected(outsidePiece, temporaryPiece);
	}

	@Override
	public boolean 太接近時參數變大()
	{
		return false;
	}

	@Override
	public void 最後處理()
	{
		insidePiece = temporaryPiece;
	}

	@Override
	public double 活字寬度()
	{
		return 調整工具.computePieceRadius(outsidePiece);
	}

	@Override
	public double 接觸邊長()
	{
		return temporaryPiece.字範圍().getWidth()
				+ temporaryPiece.字範圍().getHeight();
	}

	@Override
	public double 活字相斥值()
	{
		return 調整工具.nonsuitableToClose(outsidePiece, temporaryPiece, 接觸邊長());
	}

	@Override
	public 活字單元[] 取得調整後活字物件()
	{
		活字單元[] rectangularAreas = new 分離活字[2];
		rectangularAreas[0] = outsidePiece;
		rectangularAreas[1] = insidePiece;
		return rectangularAreas;
	}
}
