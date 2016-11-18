package idsrend.charcomponent;

/**
 * 漢字部件的組合方式。為了簡化輸入起見，共有三種組合符號(⿰⿱⿴)，除了左右水平合併(⿰)和上下垂直合併(⿱)外，其他的都屬於包含關係(⿴)。
 * 
 * @author Ihc
 */
public enum CompositionMethods
{
	/** 左右水平合併⿰ */
	左右合併,
	/** 上下垂直合併⿱ */
	上下合併,
	/** ⿲ */
	左右三個合併,
	/*** ⿳ ***/
	上下三個合併,
	/*** 包含關係⿴ ***/
	四面包圍,
	/*** 包含關係⿵ ***/
	左右上包圍,
	/*** 包含關係⿶ ***/
	左右下包圍,
	/*** 包含關係⿷ ***/
	上下左包圍,
	/*** 包含關係⿸ ***/
	左上包圍,
	/*** 包含關係⿹ ***/
	右上包圍,
	/*** 包含關係⿺ ***/
	左下包圍,
	/*** 包含關係⿻ ***/
	重疊,
	/** 專門處理注音符號⿿ */
	注音符號;// ⿰⿱⿲⿳⿴⿵⿶⿷⿸⿹⿺⿻⿿
	public String toString()
	{
		switch (this)
		{
		case 左右合併:
			return "⿰";
		case 上下合併:
			return "⿱";
		case 左右三個合併:
			return "⿲";
		case 上下三個合併:
			return "⿳";
		case 四面包圍:
			return "⿴";
		case 左右上包圍:
			return "⿵";
		case 左右下包圍:
			return "⿶";
		case 上下左包圍:
			return "⿷";
		case 左上包圍:
			return "⿸";
		case 右上包圍:
			return "⿹";
		case 左下包圍:
			return "⿺";
		case 重疊:
			return "⿻";
		case 注音符號:
			return "⿿";
		}
		return "";
	}

	/**
	 * 取得此組合方式所需部件數
	 * 
	 * @return 此組合方式所需部件數
	 */
	public int getNumberOfChildren()
	{
		switch (this)
		{
		case 左右合併:
		case 上下合併:
		case 四面包圍:
		case 左右上包圍:
		case 左右下包圍:
		case 上下左包圍:
		case 左上包圍:
		case 右上包圍:
		case 左下包圍:
		case 重疊:
		case 注音符號:
			return 2;
		case 左右三個合併:
		case 上下三個合併:
			return 3;
		}
		return 0;
	}

	/**
	 * 取得組合符號Unicode編碼
	 * 
	 * @return 組合符號Unicode編碼
	 */
	public int toCodePoint()
	{
		String string = toString();
		if (string.length() == 1 || string.length() == 2)
			return string.codePointAt(0);
		else
			return Character.MAX_CODE_POINT + 1;
	}

	/**
	 * 佇正規化的時陣，需要改做正爿結合，判斷這个字部件是毋是愛分析看覓。
	 * 
	 * @return 是毋是愛處理結合律的問題
	 */
	public boolean 有結合律無()
	{
		switch (this)
		{
		case 左右合併:
		case 上下合併:
		case 注音符號:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 判斷是否為組合符號
	 * 
	 * @param codePoint
	 *            欲判斷之Unicode編碼
	 * @return 此Unicode編碼是否為組合符號
	 */
	static public boolean isCombinationType(int codePoint)
	{
		for (CompositionMethods type : CompositionMethods
				.values())
		{
			if (codePoint == type.toCodePoint())
				return true;
		}
		return false;
	}

	/**
	 * 使用Unicode編碼取得相對應之組合符號
	 * 
	 * @param codePoint
	 *            欲轉換之Unicode編碼
	 * @return 相對應之組合符號。但是若<code>codePoint</code>不是組合符號的Unicode編碼，回傳null
	 */
	static public CompositionMethods toCombinationType(
			int codePoint)
	{
		for (CompositionMethods type : CompositionMethods
				.values())
		{
			if (codePoint == type.toCodePoint())
				return type;
		}
		return null;
	}
}
