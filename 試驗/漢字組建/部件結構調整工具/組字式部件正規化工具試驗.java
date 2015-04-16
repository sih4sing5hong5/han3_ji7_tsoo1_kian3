package 漢字組建.部件結構調整工具;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.core.ChineseCharacter;
import cc.core.展開式免查詢;
import cc.core.漢字序列分析工具;
import cc.core.組字式部件;
import cc.core.組字式部件正規化;
import cc.core.組字式部件組字式建立工具;

public class 組字式部件正規化工具試驗
{
	protected 組字式部件正規化 正規化工具;
	protected 組字式部件組字式建立工具 組字式建立工具 = new 組字式部件組字式建立工具();

	@Before
	public void setUp() throws Exception
	{
		正規化工具 = new 組字式部件正規化();
	}

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
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, new 展開式免查詢());
		ChineseCharacter 部件樹 = 解析工具.parseText().get(0);
		正規化工具.正規化(部件樹);
		組字式部件 組字部件樹 = (組字式部件) 部件樹;
		return 組字部件樹.建立組字式(組字式建立工具);
	}
}
