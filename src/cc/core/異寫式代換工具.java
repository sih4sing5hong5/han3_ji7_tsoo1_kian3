package cc.core;

public class 異寫式代換工具
{
	protected int[] 編號陣列;
	protected 異寫式查詢工具 異寫式查詢;

	public 異寫式代換工具(int[] 編號陣列, 異寫式查詢工具 異寫式查詢)
	{
		this.編號陣列 = 編號陣列;
		this.異寫式查詢 = 異寫式查詢;
	}

	public ChineseCharacter 代換(ChineseCharacter 部件)
	{
		if (部件 instanceof ChineseCharacterTzu)
		{
			ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
			if (字部件.getType() == ChineseCharacterTzuCombinationType.異寫字編號符號)
				return 設定異體字(字部件);
			for (int i = 0; i < 字部件.getChildren().length; ++i)
				字部件.getChildren()[i] = 代換(字部件.getChildren()[i]);
		}
		return 部件;
	}

	protected ChineseCharacter 設定異體字(ChineseCharacterTzu chineseCharacterTzu)
	{
		if (chineseCharacterTzu.getType() != ChineseCharacterTzuCombinationType.異寫字編號符號)
		{
			System.err.println("無應該入來異寫字編號符號");
			return chineseCharacterTzu;
		}
		int 異寫字編號 = 提著異寫字編號(chineseCharacterTzu.getChildren()[1], 0);
		ChineseCharacter 倒爿部件 = chineseCharacterTzu.getChildren()[0];
		if (倒爿部件 instanceof 組字式部件)// 假設組字式佮規定的仝款
		{
			組字式部件 倒爿組字式部件 = (組字式部件) 倒爿部件;
			String 異寫字結果 = 異寫式查詢.查異寫組字式(倒爿組字式部件.提到組字式(), 異寫字編號);
			try
			{
				if (異寫字結果 != null && !異寫字結果.equals(倒爿組字式部件.提到組字式()))
				{
					展開式免查詢 查詢方式 = new 展開式免查詢();
					漢字序列分析工具 序列分析工具 = new 漢字序列分析工具(異寫字結果, 查詢方式);
					ChineseCharacter 新異寫部件;
					新異寫部件 = 序列分析工具.parseCharacter(chineseCharacterTzu
							.getParent());
					return 新異寫部件;
				}
			}
			catch (ChineseCharacterFormatException e)
			{
				System.err.println("查出來的異寫字分析有問題");
			}
		}
		else
		{
			System.err.println("有異寫字，但是無支援");
		}
		return 倒爿部件;
	}

	protected int 提著異寫字編號(ChineseCharacter 部件, int 這馬數值)
	{
		if (部件 instanceof ChineseCharacterWen)
		{
			ChineseCharacterWen 文部件 = (ChineseCharacterWen) 部件;
			return 決定異寫字編號(文部件, 這馬數值);
		}
		ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
		return 提著異寫字編號(字部件.getChildren()[1],
				提著異寫字編號(字部件.getChildren()[0], 這馬數值));
	}

	protected int 決定異寫字編號(ChineseCharacterWen 文部件, int 這馬數值)
	{
		這馬數值 *= 編號陣列.length;
		for (int i = 0; i < 編號陣列.length; ++i)
			if (文部件.getCodePoint() == 編號陣列[i])
				return 這馬數值 + i;
		System.err.println("用無正確的編碼！！");
		return 這馬數值;
	}

}
