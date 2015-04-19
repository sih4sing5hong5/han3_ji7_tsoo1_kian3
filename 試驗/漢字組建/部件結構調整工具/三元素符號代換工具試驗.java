package 漢字組建.部件結構調整工具;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import 漢字組建.解析工具.組字式序列解析工具;
import 漢字組建.部件.部件;
import cc.core.展開式免查詢;
import cc.core.組字式部件組字式建立工具;

public class 三元素符號代換工具試驗
{
	protected 三元素符號代換工具 代換工具 = new 三元素符號代換工具();
	protected 組字式部件組字式建立工具 組字式建立工具 = new 組字式部件組字式建立工具();

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

	protected String 代換結果(String 組字式)
	{
		組字式序列解析工具 解析工具 = new 組字式序列解析工具(組字式, new 展開式免查詢());
		部件 部件樹 = 解析工具.parseText().get(0);
		部件 組字部件樹 = (部件) 代換工具.三元素組合代換成二元素(部件樹);
		return 組字部件樹.建立組字式(組字式建立工具);
	}
}
