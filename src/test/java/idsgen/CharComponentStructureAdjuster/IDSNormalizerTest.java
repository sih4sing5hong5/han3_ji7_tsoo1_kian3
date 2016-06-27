package idsgen.CharComponentStructureAdjuster;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import idsrend.CharComponentStructureAdjuster.IDSnormalizer;
import idsrend.charcomponent.CharComponent;
import idsrend.parser.IDSParser;

public class IDSNormalizerTest
{
	protected IDSnormalizer 正規化工具 = new IDSnormalizer();

	@Test
	public void 單一組字部件()
	{
		assertEquals("意", 正規化結果("意"));
	}

	@Test
	public void 單層組字部件()
	{
		assertEquals("⿰專", 正規化結果("⿰專"));
	}

	@Test
	public void 森組字部件()
	{
		assertEquals("⿱木⿰木木", 正規化結果("⿱木⿰木木"));
	}

	@Test
	public void 三連字右結合正規化()
	{
		assertEquals("⿰羊⿰羊羊", 正規化結果("⿰羊⿰羊羊"));
	}

	@Test
	public void 三連字左結合組字部件()
	{
		assertEquals("⿰羊⿰羊羊", 正規化結果("⿰⿰羊羊羊"));
	}

	@Test
	public void 四連字左結合組字部件()
	{
		assertEquals("⿰口⿰豕⿰禾火", 正規化結果("⿰⿰⿰口豕禾火"));
	}

	@Test
	public void 四連字混合結合組字部件()
	{
		assertEquals("⿰口⿰豕⿰禾火", 正規化結果("⿰⿰口豕⿰禾火"));
	}

	@Test
	public void 四連字右結合組字部件()
	{
		assertEquals("⿰口⿰豕⿰禾火", 正規化結果("⿰口⿰豕⿰禾火"));
	}

	@Test
	public void 變右結合正規化()
	{
		assertEquals("⿱⿰糹⿰言糹攵", 正規化結果("⿱⿰糹⿰言糹攵"));
	}

	@Test
	public void 變左結合組字部件()
	{
		assertEquals("⿱⿰糹⿰言糹攵", 正規化結果("⿱⿰⿰糹言糹攵"));
	}

	protected String 正規化結果(String 組字式)
	{
		IDSParser 解析工具 = new IDSParser(組字式, new ExpSequenceNoLookup());
		CharComponent 部件樹 = (CharComponent) 解析工具.解析().get(0);
		String 原本部件樹組字式 = 部件樹.樹狀結構組字式();
		CharComponent 組字部件樹 = (CharComponent) 正規化工具.正規化((CharComponent) 部件樹);
		assertEquals(原本部件樹組字式, 部件樹.樹狀結構組字式());
		return 組字部件樹.樹狀結構組字式();
	}
}
