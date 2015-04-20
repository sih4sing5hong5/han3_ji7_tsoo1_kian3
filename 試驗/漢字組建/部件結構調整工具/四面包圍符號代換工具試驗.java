package 漢字組建.部件結構調整工具;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.部件結構調整工具.展開式免查詢;
import 漢字組建.解析工具.組字式序列解析工具;
import 漢字組建.部件.組合方式;
import 漢字組建.部件.部件;

public class 四面包圍符號代換工具試驗
{
	四面包圍符號代換工具 代換工具 = new 四面包圍符號代換工具();

	@Test
	public void 設定並且代換()
	{
		代換工具.設定(組合方式.上下左包圍, new String[] { "匚",// 音方，方器義
				"⼖",// 音夕，藏匿義
		});
		組字式序列解析工具 解析工具 = new 組字式序列解析工具("⿴匚甲", new 展開式免查詢());
		部件 部件樹 = 解析工具.解析().get(0);
		String 原本部件樹組字式 = 部件樹.樹狀結構組字式();
		部件 新部件樹 = 代換工具.代換(部件樹)
		assertEquals(原本部件樹組字式, 部件樹.樹狀結構組字式());
		assertEquals("⿷匚甲", 部件樹.樹狀結構組字式());
	}

	@Test(expected=部件不存在例外.class)
	public void 部件不存在()
	{
		組字式序列解析工具 解析工具 = new 組字式序列解析工具("⿴匚甲", new 展開式免查詢());
		部件 部件樹 = 解析工具.解析().get(0);
		String 原本部件樹組字式 = 部件樹.樹狀結構組字式();
		部件 新部件樹 = 代換工具.代換(部件樹)
	}

	@Test(expected = 部件重覆例外.class)
	public void 有一樣的符號衝突()
	{
		代換工具.設定(組合方式.上下左包圍, new String[] { "匚", "⼖", });
		代換工具.設定(組合方式.左上包圍, new String[] { "匚", });
	}
}
