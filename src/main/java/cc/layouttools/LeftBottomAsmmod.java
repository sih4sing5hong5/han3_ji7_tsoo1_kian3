package cc.layouttools;


/**
 * 適用於外部部件在左下的活字接合，如「⿴辶咼」為「過」。在接合時，都固定外部活字，並將內部活字固定在右上縮放。
 * 
 * @author Ihc
 */
public class LeftBottomAsmmod extends ZoomAsmmod
{
	/**
	 * 建立左下接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public LeftBottomAsmmod(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		temporaryPiece.徙(outsidePiece.這馬字範圍().getMaxX()
				- temporaryPiece.這馬字範圍().getMaxX(), outsidePiece
				.這馬字範圍().getMinY()
				- temporaryPiece.這馬字範圍().getMinY());
		return;
	}
}
