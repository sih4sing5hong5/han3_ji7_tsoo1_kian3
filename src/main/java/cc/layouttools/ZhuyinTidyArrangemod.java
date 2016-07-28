package cc.layouttools;

import java.awt.geom.Rectangle2D;

import cc.movabletype.SeprateMovabletype;

/**
 * 組合注音模組。一直佮注音活字擲入來，會排做一排。注音之間會一格一格照位排。
 * 
 * @author Ihc
 */
class ZhuyinTidyArrangemod extends ZhuyinArrangemod
{
	@Override
	void 加新的活字(SeprateMovabletype 新活字)
	{
		double 頂一个中心懸度 = 上尾活字.這馬字範圍().getCenterY();
		double 頂一个活字大細 = Math.max(上尾活字.這馬字範圍().getWidth(), 上尾活字
				.這馬字範圍().getHeight());
		累積活字.合併活字(上尾活字);
		上尾活字 = 新活字;
		double 活字大細 = Math.max(上尾活字.這馬字範圍().getWidth(), 上尾活字
				.這馬字範圍().getHeight());
		上尾活字.徙(累積活字.這馬字範圍().getCenterX()
				- 上尾活字.這馬字範圍().getCenterX(), 頂一个中心懸度
				- 上尾活字.這馬字範圍().getCenterY() + Math.max(頂一个活字大細, 活字大細));
		if (上尾活字會當提來對齊無())
			對齊活字 = 上尾活字;
	}

	@Override
	Rectangle2D 上尾範圍()
	{
		double 活字大細 = Math.max(上尾活字.這馬字範圍().getWidth(), 上尾活字
				.這馬字範圍().getHeight());
		Rectangle2D.Double 格仔 = new Rectangle2D.Double(上尾活字.這馬字範圍()
				.getMinX() - 活字大細 / 2.0, 上尾活字.這馬字範圍().getMinY() - 活字大細
				/ 2.0, 活字大細, 活字大細);
		return 格仔;
	}

	@Override
	Rectangle2D 對齊範圍()
	{
		double 活字大細 = Math.max(對齊活字.這馬字範圍().getWidth(), 對齊活字
				.這馬字範圍().getHeight());
		Rectangle2D.Double 格仔 = new Rectangle2D.Double(對齊活字.這馬字範圍()
				.getMinX() - 活字大細 / 2.0, 對齊活字.這馬字範圍().getMinY() - 活字大細
				/ 2.0, 活字大細, 活字大細);
		return 格仔;
	}
}
