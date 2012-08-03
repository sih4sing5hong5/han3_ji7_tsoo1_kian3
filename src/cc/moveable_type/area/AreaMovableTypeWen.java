package cc.moveable_type.area;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;

/**
 * @author Ihc
 * 
 */
public class AreaMovableTypeWen extends ChineseCharacterMovableTypeWen
		implements AreaMovableType
{
	private Area area;
	private Rectangle2D bound;

	public AreaMovableTypeWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		super(parent, chineseCharacterWen);
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
