package cc.moveable_type.area;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterTzu;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;

/**
 * 區塊<code>AreaMovableType</code> 在設定時就讀取字體，處理時要考慮部件的差異，<code>area</code>
 * （區塊活字）記錄目前長寬位置及部件形狀，<code>bound</code> 記錄預計長寬位置。
 * 
 * @author Ihc
 */
public class AreaMovableTypeTzu extends ChineseCharacterMovableTypeTzu
		implements AreaMovableType
{
	/** 區塊活字 */
	private Area area;
	/** 預計長寬位置 */
	private Rectangle2D bound;

	/**
	 * 建立區塊合體活字
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterTzu
	 *            字部件結構
	 */
	public AreaMovableTypeTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		super(parent, chineseCharacterTzu);
	}

	@Override
	public Area getArea()
	{
		return area;
	}

	@Override
	public void setArea(Area area)
	{
		this.area = area;
	}

	@Override
	public Rectangle2D getBound()
	{
		return bound;
	}

	@Override
	public void setBound(Rectangle2D bound)
	{
		this.bound = bound;
	}

}
