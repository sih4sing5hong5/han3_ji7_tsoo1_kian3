package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.RectangularArea;

class 注音排密模組 extends 注音排放模組
{
	void 加新的活字(RectangularArea 新活字)
	{
		累積活字.add(上尾活字);
		上尾活字 = 新活字;
		上尾活字.moveBy(累積活字.getBounds2D().getCenterX()
				- 上尾活字.getBounds2D().getCenterX(), 累積活字.getBounds2D().getMaxY()
				- 上尾活字.getBounds2D().getMinY());
		if (會當提來對齊無())
			對齊活字 = 上尾活字;
	}
}
