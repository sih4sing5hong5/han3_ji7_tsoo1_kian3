package cc.setting.piece;

import java.awt.BasicStroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;

import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.core.ChineseCharacterWen;
import cc.core.組字式部件;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.RectangularArea;
import cc.tool.database.字串與控制碼轉換;

public class 字型參考設定工具 extends 物件活字基礎設定工具
{
	展開式查通用字型編號 查通用字型編號;
	protected 通用字體 字體;
	/** 活字的渲染屬性 */
	protected FontRenderContext 字體渲染屬性;
	
	private final int 空空無物件=-7;

	public 字型參考設定工具(展開式查通用字型編號 查通用字型編號, 通用字體 字體, FontRenderContext 字體渲染屬性)
	{
		super(null, null);
		this.查通用字型編號 = 查通用字型編號;
		this.字體 = 字體;
		this.字體渲染屬性 = 字體渲染屬性;

		int 標準字統一碼 = 字串與控制碼轉換.轉換成控制碼(物件活字基礎設定工具.tzuModelCharacter)[0];
		GlyphVector 標準字字型 = 字體.提這个字型(字體渲染屬性, 標準字統一碼);
		this.tzuModelTerritory = 標準字字型.getOutline().getBounds2D();
		BasicStroke basicStroke = new BasicStroke();
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(tzuModelTerritory));
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterWen chineseCharacterWen)
	{
		RectangularArea 物件活字 = 查物件活字(new 通用字型號碼(
				chineseCharacterWen.getCodePoint()));
		return new PieceMovableTypeWen(parent, chineseCharacterWen, 物件活字);
	}

	private RectangularArea 查物件活字(通用字型號碼 字型號碼)
	{
		RectangularArea 物件活字 = null;
		if (字體.有這个字型無(字型號碼))
		{
			GlyphVector glyphVector = 字體.提這个字型(字體渲染屬性, 字型號碼);
			物件活字 = new RectangularArea(glyphVector.getOutline());
		}
		else
		{
			物件活字 = findWenForNoBuiltIn(null);
		}
		物件活字.moveToOrigin();// TODO 伊佮這个資訊攏提掉，交予排版系統去處理，但是會有一字問題。
		return 物件活字;
	}

	@Override
	public PieceMovableType setTzu(ChineseCharacterMovableTypeTzu parent,
			ChineseCharacterTzu chineseCharacterTzu)
	{
		ChineseCharacter[] 主要結構 = 揣主要結構元件(chineseCharacterTzu);
		int[] 頭前彼个位址 = new int[主要結構.length];// -1代表空空，遏袂處理。位址代表有彼个元件有考慮到。
		for (int i = 0; i < 頭前彼个位址.length; i++)
			頭前彼个位址[i] =空空無物件;
		PieceMovableType[] 部件組合 = new PieceMovableType[主要結構.length];
		/** 保證開始位址前的攏有揣到 */
		for (int 開始位址 = 0; 開始位址 < 主要結構.length; 開始位址++)
		{
			/** [開始位址,結束位址) */
			for (int 結束位址 = 開始位址 + 1; 結束位址 <= 主要結構.length; 結束位址++)
			{
				if (頭前彼个位址[結束位址 - 1] == 空空無物件)
				{
//					System.out.println("開始位址="+開始位址+" 結束位址="+結束位址);
					/** 產生展開式 */
					StringBuilder 結構展開式 = new StringBuilder();
					for (int i = 開始位址; i < 結束位址; ++i)
					{
						if (i + 1 < 結束位址)
							結構展開式.append(chineseCharacterTzu.getChars());
						結構展開式.append(((組字式部件) 主要結構[i]).提到組字式());
					}
					通用字型號碼 字型號碼 = 查通用字型編號.查通用字型編號(結構展開式.toString());
					/** [開始位址,結束位址) 佇資料庫內底 */
					if (字型號碼 != null)
					{
//						System.out.println(字體.有這个字型無(字型號碼));
						if (字體.有這个字型無(字型號碼))
						{
							頭前彼个位址[結束位址 - 1] = 開始位址 - 1;
							// 部件組合[結束位址-1] ="資料庫抓來彼配字體" ;
							部件組合[結束位址 - 1] = new PieceMovableTypeWen(null,
									null, 查物件活字(字型號碼));// TODO null敢袂有問題？
						}
					}
				}

			}
			if (頭前彼个位址[開始位址] == 空空無物件)
			{
				頭前彼个位址[開始位址] = 開始位址 - 1;
				部件組合[開始位址] = (PieceMovableType) 主要結構[開始位址].typeset(this, null);
			}
		}
		int 目前位址 = 部件組合.length - 1;
		PieceMovableType 目前元件 = 部件組合[目前位址];
		目前位址 = 頭前彼个位址[目前位址];
		RectangularArea rectangularArea = new RectangularArea();
		rectangularArea.setTerritory(tzuModelTerritory);// TODO 有必要無？會當公家用無？
		while (目前位址 >= 0)
		{
			PieceMovableTypeTzu 新元件 = new PieceMovableTypeTzu(null,
					chineseCharacterTzu, rectangularArea);// TODO愛產生新的字部件無？
			新元件.getChildren()[1] = (ChineseCharacterMovableType) 目前元件;
			新元件.getChildren()[0] = (ChineseCharacterMovableType) 部件組合[目前位址];
			目前元件 = 新元件;
			目前位址 = 頭前彼个位址[目前位址];
		}

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
			else if (這馬字部件.getType().有結合律無())
			{
				System.out.println("有三个以上的部件組合符號！！");// TODO log
				return 這馬字部件.getChildren();
			}
			else
			{
				return 這馬字部件.getChildren();
			}
		}
		else
		{
			return new ChineseCharacter[] { 部件 };
		}
	}
}
