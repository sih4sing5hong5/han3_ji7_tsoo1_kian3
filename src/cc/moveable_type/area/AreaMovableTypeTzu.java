/**
 * 
 */
package cc.moveable_type.area;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacter;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;

/**
 * @author Ihc
 * 
 */
public class AreaMovableTypeTzu extends ChineseCharacterMovableTypeTzu
		implements AreaMovableType
{
	private Area area;
	private Rectangle2D bound;

	public AreaMovableTypeTzu(ChineseCharacter chineseCharacter)
	{
		super(chineseCharacter);
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
