package cc.layouttools;

import cc.movabletype.SeprateMovabletype;

/**
 * 組合注音模組。一直佮注音活字擲入來，會排做一排。注音之間會排密密。
 * 
 * @author Ihc
 */
class ZhuyinCloseArrangemod extends ZhuyinArrangemod
{
	void 加新的活字(SeprateMovabletype 新活字)
	{
		累積活字.合併活字(上尾活字);
		上尾活字 = 新活字;
		上尾活字.徙(累積活字.這馬字範圍().getCenterX()
				- 上尾活字.這馬字範圍().getCenterX(), 累積活字.這馬字範圍().getMaxY()
				- 上尾活字.這馬字範圍().getMinY());
		if (上尾活字會當提來對齊無())
			對齊活字 = 上尾活字;
	}
}
