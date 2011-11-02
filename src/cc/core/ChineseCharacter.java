package cc.core;

import java.awt.Point;

import cc.typesetting.ChineseCharacterTypesetter;

public abstract class ChineseCharacter
{
	protected ChineseCharacter parent;
	private Point area;

	public abstract void generateByWriter(ChineseCharacterTypesetter writer);

	public Point getArea()
	{
		return area;
	}

	public void setArea(Point area)
	{
		this.area = area;
	}
}