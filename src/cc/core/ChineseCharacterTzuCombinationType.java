package cc.core;

public enum ChineseCharacterTzuCombinationType
{
	horizontal, vertical, wrap;// ⿰⿱⿲⿳⿴⿵⿶⿷⿸⿹⿺⿻
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
		}
		return "";
	}

	public int getNumberOfChildren()
	{
		switch (this)
		{
		case horizontal:
		case vertical:
		case wrap:
			return 2;
		}
		return 0;
	}

	public int toCodePoint()
	{
		String string = toString();
		if (string.length() == 0)
			return Character.MAX_CODE_POINT+1;
		if (string.length() == 1)
			return string.charAt(0);
		if (string.length() == 2)
			return Character.toCodePoint(string.charAt(0), string.charAt(1));
		return Character.MAX_CODE_POINT+1;
	}

	static boolean isCombinationType(int codePoint)
	{
		for (ChineseCharacterTzuCombinationType type : ChineseCharacterTzuCombinationType
				.values())
		{
			if (codePoint == type.toCodePoint())
				return true;
		}
		return false;
	}

	static ChineseCharacterTzuCombinationType toCombinationType(int codePoint)
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
