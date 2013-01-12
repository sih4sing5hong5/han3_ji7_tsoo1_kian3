package cc.setting.piece;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.core.ChineseCharacterWen;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;

public class 字型參考設定工具 extends 物件活字基礎設定工具
{
	;
	/** 活字的渲染屬性 */
	protected FontRenderContext 字體渲染屬性;
	protected 字體介面 字體;

	public 字型參考設定工具(字體介面 字體)
	{
		super(null, null);
		this.字體 = 字體;
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		RectangularArea rectangularArea = null;
		if (字體.是否可顯示該字型(chineseCharacterWen.getCodePoint()))
		{
			GlyphVector glyphVector = 字體.取得該字型(字體渲染屬性,
					chineseCharacterWen.getCodePoint());
			rectangularArea = new RectangularArea(glyphVector.getOutline());
		}
		else
		{
			rectangularArea = findWenForNoBuiltIn(chineseCharacterWen);
		}
		rectangularArea.moveToOrigin();// TODO 伊佮這个資訊攏提掉，交予排版系統去處理，但是會有一字問題。

		PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
				parent, chineseCharacterWen, rectangularArea);
		return pieceMovableTypeWen;
	}

	// * 先共樹仔ㄋㄚ展開ㄋㄚ變字串，
	// * 找部件（目前的節點）
	// ｛
	// 如果 目前節點的展開式＝＝資料庫其中一筆展開式
	// 回傳 其字集編碼
	// 如果 非不可拆分部件
	// 資料表[0]=空的組字式
	// for i=1; i<=主結構.size();++i
	// 如果資料表[i-1]有組字式
	// 部件集合 ＝ 找部件（資料表）
	// 有的話使用者字體有此字型
	// 資料表[i]=主結構組合符 資料表[j-1] 部件集合
	// for(int j=i-1;j>=1;--j)
	// ｛
	// 如果資料表[j-1]有組字式
	// 部件集合＝去儲存媒體找是否有字主結構是｛部件j,部件（j＋1）,...,部件i｝
	// 有的話使用者字體有此字型
	// 資料表[i]=主結構組合符 資料表[j-1] 部件集合
	// ｝
	// 如果 是不可拆分部件
	// 則去公用部件庫找其他字體的部件，或是用空白、方框等其他部件取代
	// 回傳
	// ｝

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		ChineseCharacter[] 主要結構 = 揣主要結構元件(chineseCharacterTzu);
		int[] 頭前彼个位址 = new int[主要結構.length];// -1代表空空，遏袂處理。位址代表有彼个元件有考慮到。
		for (int i = 0; i < 頭前彼个位址.length; i++)
			頭前彼个位址[i] = -1;
		PieceMovableTypeTzu[] 部件組合 = new PieceMovableTypeTzu[主要結構.length];
		/** 保證開始位址前的攏有揣到 */
		for (int 開始位址 = 0; 開始位址 < 主要結構.length; 開始位址++)
		{
			for (int 結束位址 = 開始位址 + 1; 結束位址 <= 主要結構.length; 結束位址++)// [開始位址,結束位址)
			{
				if (頭前彼个位址[結束位址] == -1)
				{
					// 產生字串
					if ("[開始位址,結束位址) 佇資料庫內底" == null)
					{
						頭前彼个位址[結束位址] = 開始位址 - 1;
						// 部件組合[結束位址] ="資料庫抓來彼配字體" ;
					}
				}

			}
			if (頭前彼个位址[開始位址] == -1)
			{
				頭前彼个位址[開始位址] = 開始位址 - 1;
				部件組合[開始位址] = (PieceMovableTypeTzu) 主要結構[開始位址].typeset(this,
						null);
			}
		}
		int 目前位址 = 部件組合.length - 1;
		PieceMovableTypeTzu 目前元件 = 部件組合[目前位址];
		目前位址 = 頭前彼个位址[目前位址];
		RectangularArea rectangularArea = new RectangularArea();
		rectangularArea.setTerritory(tzuModelTerritory);// TODO 有必要無？會當公家用無？
		while (目前位址 >= 0)
		{
			PieceMovableTypeTzu 新元件 = new PieceMovableTypeTzu(null,
					chineseCharacterTzu, rectangularArea);// TODO愛產生新的字部件無？
			新元件.getChildren()[1] = 目前元件;
			新元件.getChildren()[0] = 部件組合[目前位址];
			目前元件 = 新元件;
			目前位址 = 頭前彼个位址[目前位址];
		}

		// 反向建

		// RectangularArea rectangularArea = new RectangularArea();
		// rectangularArea.setTerritory(tzuModelTerritory);
		// PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
		// parent, chineseCharacterTzu, rectangularArea);
		//
		// setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);

		return 目前元件;
	}

	private ChineseCharacter[] 揣主要結構元件(ChineseCharacter 部件)
	{
		if (ChineseCharacterTzuCombinationType.isCombinationType(部件
				.getCodePoint()))
		{
			ChineseCharacterTzu 這馬字部件 = (ChineseCharacterTzu) 部件;
			if (這馬字部件.getType().有結合律無() && 這馬字部件.getChildren().length == 2)
			{
				int 元件數量 = 2;
				// 倒爿部件.getCodePoint() == 字部件.getCodePoint())
				while (這馬字部件.getChildren()[1].getCodePoint() == 這馬字部件
						.getCodePoint())
				{
					元件數量++;
					這馬字部件 = (ChineseCharacterTzu) 這馬字部件.getChildren()[1];
				}
				這馬字部件 = (ChineseCharacterTzu) 部件;
				ChineseCharacter[] 元件 = new ChineseCharacter[元件數量];
				int 陣列位置 = 0;
				while (這馬字部件.getChildren()[1].getCodePoint() == 這馬字部件
						.getCodePoint())
				{
					元件[陣列位置++] = 這馬字部件.getChildren()[0];
					這馬字部件 = (ChineseCharacterTzu) 這馬字部件.getChildren()[1];
				}
				元件[陣列位置++] = 這馬字部件.getChildren()[0];
				元件[陣列位置++] = 這馬字部件.getChildren()[1];
				return 元件;
			}
			else
			{
				System.out.println("有三个以上的部件組合符號！！");// TODO log
				return 這馬字部件.getChildren();
			}
		}
		else
		{
			return new ChineseCharacter[] { 部件 };
		}
	}
}
