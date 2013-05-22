package cc.adjusting.piece;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;

class 注音符號分開工具
{
	protected 注音符號分類工具 分類工具;

	注音符號分開工具(注音符號分類工具 分類工具)
	{
		this.分類工具 = 分類工具;
	}

	void 分開(PieceMovableType 活字, 注音符號分類 分類)
	{
		if (活字 instanceof PieceMovableTypeTzu)
		{
			PieceMovableTypeTzu 字活字 = (PieceMovableTypeTzu) 活字;
			if (((ChineseCharacterTzu) 字活字.getChineseCharacter()).getType() != ChineseCharacterTzuCombinationType.注音符號)
			{
				System.out.println("注音下跤有毋是注音的組合符號");
			}
			分開((PieceMovableType) 字活字.getChildren()[0], 分類);
			分開((PieceMovableType) 字活字.getChildren()[1], 分類);
		}
		else if (活字 instanceof PieceMovableTypeWen)
		{
			PieceMovableTypeWen 文活字 = (PieceMovableTypeWen) 活字;
			int 控制碼 = 文活字.getChineseCharacter().getCodePoint();
			switch (分類.目前類別)
			{
			case 輕聲:
				if (分類工具.是毋是輕聲(控制碼))
				{
					分類.輕聲.add(文活字.getPiece());
					break;
				}
			case 聲韻號:
				if (分類工具.是毋是聲韻號(控制碼))
				{
					分類.聲韻號.add(文活字.getPiece());
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
				// if (分類工具.是毋是聲韻號(控制碼))
				// {
				// 分類.聲韻號.add(文活字.getPiece());
				// break;
				// }
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
