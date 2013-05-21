package cc.core;

/**
 * 共規的部件結構樹，內底的異寫式換做組字式。
 * 
 * @author Ihc
 */
public class 異寫式代換工具
{
	/** 定義異寫編號數字 */
	protected int[] 編號陣列;
	/** 異寫式查詢的方法 */
	protected 異寫式查詢工具 異寫式查詢;

	/**
	 * 建立一个代換工具。
	 * 
	 * @param 編號陣列
	 *            定義異寫編號數字
	 * @param 異寫式查詢
	 *            異寫式查詢的方法
	 */
	public 異寫式代換工具(int[] 編號陣列, 異寫式查詢工具 異寫式查詢)
	{
		this.編號陣列 = 編號陣列;
		this.異寫式查詢 = 異寫式查詢;
	}

	/**
	 * 將部件下跤的異寫式攏換掉，閣回傳上新的部件出來。 若是愛提著結果，愛記得共原本的指標換掉。
	 * 
	 * @param 部件
	 *            愛換的部件
	 * @return 換了異寫式的部件
	 */
	public ChineseCharacter 代換(ChineseCharacter 部件)
	{
		if (部件 instanceof ChineseCharacterTzu)
		{
			ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
			if (字部件.getType() == ChineseCharacterTzuCombinationType.異寫字編號符號)
				return 設定異體式(字部件);
			for (int i = 0; i < 字部件.getChildren().length; ++i)
				字部件.getChildren()[i] = 代換(字部件.getChildren()[i]);
		}
		return 部件;
	}

	/**
	 * 查異寫式所代表的組字式，順紲產生新的結果。
	 * 
	 * @param 字部件
	 *            是異寫式的主部件
	 * @return 新的異寫式部件
	 */
	protected ChineseCharacter 設定異體式(ChineseCharacterTzu 字部件)
	{
		if (字部件.getType() != ChineseCharacterTzuCombinationType.異寫字編號符號)
		{
			System.err.println("無應該入來異寫字編號符號");
			return 字部件;
		}
		int 異寫字編號 = 提著異寫式編號(字部件.getChildren()[1], 0);
		ChineseCharacter 倒爿部件 = 字部件.getChildren()[0];
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
					新異寫部件 = 序列分析工具.parseCharacter(字部件.getParent());
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

	/**
	 * 行規的異寫式的結構，並沿途算編號出來
	 * 
	 * @param 部件
	 *            編號的主結構
	 * @param 這馬數值
	 *            進前算的數值
	 * @return 上尾結果
	 */
	protected int 提著異寫式編號(ChineseCharacter 部件, int 這馬數值)
	{
		if (部件 instanceof ChineseCharacterWen)
		{
			ChineseCharacterWen 文部件 = (ChineseCharacterWen) 部件;
			return 決定異寫式編號(文部件, 這馬數值);
		}
		ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
		return 提著異寫式編號(字部件.getChildren()[1],
				提著異寫式編號(字部件.getChildren()[0], 這馬數值));
	}

	/**
	 * 共這个部件的值揣出來，閣加上進前算的數值
	 * 
	 * @param 文部件
	 *            代表一个數字的部件
	 * @param 這馬數值
	 *            進前算的數值
	 * @return 上尾結果
	 */
	protected int 決定異寫式編號(ChineseCharacterWen 文部件, int 這馬數值)
	{
		這馬數值 *= 編號陣列.length;
		for (int i = 0; i < 編號陣列.length; ++i)
			if (文部件.getCodePoint() == 編號陣列[i])
				return 這馬數值 + i;
		System.err.println("用無正確的編碼！！");
		return 這馬數值;
	}

}
