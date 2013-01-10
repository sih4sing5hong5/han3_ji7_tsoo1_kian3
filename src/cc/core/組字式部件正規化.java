package cc.core;

/**
 * 僅限雙組合符號的正規化型態。
 * 
 * @author Ihc
 */
public class 組字式部件正規化
{
	/**
	 * 正規化部件結構。
	 * 
	 * @param 部件
	 *            欲正規化的物件
	 */
	public void 正規化(ChineseCharacter 部件)
	{
		if (ChineseCharacterTzuCombinationType.isCombinationType(部件
				.getCodePoint()))
		{
			ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 部件;
			if (字部件.getType().有結合律無())
			{
				if (字部件.getChildren().length == 2)
				{
					// 倒爿部件.getCodePoint() == 字部件.getCodePoint())
					while (字部件.getChildren()[0].getCodePoint() == 字部件
							.getCodePoint())
					{
						ChineseCharacter 倒爿部件 = 字部件.getChildren()[0];
						ChineseCharacterTzu 倒爿字部件 = (ChineseCharacterTzu) 倒爿部件;
						ChineseCharacter 倒倒爿部件 = 倒爿字部件.getChildren()[0], 倒正爿部件 = 倒爿字部件
								.getChildren()[1], 正爿部件 = 字部件.getChildren()[1];
						字部件.getChildren()[0] = 倒倒爿部件;
						字部件.getChildren()[1] = 倒爿部件;
						倒爿字部件.getChildren()[0] = 倒正爿部件;
						倒爿字部件.getChildren()[1] = 正爿部件;
					}
				}
				else
				{
					System.out.println("有三个以上的部件組合符號！！");
				}
			}
			for (ChineseCharacter 子部件 : 字部件.getChildren())
				正規化(子部件);
		}
		return;
	}
}
