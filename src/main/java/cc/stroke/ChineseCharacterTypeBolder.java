package cc.stroke;

import java.awt.Stroke;

/**
 * 物件活字筆劃加寬工具包。包裝產生<code>Stroke</code>的介面。因為<code>BasicStroke</code>
 * 無法直接用物件直接產生不同粗細的物件，所以用<code></code>來包裝給<code>PieceAdjuster</code>用。
 * 
 * @author Ihc
 */
public interface ChineseCharacterTypeBolder
{
	/**
	 * 取得筆劃加寬物件
	 * 
	 * @param width
	 *            加寬的寬度
	 * @return 筆劃加寬物件
	 */
	public Stroke getStroke(double width);
}
