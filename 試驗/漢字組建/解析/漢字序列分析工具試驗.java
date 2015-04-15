package 漢字組建.解析;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.core.ChineseCharacterWen;
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
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單一組字式部件()
	{
		String 組字式 = "意";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		檢查意部件((ChineseCharacterWen) 部件樹陣列.get(0));
	}

	@Test
	public void 單層組字式字數()
	{
		String 組字式 = "⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 單層組字式部件()
	{
		String 組字式 = "⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		檢查傳部件((ChineseCharacterTzu) 部件樹陣列.get(0));
	}

	@Test
	public void 多層組字式字數()
	{
		String 組字式 = "⿰矛⿱攵力";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		assertEquals(1, 部件樹陣列.size());
	}

	@Test
	public void 多層組字式部件()
	{
		String 組字式 = "⿰矛⿱攵力";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		檢查務部件((ChineseCharacterTzu) 部件樹陣列.get(0));
	}

	@Test
	public void 空組字式字數()
	{
		String 組字式 = "";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		assertEquals(部件樹陣列.size(), 0);
	}

	@Test
	public void 兩字組字式字數()
	{
		String 組字式 = "意⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		assertEquals(2, 部件樹陣列.size());
	}

	@Test
	public void 兩字組字式部件()
	{
		String 組字式 = "意⿰專";
		漢字序列分析工具 解析工具 = new 漢字序列分析工具(組字式, 展開式);
		Vector<ChineseCharacter> 部件樹陣列 = 解析工具.parseText();
		檢查意部件(部件樹陣列.get(0));
		檢查傳部件(部件樹陣列.get(1));
	}

	@Test
	public void 第一字無完整組字式字數()
	{
		fail("還沒做");
	}

	@Test
	public void 第二字無完整組字式字數()
	{
		fail("還沒做");
	}

	private void 檢查意部件(ChineseCharacter 部件)
	{
		ChineseCharacterWen 文部件意 = (ChineseCharacterWen) 部件;
		assertEquals("意".codePointAt(0), 文部件意.getCodePoint());
	}

	private void 檢查傳部件(ChineseCharacter 部件)
	{
		ChineseCharacterTzu 字部件傳 = (ChineseCharacterTzu) 部件;
		assertEquals(ChineseCharacterTzuCombinationType.horizontal,
				字部件傳.getType());
		assertEquals(2, 字部件傳.getChildren().length);
		ChineseCharacterWen 文部件人 = (ChineseCharacterWen) 字部件傳.getChildren()[0];
		ChineseCharacterWen 文部件專 = (ChineseCharacterWen) 字部件傳.getChildren()[1];
		assertEquals("".codePointAt(0), 文部件人.getCodePoint());
		assertEquals("專".codePointAt(0), 文部件專.getCodePoint());
	}

	private void 檢查務部件(ChineseCharacter 部件)
	{
		ChineseCharacterTzu 字部件務 = (ChineseCharacterTzu) 部件;
		assertEquals(ChineseCharacterTzuCombinationType.horizontal,
				字部件務.getType());
		assertEquals(字部件務.getChildren().length, 2);
		ChineseCharacterWen 文部件矛 = (ChineseCharacterWen) 字部件務.getChildren()[0];
		assertEquals("矛".codePointAt(0), 文部件矛.getCodePoint());
		ChineseCharacterTzu 上下字部件 = (ChineseCharacterTzu) 字部件務.getChildren()[1];
		assertEquals(ChineseCharacterTzuCombinationType.vertical,
				上下字部件.getType());
		assertEquals(2, 字部件務.getChildren().length);
		ChineseCharacterWen 文部件攵 = (ChineseCharacterWen) 上下字部件.getChildren()[0];
		ChineseCharacterWen 文部件力 = (ChineseCharacterWen) 上下字部件.getChildren()[1];
		assertEquals("攵".codePointAt(0), 文部件攵.getCodePoint());
		assertEquals("力".codePointAt(0), 文部件力.getCodePoint());
	}
}
