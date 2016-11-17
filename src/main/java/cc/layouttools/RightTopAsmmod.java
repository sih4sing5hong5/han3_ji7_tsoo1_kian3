package cc.layouttools;


/**
 * 適用於外部部件在右上的活字接合，如「⿴气米」為「氣」。在接合時，都固定外部活字，並將內部活字固定在左下縮放。
 * 
 * @author Ihc
 */
public class RightTopAsmmod extends ZoomAsmmod
{
	/**
	 * 建立右上接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public RightTopAsmmod(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		temporaryPiece.徙(outsidePiece.這馬字範圍().getMinX()
				- temporaryPiece.這馬字範圍().getMinX(), outsidePiece
				.這馬字範圍().getMaxY()
				- temporaryPiece.這馬字範圍().getMaxY());
		return;
	}
}
