package cc.adjusting.piece;

import cc.tool.database.字串與控制碼轉換;

class 注音符號分類工具
{
	int[] 輕聲 = 字串與控制碼轉換.轉換成控制碼("˙");
	int[] 聲韻號 = 字串與控制碼轉換.轉換成控制碼("ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙ" + "ㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦ"
			+ "ㄧㄨㄩ" + "ㄪㄫㄬ" + "ㄭㄮ" + "ㆠㆡㆢㆣ" + "ㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳ");
	int[] 調號 = 字串與控制碼轉換.轉換成控制碼("ˊˇˋ˙˪˫|");

	boolean 是毋是輕聲(int 控制碼)
	{
		for (int 符號控制碼 : 輕聲)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}

	boolean 是毋是聲韻號(int 控制碼)
	{
		for (int 符號控制碼 : 聲韻號)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}

	boolean 是毋是調號(int 控制碼)
	{
		for (int 符號控制碼 : 調號)
			if (符號控制碼 == 控制碼)
				return true;
		return false;
	}
}
