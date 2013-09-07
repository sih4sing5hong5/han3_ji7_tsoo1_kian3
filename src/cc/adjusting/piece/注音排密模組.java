package cc.adjusting.piece;

import cc.moveable_type.rectangular_area.活字單元;

/**
 * 組合注音模組。一直佮注音活字擲入來，會排做一排。注音之間會排密密。
 * 
 * @author Ihc
 */
class 注音排密模組 extends 注音排放模組
{
	void 加新的活字(活字單元 新活字)
	{
		累積活字.add(上尾活字);
		上尾活字 = 新活字;
		上尾活字.moveBy(累積活字.getBounds2D().getCenterX()
				- 上尾活字.getBounds2D().getCenterX(), 累積活字.getBounds2D().getMaxY()
				- 上尾活字.getBounds2D().getMinY());
		if (上尾活字會當提來對齊無())
			對齊活字 = 上尾活字;
	}
}
