package cc.adjusting.piece;

import java.util.Vector;

import cc.moveable_type.rectangular_area.RectangularArea;

/**
 * 記錄這馬分類的狀況，而且存有啥物符號。
 * 
 * @author Ihc
 */
class 注音符號分類
{
	/** 這馬分類到佗矣 */
	protected 注音符號類別 目前類別;
	/** 頂面的聲調符號 */
	protected Vector<RectangularArea> 輕聲;
	/** 主要聲韻符號 */
	protected Vector<RectangularArea> 聲韻;
	/** 邊仔聲調符號 */
	protected Vector<RectangularArea> 調號;

	/** 　建立一个分類物件 */
	注音符號分類()
	{
		目前類別 = 注音符號類別.輕聲;
		輕聲 = new Vector<RectangularArea>();
		聲韻 = new Vector<RectangularArea>();
		調號 = new Vector<RectangularArea>();
	}
}
