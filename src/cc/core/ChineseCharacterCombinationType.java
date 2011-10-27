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

	static public ChineseCharacterCombinationType checkType()// TODO
	{
		return null;
	}
}
