package cc.core;

import cc.tool.database.字串與控制碼轉換;

/**
 * 新的漢字序列分析工具。分析漢字序列的時陣，攏佇這个物件中處理，免予字部件處理。
 * 
 * @author Ihc
 */
public class 漢字序列分析工具 extends ChineseCharacterUtility
{
	/** 愛分析的統一碼控制碼陣列 */
	protected int[] 統一碼控制碼;
	/** 目前處理到的控制碼位置 */
	protected int 陣列位置;
	/** 查詢展開式的工具 */
	展開式查詢工具 展開式查詢;
	/** 用來看愛展開式愛展開無 */
	private int 異寫字編號符號深度;

	/**
	 * 用字串佮查詢工具初使化物件。
	 * 
	 * @param 漢字字串
	 *            愛分析的字串
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public 漢字序列分析工具(String 漢字字串, 展開式查詢工具 展開式查詢)
	{
		this(字串與控制碼轉換.轉換成控制碼(漢字字串), 展開式查詢);
	}

	/**
	 * 用統一碼控制碼佮查詢工具初使化物件。
	 * 
	 * @param 統一碼控制碼
	 *            愛分析的統一碼控制碼陣列
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public 漢字序列分析工具(int[] 統一碼控制碼, 展開式查詢工具 展開式查詢)
	{
		super(new String());
		this.統一碼控制碼 = 統一碼控制碼;
		this.陣列位置 = 0;
		this.展開式查詢 = 展開式查詢;
	}

	@Override
	public ChineseCharacter parseCharacter(ChineseCharacterTzu parent)
			throws ChineseCharacterFormatException
	{
		if (組合式是毋是結束矣())
			throw new ChineseCharacterFormatException();
		ChineseCharacter chineseCharacter = null;
		if (ChineseCharacterTzuCombinationType.isCombinationType(目前控制碼()))
		{
			ChineseCharacterTzu chineseCharacterTzu = new 組字式字部件(parent,
					目前控制碼());
			下一个控制碼();
			// 若是編碼符號愛設定勿愛展開
			if (chineseCharacterTzu.getType() == ChineseCharacterTzuCombinationType.異寫字編號符號)
				異寫字編號符號深度++;
			for (int i = 0; i < chineseCharacterTzu.getChildren().length; ++i)
			{
				chineseCharacterTzu.getChildren()[i] = parseCharacter(chineseCharacterTzu);
			}
			if (chineseCharacterTzu.getType() == ChineseCharacterTzuCombinationType.異寫字編號符號)
				異寫字編號符號深度--;
			chineseCharacter = chineseCharacterTzu;
		}
		else
		{
			String 展開式 = null;
			if (異寫字編號符號深度 == 0)
				展開式 = 展開式查詢.查詢展開式(目前控制碼());
			if (展開式 == null)
				chineseCharacter = new 組字式文部件(parent, 目前控制碼());
			else
			{
				漢字序列分析工具 分析工具 = new 漢字序列分析工具(展開式, new 展開式免查詢());// 避免把異體字給展開
				// TODO 若異寫字已經予儂拆開欲按怎？？
				chineseCharacter = 分析工具.parseCharacter(parent);
			}
			下一个控制碼();
		}
		return chineseCharacter;
	}

	/**
	 * 提到目前的控制碼。
	 * 
	 * @return 目前的控制碼
	 */
	private int 目前控制碼()
	{
		return 統一碼控制碼[陣列位置];
	}

	/** 換到下一个控制碼。 */
	private void 下一个控制碼()
	{
		陣列位置++;
		return;
	}

	@Override
	protected boolean 組合式是毋是結束矣()
	{
		return 統一碼控制碼.length == 陣列位置;
	}
}
