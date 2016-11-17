package idsrend.parser;

import java.util.Vector;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import cc.ccomponent_adjuster.ExpSequenceLookup;
import cc.tool.database.String2ControlCode;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;

/**
 * 新的漢字序列分析工具。分析漢字序列的時陣，攏佇這个物件中處理，免予字部件處理。
 * 
 * @author Ihc
 */
public class IDSParser
{
	/** 愛分析的統一碼控制碼陣列 */
	protected int[] 統一碼控制碼;
	/** 目前處理到的控制碼位置 */
	protected int 陣列位置;
	/** 查詢展開式的工具 */
	ExpSequenceLookup 展開式查詢;

	/**
	 * 用字串佮查詢工具初使化物件。
	 * 
	 * @param 漢字字串
	 *            愛分析的字串
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public IDSParser(String 漢字字串, ExpSequenceLookup 展開式查詢)
	{
		this(String2ControlCode.轉換成控制碼(漢字字串), 展開式查詢);
	}

	/**
	 * 用統一碼控制碼佮查詢工具初使化物件。
	 * 
	 * @param 統一碼控制碼
	 *            愛分析的統一碼控制碼陣列
	 * @param 展開式查詢
	 *            查詢展開式的工具
	 */
	public IDSParser(int[] 統一碼控制碼, ExpSequenceLookup 展開式查詢)
	{
		this.統一碼控制碼 = 統一碼控制碼;
		this.陣列位置 = 0;
		this.展開式查詢 = 展開式查詢;
	}

	/**
	 * 分析字串並回傳字串中全部的漢字部件
	 * 
	 * @return 字串中全部的漢字部件。若字串格式有錯，不完整的部件不會被加上去，並且在陣列最後會補上一個null當作通知
	 */
	public Vector<CharComponent> 解析()
	{
		Vector<CharComponent> vector = new Vector<CharComponent>();
		try
		{
			while (!組合式是毋是結束矣())
			{
				vector.add(解析一個組字式());
			}
		}
		catch (IDSExecption e)
		{
			vector.add(null);
		}
		return vector;
	}

	public CharComponent 解析一個組字式() throws IDSExecption
	{
		if (組合式是毋是結束矣())
			throw new IDSExecption();
		CharComponent chineseCharacter = null;
		if (CompositionMethods.isCombinationType(目前控制碼()))
		{
			FinalCharComponent chineseCharacterTzu = new FinalCharComponent(目前控制碼());
			下一个控制碼();
			for (int i = 0; i < chineseCharacterTzu.底下元素().length; ++i)
			{
				chineseCharacterTzu.底下元素()[i] = 解析一個組字式();
			}
			chineseCharacter = chineseCharacterTzu;
		}
		else
		{
			String 展開式 = null;
			展開式 = 展開式查詢.查詢展開式(目前控制碼());
			if (展開式 == null)
				chineseCharacter = new NonFinalCharComponent(目前控制碼());
			else
			{
				IDSParser 分析工具 = new IDSParser(展開式, new ExpSequenceNoLookup());// 避免把異體字給展開
				chineseCharacter = 分析工具.解析一個組字式();
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
	protected int 目前控制碼()
	{
		return 統一碼控制碼[陣列位置];
	}

	/** 換到下一个控制碼。 */
	protected void 下一个控制碼()
	{
		陣列位置++;
		return;
	}

	protected boolean 組合式是毋是結束矣()
	{
		return 統一碼控制碼.length == 陣列位置;
	}
}
