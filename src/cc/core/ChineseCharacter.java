package cc.core;

import java.awt.Point;

import cc.writer.ChineseCharacterWriter;

public abstract class ChineseCharacter
{
	protected ChineseCharacter parent;
	private Point area;

	public abstract void generateByWriter(ChineseCharacterWriter writer);

	public Point getArea()
	{
		return area;
	}

	public void setArea(Point area)
	{
		this.area = area;
	}
}