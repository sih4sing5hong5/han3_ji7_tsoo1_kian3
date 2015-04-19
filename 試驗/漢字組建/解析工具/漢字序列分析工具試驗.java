package 漢字組建.解析工具;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import cc.core.部件;
import cc.core.字部件;
import cc.core.組合方式;
import cc.core.文部件;
import cc.core.展開式免查詢;
import cc.core.漢字序列分析工具;

public class 漢字序列分析工具試驗
{
	private 展開式免查詢 展開式 = new 展開式免查詢();

	@Test
	public void 單一組字式字數()
	{
		String 組字式 = "意";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單一組字式部件()
	{
		String 組字式 = "意";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查意部件((文部件) 部件樹陣列.get(0));
	}

	@Test
	public void 單層組字式字數()
	{
		String 組字式 = "⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單層組字式部件()
	{
		String 組字式 = "⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查傳部件((字部件) 部件樹陣列.get(0));
	}

	@Test
	public void 多層組字式字數()
	{
		String 組字式 = "⿰矛⿱攵力";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 多層組字式部件()
	{
		String 組字式 = "⿰矛⿱攵力";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查務部件((字部件) 部件樹陣列.get(0));
	}

	@Test
	public void 三元素組字式字數()
	{
		String 組字式 = "⿲口禾火";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 三元素組字式部件()
	{
		String 組字式 = "⿲口禾火";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查啾部件(部件樹陣列.get(0));
	}

	@Test
	public void 空組字式字數()
	{
		String 組字式 = "";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(部件樹陣列.size(), 0);
	}

	@Test
	public void 兩字組字式字數()
	{
		String 組字式 = "意⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(2, 部件樹陣列.size());
	}

	@Test
	public void 兩字組字式部件()
	{
		String 組字式 = "意⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查意部件(部件樹陣列.get(0));
		檢查傳部件(部件樹陣列.get(1));
	}

	@Test
	public void 第一字無完整組字式字數()
	{
		String 組字式 = "⿰";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 第一字無完整組字式部件()
	{
		String 組字式 = "⿰";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(null, 部件樹陣列.get(0));
	}

	@Test
	public void 第二字無完整組字式字數()
	{
		String 組字式 = "意⿰";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		assertEquals(2, 部件樹陣列.size());
	}

	@Test
	public void 第二字無完整組字式部件()
	{
		String 組字式 = "意⿰";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<部件> 部件樹陣列 = 解析工具.parseText();
		檢查意部件(部件樹陣列.get(0));
		assertEquals(null, 部件樹陣列.get(1));
	}

	private void 檢查意部件(部件 部件)
	{
		文部件 文部件意 = (文部件) 部件;
		assertEquals("意".codePointAt(0), 文部件意.getCodePoint());
	}

	private void 檢查傳部件(部件 部件)
	{
		字部件 字部件傳 = (字部件) 部件;
		assertEquals(組合方式.左右合併, 字部件傳.getType());
		assertEquals(2, 字部件傳.getChildren().length);
		文部件 文部件人 = (文部件) 字部件傳.getChildren()[0];
		文部件 文部件專 = (文部件) 字部件傳.getChildren()[1];
		assertEquals("".codePointAt(0), 文部件人.getCodePoint());
		assertEquals("專".codePointAt(0), 文部件專.getCodePoint());
	}

	private void 檢查務部件(部件 部件)
	{
		字部件 字部件務 = (字部件) 部件;
		assertEquals(組合方式.左右合併, 字部件務.getType());
		assertEquals(字部件務.getChildren().length, 2);
		文部件 文部件矛 = (文部件) 字部件務.getChildren()[0];
		assertEquals("矛".codePointAt(0), 文部件矛.getCodePoint());
		字部件 上下字部件 = (字部件) 字部件務.getChildren()[1];
		assertEquals(組合方式.上下合併, 上下字部件.getType());
		assertEquals(2, 字部件務.getChildren().length);
		文部件 文部件攵 = (文部件) 上下字部件.getChildren()[0];
		文部件 文部件力 = (文部件) 上下字部件.getChildren()[1];
		assertEquals("攵".codePointAt(0), 文部件攵.getCodePoint());
		assertEquals("力".codePointAt(0), 文部件力.getCodePoint());
	}

	private void 檢查啾部件(部件 部件)
	{
		字部件 字部件 = (字部件) 部件;
		assertEquals(組合方式.左右三個合併, 字部件.getType());
		assertEquals(3, 字部件.getChildren().length);
		assertEquals("口".codePointAt(0),
				((文部件) 字部件.getChildren()[0]).getCodePoint());
		assertEquals("禾".codePointAt(0),
				((文部件) 字部件.getChildren()[1]).getCodePoint());
		assertEquals("火".codePointAt(0),
				((文部件) 字部件.getChildren()[2]).getCodePoint());
	}
}
