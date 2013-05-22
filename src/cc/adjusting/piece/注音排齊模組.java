package cc.adjusting.piece;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cc.moveable_type.rectangular_area.RectangularArea;

class 注音排齊模組
{
	protected RectangularArea 累積活字;
	protected RectangularArea 上尾活字;

	注音排齊模組()
	{
		累積活字 = new RectangularArea();
		上尾活字 = new RectangularArea();
	}

	void 加新的活字(RectangularArea 新活字)
	{
		累積活字.add(上尾活字);
		上尾活字 = 新活字;

		上尾活字.moveBy(累積活字.getBounds2D().getCenterX()
				- 上尾活字.getBounds2D().getCenterX(), 累積活字.getBounds2D().getMaxY()
				- 上尾活字.getBounds2D().getMinY());
	}

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
}
