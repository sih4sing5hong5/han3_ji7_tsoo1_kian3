package idsrend.CharComponentStructureAdjuster;

import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;

/**
 * 僅限雙組合符號的正規化型態。
 * 
 * @author Ihc
 */
public class IDSnormalizer
{
	/**
	 * 正規化部件結構。
	 * 
	 * @param CharComponent
	 *            欲正規化的物件
	 */
	public CharComponent 正規化(CharComponent CharComponent)
	{
		int 部件編碼 = ((CharComponent) CharComponent).Unicode編號();
		if (CompositionMethods.isCombinationType(部件編碼))
		{
			FinalCharComponent FinalCharComponent = (FinalCharComponent) CharComponent;
			if (FinalCharComponent.CompositionMethods().有結合律無()
					&& FinalCharComponent.底下元素()[0].Unicode編號() == FinalCharComponent.Unicode編號())
			{
				FinalCharComponent 第一層字部件 = new FinalCharComponent(部件編碼);
				FinalCharComponent 第二層右邊字部件 = new FinalCharComponent(部件編碼);
				第一層字部件.底下元素()[0] = ((FinalCharComponent) FinalCharComponent.底下元素()[0]).底下元素()[0];
				第一層字部件.底下元素()[1] = 第二層右邊字部件;
				第二層右邊字部件.底下元素()[0] = ((FinalCharComponent) FinalCharComponent.底下元素()[0]).底下元素()[1];
				第二層右邊字部件.底下元素()[1] = FinalCharComponent.底下元素()[1];
				return 正規化(第一層字部件);
			}
			else
			{
				FinalCharComponent 新字部件 = new FinalCharComponent(部件編碼);
				for (int i = 0; i < 新字部件.底下元素().length; i++)
					新字部件.底下元素()[i] = (CharComponent) 正規化(FinalCharComponent.底下元素()[i]);
				return 新字部件;
			}
		}
		return (CharComponent) CharComponent;
	}
}
