package 漢字組建.部件;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class 字部件試驗
{
	@Test
	public void 是文部件()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertFalse(部件左右.是文部件());
	}

	@Test
	public void 是字部件()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertTrue(部件左右.是字部件());
	}

	@Test
	public void Unicode編號()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertEquals("⿰".codePointAt(0), 部件左右.Unicode編號());
	}

	@Test
	public void 部件組字式()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertEquals("⿰", 部件左右.部件組字式());
	}

	@Test
	public void 底下二元素()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertEquals(2, 部件左右.底下元素().length);
	}

	@Test
	public void 底下三元素()
	{
		字部件 部件左右 = new 字部件("⿲");
		assertEquals(3, 部件左右.底下元素().length);
	}

	@Test
	public void 檢查組合方式()
	{
		字部件 部件左右 = new 字部件("⿰");
		assertEquals(組合方式.左右合併, 部件左右.組合方式());
	}

	@Test
	public void 樹狀結構組字式()
	{
		字部件 部件左右 = new 字部件("⿰");
		部件左右.底下元素()[0] = new 文部件("");
		部件左右.底下元素()[1] = new 文部件("專");
		assertEquals("⿰專", 部件左右.樹狀結構組字式());
	}
}
