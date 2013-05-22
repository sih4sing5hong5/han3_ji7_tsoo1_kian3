package cc.adjusting.piece;

import java.awt.geom.Rectangle2D;

import cc.moveable_type.rectangular_area.RectangularArea;

class 注音排齊模組 extends 注音排放模組
{
	@Override
	void 加新的活字(RectangularArea 新活字)
	{
		double 頂一个中心懸度 = 上尾活字.getBounds2D().getCenterY();
		double 頂一个活字大細 = Math.max(上尾活字.getBounds2D().getWidth(), 上尾活字
				.getBounds2D().getHeight());
		累積活字.add(上尾活字);
		上尾活字 = 新活字;
		double 活字大細 = Math.max(上尾活字.getBounds2D().getWidth(), 上尾活字
				.getBounds2D().getHeight());
		上尾活字.moveBy(累積活字.getBounds2D().getCenterX()
				- 上尾活字.getBounds2D().getCenterX(), 頂一个中心懸度
				- 上尾活字.getBounds2D().getCenterY() + Math.max(頂一个活字大細, 活字大細));
		if (會當提來對齊無())
			對齊活字 = 上尾活字;
		else System.out.println("@@@@@@@@@@@@@SSAA");
	}

	@Override
	Rectangle2D 上尾範圍()
	{
		double 活字大細 = Math.max(上尾活字.getBounds2D().getWidth(), 上尾活字
				.getBounds2D().getHeight());
		Rectangle2D.Double 格仔 = new Rectangle2D.Double(上尾活字.getBounds2D()
				.getMinX() - 活字大細 / 2.0, 上尾活字.getBounds2D().getMinY() - 活字大細
				/ 2.0, 活字大細, 活字大細);
		return 格仔;
	}

	@Override
	Rectangle2D 對齊範圍()
	{
		double 活字大細 = Math.max(對齊活字.getBounds2D().getWidth(), 對齊活字
				.getBounds2D().getHeight());
		Rectangle2D.Double 格仔 = new Rectangle2D.Double(對齊活字.getBounds2D()
				.getMinX() - 活字大細 / 2.0, 對齊活字.getBounds2D().getMinY() - 活字大細
				/ 2.0, 活字大細, 活字大細);
		return 格仔;
	}
}
