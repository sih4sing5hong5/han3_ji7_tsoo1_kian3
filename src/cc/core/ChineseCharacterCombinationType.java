package cc.core;

public enum ChineseCharacterCombinationType
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
		if (string.equals(""))
			return 0;
		return Character.toCodePoint(string.charAt(0), string.charAt(1));
	}

	static boolean isCombinationType(int codePoint)
	{
		for (ChineseCharacterCombinationType type : ChineseCharacterCombinationType
				.values())
		{
			if (codePoint == type.toCodePoint())
				return true;
		}
		return false;
	}

	static ChineseCharacterCombinationType toCombinationType(int codePoint)
	{
		for (ChineseCharacterCombinationType type : ChineseCharacterCombinationType
				.values())
		{
			if (codePoint == type.toCodePoint())
				return type;
		}
		return null;
	}
}
