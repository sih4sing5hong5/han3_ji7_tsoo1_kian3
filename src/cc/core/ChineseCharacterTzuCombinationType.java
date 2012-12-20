package cc.core;

/**
 * 漢字部件的組合方式。為了簡化輸入起見，共有三種組合符號(⿰⿱⿴)，除了左右水平合併(⿰)和上下垂直合併(⿱)外，其他的都屬於包含關係(⿴)。
 * 
 * @author Ihc
 */
public enum ChineseCharacterTzuCombinationType
{
	/**
	 * 左右水平合併
	 */
	horizontal, /**
	 * 上下垂直合併
	 */
	vertical, /**
	 * 包含關係
	 */
	wrap, /** 儲存異寫字編號 */
	異寫字編號符號, /** 專門處理注音符號 */
	注音符號;// ⿰⿱⿲⿳⿴⿵⿶⿷⿸⿹⿺⿻
	public String toString()
	{
		switch (this)
		{
		case horizontal:
			return "⿰";
		case vertical:
			return "⿱";
		case wrap:
			return "⿴";
		case 異寫字編號符號:
			return "⿻";
		case 注音符號:
			return "⿳";
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
		case horizontal:
		case vertical:
		case wrap:
		case 異寫字編號符號:
		case 注音符號:
			return 2;
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
		if (string.length() == 0)
			return Character.MAX_CODE_POINT + 1;
		if (string.length() == 1)
			return string.codePointAt(0);
		if (string.length() == 2)
			return Character.toCodePoint(string.charAt(0), string.charAt(1));
		return Character.MAX_CODE_POINT + 1;
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
		for (ChineseCharacterTzuCombinationType type : ChineseCharacterTzuCombinationType
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
	static public ChineseCharacterTzuCombinationType toCombinationType(
			int codePoint)
	{
		for (ChineseCharacterTzuCombinationType type : ChineseCharacterTzuCombinationType
				.values())
		{
			if (codePoint == type.toCodePoint())
				return type;
		}
		return null;
	}
}
