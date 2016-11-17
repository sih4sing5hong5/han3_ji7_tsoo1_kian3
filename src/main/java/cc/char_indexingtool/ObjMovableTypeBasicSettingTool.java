package cc.char_indexingtool;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.movabletype.SeprateMovabletype;
import idsrend.charcomponent.NonFinalCharComponent;
import cc.movabletype.PlaneGeometry;

/**
 * 這是予物件活字設定工具一个共用的型態。用一層較懸頂的收集逐家攏愛用到的物件。
 * 
 * @author Ihc
 * 
 */
public abstract class ObjMovableTypeBasicSettingTool implements ChineseCharacterTypeSetter
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
	protected ObjMovableTypeBasicSettingTool(Rectangle2D tzuModelTerritory,
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
	protected SeprateMovabletype findWenForNoBuiltIn(NonFinalCharComponent chineseCharacterWen)
	{
		return new SeprateMovabletype(new PlaneGeometry(pieceForNoBuiltInWen));
	}

}
