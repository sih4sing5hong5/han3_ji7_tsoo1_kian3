package cc.layouttools;

import cc.movabletype.PieceMovableType;
import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.PieceMovableTypeWen;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;

/**
 * 用遞迴方式，照注音符號類別來分類的工具。
 * 
 * @author Ihc
 */
class ZhuyinSeparater
{
	/** 決定啥物注音會當用，愛下佇佗，主要的參考 */
	protected ZhuyinClassifier 分類工具;

	/**
	 * 建立一个分開工具。
	 * 
	 * @param 分類工具
	 *            參考的分類方法
	 */
	ZhuyinSeparater(ZhuyinClassifier 分類工具)
	{
		this.分類工具 = 分類工具;
	}

	/**
	 * 照分類工具共活字下跤的注音分開，存入分類。
	 * 
	 * @param 活字
	 *            目前愛處理的活字結構樹根
	 * @param 分類
	 *            分類狀況物件
	 */
	void 分開(PieceMovableType 活字, ZhuyinSystematics 分類)
	{
		if (活字 instanceof PieceMovableTypeTzu)
		{
			PieceMovableTypeTzu 字活字 = (PieceMovableTypeTzu) 活字;
			if (((FinalCharComponent) 字活字.getChineseCharacter()).CompositionMethods() != CompositionMethods.注音符號)
			{
				System.out.println("注音下跤有毋是注音的組合符號");
			}
			分開((PieceMovableType) 字活字.getChildren()[0], 分類);
			分開((PieceMovableType) 字活字.getChildren()[1], 分類);
		}
		else if (活字 instanceof PieceMovableTypeWen)
		{
			PieceMovableTypeWen 文活字 = (PieceMovableTypeWen) 活字;
			int 控制碼 = 文活字.getChineseCharacter().Unicode編號();
			switch (分類.目前類別)
			{
			case 輕聲:
				if (分類工具.是毋是輕聲(控制碼))
				{
					分類.輕聲.add(文活字.getPiece());
					break;
				}
			case 聲韻號:
				if (分類工具.是毋是聲韻(控制碼))
				{
					分類.聲韻.add(文活字.getPiece());
					break;
				}
			case 調號:
				if (分類工具.是毋是調號(控制碼))
				{
					分類.調號.add(文活字.getPiece());
					break;
				}
			default:
				System.out.println("使用者注音結構有問題");
				if (分類工具.是毋是聲韻(控制碼))
				{
					分類.聲韻.add(文活字.getPiece());
					break;
				}
				分類.調號.add(文活字.getPiece());
				break;
			}
		}
		else
		{
			System.out.println("注音下跤活字結構有問題");
		}
	}
}
