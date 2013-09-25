package cc.setting.piece;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.ChineseCharacterWen;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.活字單元;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 這是予物件活字設定工具一个共用的型態。用一層較懸頂的收集逐家攏愛用到的物件。
 * 
 * @author Ihc
 * 
 */
public abstract class 物件活字基礎設定工具 implements ChineseCharacterTypeSetter
{
	/** 合體字組合符號參考的規範字 */
	static protected final String tzuModelCharacter = "意";
	/** 合體字組合符號參考的位置及大小 */
	protected Rectangle2D tzuModelTerritory;
	/** 獨體字缺字的替代物件 */
	protected Area pieceForNoBuiltInWen;

	/**
	 * 準備設定工具。
	 * 
	 * @param tzuModelTerritory
	 *            合體字組合符號參考的位置及大小
	 * @param pieceForNoBuiltInWen
	 *            獨體字缺字的替代物件
	 */
	protected 物件活字基礎設定工具(Rectangle2D tzuModelTerritory,
			Area pieceForNoBuiltInWen)
	{
		this.tzuModelTerritory = tzuModelTerritory;
		this.pieceForNoBuiltInWen = pieceForNoBuiltInWen;
	}

	/**
	 * 欠字型的替代方案。
	 * 
	 * @param chineseCharacterWen
	 *            所缺的字部件
	 * @return 替代圖案或文字
	 */
	protected 活字單元 findWenForNoBuiltIn(ChineseCharacterWen chineseCharacterWen)
	{
		return new 分離活字(new 平面幾何(pieceForNoBuiltInWen));
	}

}
