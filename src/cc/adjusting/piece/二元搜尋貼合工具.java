package cc.adjusting.piece;

import java.awt.geom.AffineTransform;

import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.RectangularArea;

public class 二元搜尋貼合工具
{
	private 二元搜尋貼合模組 模組;

	public 二元搜尋貼合工具(二元搜尋貼合模組 模組)
	{
		this.模組 = 模組;
	}

	public void 執行(PieceMovableTypeTzu 物件活字)
	{
		RectangularArea[] 活字物件 = new RectangularArea[物件活字.getChildren().length];
		for (int i = 0; i < 物件活字.getChildren().length; ++i)
			活字物件[i] = new RectangularArea(
					((PieceMovableType) 物件活字.getChildren()[i]).getPiece());
		執行(活字物件);
		return;
	}

	public void 執行(RectangularArea[] 活字物件)
	{
		模組.初使化(活字物件);

		double mininumValue = 模組.下限初始值(), maxinumValue = 模組.上限初始值();
		while (mininumValue + 模組.取得精確度() < maxinumValue)
		{
			double middleValue = 0.5 * (mininumValue + maxinumValue);
			模組.變形處理(middleValue);
			if (模組.搜尋判斷條件() ^ 模組.條件成立變大())
				maxinumValue = middleValue;
			else
				mininumValue = middleValue;
		}

		double 活字寬度 = 模組.活字寬度();
		模組.變形處理(mininumValue - 活字寬度 * 2.6 * 縮放參數());

		double 活字相斥值 = 模組.活字相斥值();
		if (活字相斥值 > 1.6)// TODO 人工參數
			mininumValue += 活字寬度 * 3.0 * 縮放參數();
		else if (活字相斥值 > 0.8)
			mininumValue += 0.0;
		else
			mininumValue += -活字寬度 * 1.2 * 縮放參數();

		模組.變形處理(mininumValue);
		模組.最後處理();
		return;
	}

	protected double 縮放參數()
	{
		if (模組.條件成立變大())
			return 1.0;
		return -1.0;
	}
}
