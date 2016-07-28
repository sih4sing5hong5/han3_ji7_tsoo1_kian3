package cc.layouttools;


/**
 * 適用於外部部件有上下左三邊的活字接合，如「⿴匚甲」為「匣」。在接合時，都固定外部活字，並將內部活字固定在右中縮放。
 * 
 * @author Ihc
 */
public class TBLSurroundAssembler extends ZoomAsmmod
{
	/**
	 * 建立上下左三邊接合模組
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public TBLSurroundAssembler(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		temporaryPiece.徙(outsidePiece.這馬字範圍().getMaxX()
				- temporaryPiece.這馬字範圍().getMaxX(), outsidePiece
				.這馬字範圍().getCenterY()
				- temporaryPiece.這馬字範圍().getCenterY());
		return;
	}
}
