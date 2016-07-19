package cc.layouttools;

/**
 * 適用於靠移動合併的模組，若是兩活字是靠移動距離來當參數調整，就可適用此型態。
 * 
 * @author Ihc
 */
public abstract class FlatSurAsmmod extends BisearchPasteAsmMod
{
	/**
	 * 建立平移拼合模組
	 * @param 調整工具 使用此模組的調整工具，並使用其自身合併相關函式
	 */
	public FlatSurAsmmod(MergePieceAdjuster 調整工具)
	{
		super(調整工具);
	}

	@Override
	public boolean 太接近時參數變大()
	{
		return true;
	}

	@Override
	public void 最後處理()
	{
		return;
	}
}
