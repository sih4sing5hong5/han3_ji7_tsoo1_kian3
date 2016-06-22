package idsgen.analysistool;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import cc.ccomponent_adjuster.ExpSequenceNoLookup;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;
import idsrend.parser.IDSParser;

public class IDSParserTest
{
	private ExpSequenceNoLookup 展開式 = new ExpSequenceNoLookup();

	@Test
	public void 單一組字式字數()
	{
		String 組字式 = "意";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單一組字式部件()
	{
		String 組字式 = "意";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查意部件((NonFinalCharComponent) 部件樹陣列.get(0));
	}

	@Test
	public void 單層組字式字數()
	{
		String 組字式 = "⿰專";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單層組字式部件()
	{
		String 組字式 = "⿰專";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查傳部件((FinalCharComponent) 部件樹陣列.get(0));
	}

	@Test
	public void 多層組字式字數()
	{
		String 組字式 = "⿰矛⿱攵力";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 多層組字式部件()
	{
		String 組字式 = "⿰矛⿱攵力";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查務部件((FinalCharComponent) 部件樹陣列.get(0));
	}

	@Test
	public void 三元素組字式字數()
	{
		String 組字式 = "⿲口禾火";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 三元素組字式部件()
	{
		String 組字式 = "⿲口禾火";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查啾部件(部件樹陣列.get(0));
	}

	@Test
	public void 空組字式字數()
	{
		String 組字式 = "";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(部件樹陣列.size(), 0);
	}

	@Test
	public void 兩字組字式字數()
	{
		String 組字式 = "意⿰專";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(2, 部件樹陣列.size());
	}

	@Test
	public void 兩字組字式部件()
	{
		String 組字式 = "意⿰專";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查意部件(部件樹陣列.get(0));
		檢查傳部件(部件樹陣列.get(1));
	}

	@Test
	public void 第一字無完整組字式字數()
	{
		String 組字式 = "⿰";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 第一字無完整組字式部件()
	{
		String 組字式 = "⿰";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(null, 部件樹陣列.get(0));
	}

	@Test
	public void 第二字無完整組字式字數()
	{
		String 組字式 = "意⿰";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		assertEquals(2, 部件樹陣列.size());
	}

	@Test
	public void 第二字無完整組字式部件()
	{
		String 組字式 = "意⿰";
		IDSParser 解析工具 = new IDSParser(組字式, 展開式);
		Vector<CharComponent> 部件樹陣列 = 解析工具.解析();
		檢查意部件(部件樹陣列.get(0));
		assertEquals(null, 部件樹陣列.get(1));
	}

	private void 檢查意部件(CharComponent CharComponent)
	{
		NonFinalCharComponent 文部件意 = (NonFinalCharComponent) CharComponent;
		assertEquals("意".codePointAt(0), 文部件意.Unicode編號());
	}

	private void 檢查傳部件(CharComponent CharComponent)
	{
		FinalCharComponent 字部件傳 = (FinalCharComponent) CharComponent;
		assertEquals(CompositionMethods.左右合併, 字部件傳.CompositionMethods());
		assertEquals(2, 字部件傳.底下元素().length);
		NonFinalCharComponent 文部件人 = (NonFinalCharComponent) 字部件傳.底下元素()[0];
		NonFinalCharComponent 文部件專 = (NonFinalCharComponent) 字部件傳.底下元素()[1];
		assertEquals("".codePointAt(0), 文部件人.Unicode編號());
		assertEquals("專".codePointAt(0), 文部件專.Unicode編號());
	}

	private void 檢查務部件(CharComponent CharComponent)
	{
		FinalCharComponent 字部件務 = (FinalCharComponent) CharComponent;
		assertEquals(CompositionMethods.左右合併, 字部件務.CompositionMethods());
		assertEquals(字部件務.底下元素().length, 2);
		NonFinalCharComponent 文部件矛 = (NonFinalCharComponent) 字部件務.底下元素()[0];
		assertEquals("矛".codePointAt(0), 文部件矛.Unicode編號());
		FinalCharComponent 上下字部件 = (FinalCharComponent) 字部件務.底下元素()[1];
		assertEquals(CompositionMethods.上下合併, 上下字部件.CompositionMethods());
		assertEquals(2, 字部件務.底下元素().length);
		NonFinalCharComponent 文部件攵 = (NonFinalCharComponent) 上下字部件.底下元素()[0];
		NonFinalCharComponent 文部件力 = (NonFinalCharComponent) 上下字部件.底下元素()[1];
		assertEquals("攵".codePointAt(0), 文部件攵.Unicode編號());
		assertEquals("力".codePointAt(0), 文部件力.Unicode編號());
	}

	private void 檢查啾部件(CharComponent CharComponent)
	{
		FinalCharComponent FinalCharComponent = (FinalCharComponent) CharComponent;
		assertEquals(CompositionMethods.左右三個合併, FinalCharComponent.CompositionMethods());
		assertEquals(3, FinalCharComponent.底下元素().length);
		assertEquals("口".codePointAt(0),
				((NonFinalCharComponent) FinalCharComponent.底下元素()[0]).Unicode編號());
		assertEquals("禾".codePointAt(0),
				((NonFinalCharComponent) FinalCharComponent.底下元素()[1]).Unicode編號());
		assertEquals("火".codePointAt(0),
				((NonFinalCharComponent) FinalCharComponent.底下元素()[2]).Unicode編號());
	}
}
