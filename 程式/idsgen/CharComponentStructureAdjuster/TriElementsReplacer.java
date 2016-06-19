package idsgen.CharComponentStructureAdjuster;

import idsgen.charcomponent.FinalCharComponent;
import idsgen.charcomponent.CompositionMethods;
import idsgen.charcomponent.CharComponent;

public class TriElementsReplacer
{
	public CharComponent 三元素組合代換成二元素(CharComponent CharComponent)
	{
		if (CompositionMethods.isCombinationType(CharComponent.Unicode編號()))
		{
			FinalCharComponent FinalCharComponent = (FinalCharComponent) CharComponent;
			CompositionMethods 方式 = FinalCharComponent.CompositionMethods();
			if (方式 == CompositionMethods.左右三個合併)
			{
				FinalCharComponent 第一層字部件 = new FinalCharComponent(CompositionMethods.左右合併.toCodePoint());
				FinalCharComponent 第二層右邊字部件 = new FinalCharComponent(CompositionMethods.左右合併.toCodePoint());
				第一層字部件.底下元素()[0] = FinalCharComponent.底下元素()[0];
				第一層字部件.底下元素()[1] = 第二層右邊字部件;
				第二層右邊字部件.底下元素()[0] = FinalCharComponent.底下元素()[1];
				第二層右邊字部件.底下元素()[1] = FinalCharComponent.底下元素()[2];
				CharComponent = 第一層字部件;
			}
			else if (方式 == CompositionMethods.上下三個合併)
			{
				FinalCharComponent 第一層字部件 = new FinalCharComponent(CompositionMethods.上下合併.toCodePoint());
				FinalCharComponent 第二層右邊字部件 = new FinalCharComponent(CompositionMethods.上下合併.toCodePoint());
				第一層字部件.底下元素()[0] = FinalCharComponent.底下元素()[0];
				第一層字部件.底下元素()[1] = 第二層右邊字部件;
				第二層右邊字部件.底下元素()[0] = FinalCharComponent.底下元素()[1];
				第二層右邊字部件.底下元素()[1] = FinalCharComponent.底下元素()[2];
				CharComponent = 第一層字部件;
			}
			for (int i = 0; i < FinalCharComponent.底下元素().length; i++)
				FinalCharComponent.底下元素()[i] = 三元素組合代換成二元素(FinalCharComponent.底下元素()[i]);
		}
		return CharComponent;
	}
}
