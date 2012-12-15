package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.setting.ChineseCharacterTypeSetter;


public class 字型參考設定工具 //implements ChineseCharacterTypeSetter
{;
/** 活字的渲染屬性 */
protected FontRenderContext 字體渲染屬性;
// * 先共樹仔ㄋㄚ展開ㄋㄚ變字串，
// * 找部件（目前的節點）
//｛
//	如果 目前節點的展開式＝＝資料庫其中一筆展開式
//		回傳 其字集編碼
//	如果 非不可拆分部件
//		資料表[0]=空的組字式
//		for i=1; i<=主結構.size();++i
//			如果資料表[i-1]有組字式
//				部件集合 ＝ 找部件（資料表）
//				有的話使用者字體有此字型
//					資料表[i]=主結構組合符 資料表[j-1] 部件集合
//			for(int j=i-1;j>=1;--j)
//			｛
//				如果資料表[j-1]有組字式
//					部件集合＝去儲存媒體找是否有字主結構是｛部件j,部件（j＋1）,...,部件i｝
//					有的話使用者字體有此字型
//						資料表[i]=主結構組合符 資料表[j-1] 部件集合
//			｝
//	如果 是不可拆分部件
//		則去公用部件庫找其他字體的部件，或是用空白、方框等其他部件取代
//		回傳
//｝
 
//	@Override
//	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
//			ChineseCharacterWen chineseCharacterWen)
//	{
////		RectangularArea rectangularArea = null;
////		if (font.canDisplay(chineseCharacterWen.getCodePoint()))
////		{
////			GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
////					chineseCharacterWen.getChars());
////			rectangularArea = new RectangularArea(glyphVector.getOutline());
////		}
////		else
////		{
////			rectangularArea = findWenForNoBuiltIn(chineseCharacterWen);
////		}
////		rectangularArea.moveToOrigin();
////
////		PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
////				parent, chineseCharacterWen, rectangularArea);
//		return pieceMovableTypeWen;
//	}
//
//	@Override
//	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
//			ChineseCharacterTzu chineseCharacterTzu)
//	{
////		RectangularArea rectangularArea = new RectangularArea();
////		rectangularArea.setTerritory(tzuModelTerritory);
////		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
////				parent, chineseCharacterTzu, rectangularArea);
////
////		setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);
//
//		return pieceMovableTypeTzu;
//	}
}
