package 漢字組建.部件結構調整工具;

import cc.core.部件;
import cc.core.字部件;
import cc.core.組合方式;
import cc.core.字部件;

public class 三元素符號代換工具
{
	public 部件 三元素組合代換成二元素(部件 部件)
	{
		if (組合方式.isCombinationType(部件.getCodePoint()))
		{
			字部件 字部件 = (字部件) 部件;
			組合方式 方式 = 字部件.getType();
			if (方式 == 組合方式.左右三個合併)
			{
				字部件 第一層字部件 = new 字部件(null,
						組合方式.左右合併.toCodePoint());
				字部件 第二層右邊字部件 = new 字部件(第一層字部件,
						組合方式.左右合併.toCodePoint());
				第一層字部件.getChildren()[0] = 字部件.getChildren()[0];
				第一層字部件.getChildren()[1] = 第二層右邊字部件;
				第二層右邊字部件.getChildren()[0] = 字部件.getChildren()[1];
				第二層右邊字部件.getChildren()[1] = 字部件.getChildren()[2];
				部件 = 第一層字部件;
			}
			else if (方式 == 組合方式.上下三個合併)
			{
				字部件 第一層字部件 = new 字部件(null,
						組合方式.上下合併.toCodePoint());
				字部件 第二層右邊字部件 = new 字部件(第一層字部件,
						組合方式.上下合併.toCodePoint());
				第一層字部件.getChildren()[0] = 字部件.getChildren()[0];
				第一層字部件.getChildren()[1] = 第二層右邊字部件;
				第二層右邊字部件.getChildren()[0] = 字部件.getChildren()[1];
				第二層右邊字部件.getChildren()[1] = 字部件.getChildren()[2];
				部件 = 第一層字部件;
			}
			for (int i = 0; i < 字部件.getChildren().length; i++)
				字部件.getChildren()[i] = 三元素組合代換成二元素(字部件.getChildren()[i]);
		}
		return 部件;
	}
}
