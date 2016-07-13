package idsgen.CharComponentStructureAdjuster;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import idsrend.CharComponentStructureAdjuster.TriElementsReplacer;
import idsrend.charcomponent.CharComponent;
import idsrend.parser.IDSParser;

public class TriElementsReplacerTest
{
	protected TriElementsReplacer 代換工具 = new TriElementsReplacer();

	@Test
	public void 左右組字方式()
	{
		assertEquals("⿰口⿰禾火", 代換結果("⿲口禾火"));
	}

	@Test
	public void 上下組字方式()
	{
		assertEquals("⿱立⿱日心", 代換結果("⿳立日心"));
	}

	@Test
	public void 左右雙層組字方式()
	{
		assertEquals("⿱⿰糹⿰言糹攵", 代換結果("⿱⿲糹言糹攵"));
	}

	@Test
	public void 巢狀三部件組合符號()
	{
		assertEquals("⿺辶⿱宀⿱珤⿰隹⿰貝招", 代換結果("⿺辶⿳宀珤⿲隹貝招"));
	}

	protected String 代換結果(String 組字式)
	{
		IDSParser 解析工具 = new IDSParser(組字式, new ExpSequenceNoLookup());
		CharComponent 部件樹 = 解析工具.解析().get(0);
		CharComponent 組字部件樹 = (CharComponent) 代換工具.三元素組合代換成二元素(部件樹);
		return 組字部件樹.樹狀結構組字式();
	}
}
