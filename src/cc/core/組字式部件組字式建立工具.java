package cc.core;

import cc.tool.database.字串與控制碼轉換;

class 組字式部件組字式建立工具
{
	String 建立組字式(組字式文部件 文部件)
	{
		文部件.設定組字式(字串與控制碼轉換.轉換成字串(文部件.getCodePoint()));
		return 文部件.提到組字式();
	}

	String 建立組字式(組字式字部件 字部件)
	{
		StringBuilder 組字式 = new StringBuilder(字部件.getType().toString());
		for (ChineseCharacter 部件 : 字部件.getChildren())
		{
			組字式部件 子部件 = (組字式部件) 部件;
			組字式.append(子部件.建立組字式(this));
		}
		字部件.設定組字式(組字式.toString());
		return 字部件.提到組字式();
	}
}
