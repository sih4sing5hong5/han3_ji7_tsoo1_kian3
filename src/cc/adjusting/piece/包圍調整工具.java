package cc.adjusting.piece;

import java.util.Vector;

import cc.moveable_type.rectangular_area.RectangularArea;

public class 包圍調整工具
{
	/* 使用此包圍工具的調整工具，並使用其自身合併相關函式 */
	// protected MergePieceAdjuster 調整工具;
	protected 縮放接合模組 包圍模組;
	protected Vector<二元搜尋貼合工具> 包圍工具列;
	protected Vector<二元搜尋貼合工具> 平推工具列;
	protected Vector<平推黏合模組> 平推模組列;

	public 包圍調整工具(縮放接合模組 包圍模組, MergePieceAdjuster 調整工具)
	{
		// this.調整工具 = 調整工具;
		this.包圍模組 = 包圍模組;
		包圍工具列 = new Vector<二元搜尋貼合工具>();
		包圍工具列.add(new 二元搜尋間隔工具());
		包圍工具列.add(new 二元搜尋貼合工具());

		平推工具列 = new Vector<二元搜尋貼合工具>();
		平推工具列.add(new 二元搜尋間隔工具());
		平推工具列.add(new 二元搜尋微貼工具());
		平推模組列 = new Vector<平推黏合模組>();
		平推模組列.add(new 上推黏合模組(調整工具));
		平推模組列.add(new 下推黏合模組(調整工具));
		平推模組列.add(new 左推黏合模組(調整工具));
		平推模組列.add(new 右推黏合模組(調整工具));
	}

	public void 組合(RectangularArea[] 活字物件)
	{
		包圍工具列.elementAt(0).執行(包圍模組, 活字物件);
		活字物件 = 包圍模組.取得調整後活字物件();
		for (二元搜尋貼合工具 工具 : 平推工具列)
			for (平推黏合模組 模組 : 平推模組列)
			{
				工具.執行(模組, 活字物件);
				活字物件 = 模組.取得調整後活字物件();
			}
		包圍工具列.elementAt(1).執行(包圍模組, 活字物件);
		活字物件 = 包圍模組.取得調整後活字物件();
		return;
	}
}
