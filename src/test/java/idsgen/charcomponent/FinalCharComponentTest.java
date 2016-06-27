package idsgen.charcomponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import idsrend.charcomponent.NonFinalCharComponent;

public class FinalCharComponentTest
{
	@Test
	public void 是文部件()
	{
		NonFinalCharComponent 部件意 = new NonFinalCharComponent("意");
		assertTrue(部件意.是文部件());
	}

	@Test
	public void 是字部件()
	{
		NonFinalCharComponent 部件意 = new NonFinalCharComponent("意");
		assertFalse(部件意.是字部件());
	}

	@Test
	public void Unicode編號()
	{
		NonFinalCharComponent 部件意 = new NonFinalCharComponent("意");
		assertEquals("意".codePointAt(0), 部件意.Unicode編號());
	}

	@Test
	public void 部件組字式()
	{
		NonFinalCharComponent 部件意 = new NonFinalCharComponent("意");
		assertEquals("意", 部件意.部件組字式());
	}

	@Test
	public void 樹狀結構組字式()
	{
		NonFinalCharComponent 部件意 = new NonFinalCharComponent("意");
		assertEquals("意", 部件意.樹狀結構組字式());
	}

}
