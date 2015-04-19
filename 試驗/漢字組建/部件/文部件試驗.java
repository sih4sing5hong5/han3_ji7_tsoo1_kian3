package 漢字組建.部件;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class 文部件試驗
{
	@Test
	public void 是文部件()
	{
		文部件 部件意 = 文部件("意");
		assertTrue(部件意.是文部件());
	}

	@Test
	public void 是字部件()
	{
		文部件 部件意 = 文部件("意");
		assertFalse(部件意.是文部件());
	}

	@Test
	public void Unicode編號()
	{
		文部件 部件意 = 文部件("意");
		assertEquals("意".codePointAt(0), 部件意.Unicode編號());
	}

	@Test
	public void 部件組字式()
	{
		文部件 部件意 = 文部件("意");
		assertEquals("意", 部件意.部件組字式());
	}

	@Test
	public void 樹狀結構組字式()
	{
		文部件 部件意 = 文部件("意");
		assertEquals("意", 部件意.樹狀結構組字式());
	}

}
