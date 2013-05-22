package cc.adjusting.piece;

import java.awt.geom.Rectangle2D;

import cc.moveable_type.rectangular_area.RectangularArea;

abstract class 注音排放模組
{
	protected RectangularArea 累積活字;
	protected RectangularArea 上尾活字;
	protected RectangularArea 對齊活字;

	注音排放模組()
	{
		累積活字 = new RectangularArea();
		上尾活字 = new RectangularArea();
		對齊活字 = new RectangularArea();
	}

	abstract void 加新的活字(RectangularArea 新活字);

	RectangularArea 目前結果()
	{
		RectangularArea 結果 = new RectangularArea(累積活字);
		結果.add(上尾活字);
		return 結果;
	}

	Rectangle2D 上尾範圍()
	{
		return 上尾活字.getBounds2D();
	}

	Rectangle2D 對齊範圍()
	{
		return 對齊活字.getBounds2D();
	}

	boolean 會當提來對齊無()
	{
		return 對齊活字.getBounds2D().getWidth() < 1e-8
				|| 上尾活字.getBounds2D().getWidth() * 3.0 >= 上尾活字.getBounds2D()
						.getHeight();
	}
}
