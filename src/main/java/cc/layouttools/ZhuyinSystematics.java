package cc.layouttools;

import java.util.Vector;

import cc.movabletype.SeprateMovabletype;

/**
 * 記錄這馬分類的狀況，而且存有啥物符號。
 * 
 * @author Ihc
 */
class ZhuyinSystematics
{
	/** 這馬分類到佗矣 */
	protected ZhuyinTypes 目前類別;
	/** 頂面的聲調符號 */
	protected Vector<SeprateMovabletype> 輕聲;
	/** 主要聲韻符號 */
	protected Vector<SeprateMovabletype> 聲韻;
	/** 邊仔聲調符號 */
	protected Vector<SeprateMovabletype> 調號;

	/** 　建立一个分類物件 */
	ZhuyinSystematics()
	{
		目前類別 = ZhuyinTypes.輕聲;
		輕聲 = new Vector<SeprateMovabletype>();
		聲韻 = new Vector<SeprateMovabletype>();
		調號 = new Vector<SeprateMovabletype>();
	}
}
