package idsgen.charcomponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;

public class NonFinalCharComponentTest
{
	@Test
	public void 是文部件()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertFalse(部件左右.是文部件());
	}

	@Test
	public void 是字部件()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertTrue(部件左右.是字部件());
	}

	@Test
	public void Unicode編號()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertEquals("⿰".codePointAt(0), 部件左右.Unicode編號());
	}

	@Test
	public void 部件組字式()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertEquals("⿰", 部件左右.部件組字式());
	}

	@Test
	public void 底下二元素()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertEquals(2, 部件左右.底下元素().length);
	}

	@Test
	public void 底下三元素()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿲");
		assertEquals(3, 部件左右.底下元素().length);
	}

	@Test
	public void 檢查組合方式()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		assertEquals(CompositionMethods.左右合併, 部件左右.CompositionMethods());
	}

	@Test
	public void 樹狀結構組字式()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		部件左右.底下元素()[0] = new NonFinalCharComponent("");
		部件左右.底下元素()[1] = new NonFinalCharComponent("專");
		assertEquals("⿰專", 部件左右.樹狀結構組字式());
	}

	@Test
	public void 樹狀結構遞迴組字式()
	{
		FinalCharComponent 部件左右 = new FinalCharComponent("⿰");
		FinalCharComponent 部件上下 = new FinalCharComponent("⿱");
		部件上下.底下元素()[0] = new NonFinalCharComponent("攵");
		部件上下.底下元素()[1] = new NonFinalCharComponent("力");
		部件左右.底下元素()[0] = new NonFinalCharComponent("矛");
		部件左右.底下元素()[1] = 部件上下;
		assertEquals("⿰矛⿱攵力", 部件左右.樹狀結構組字式());
	}
}
