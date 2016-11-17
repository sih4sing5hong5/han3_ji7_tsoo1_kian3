package cc.char_indexingtool;

import java.awt.BasicStroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.movabletype.ChineseCharacterMovableType;
import cc.movabletype.ChineseCharacterMovableTypeTzu;
import cc.movabletype.PieceMovableType;
import cc.movabletype.PieceMovableTypeTzu;
import cc.movabletype.PieceMovableTypeWen;
import cc.movabletype.SeprateMovabletype;
import cc.stroketool.MkeSeparateMovableType_Bolder;
import cc.movabletype.PlaneGeometry;
import cc.tool.database.String2ControlCode;
import idsrend.charcomponent.CharComponent;
import idsrend.charcomponent.CompositionMethods;
import idsrend.charcomponent.FinalCharComponent;
import idsrend.charcomponent.NonFinalCharComponent;

/**
 * 判斷字型有字無字，決定愛設定佗一个字型來組合。
 * 
 * @author Ihc
 */
public class FontRefSettingTool extends ObjMovableTypeBasicSettingTool
{
	/** 用來查展開式的物件 */
	CommonFontNoSearch 查通用字型編號;
	/** 設定時愛用的字體 */
	protected CommonFont 字體;
	/** 活字的渲染屬性 */
	protected FontRenderContext 字體渲染屬性;
	/** 佇鬥部件做伙揣現有的字型時，代表空空，遏袂處理的位址號碼 */
	private final int 空空無物件 = -7;
	protected final double 活字平均闊度;

	/**
	 * 準備設定工具。
	 * 
	 * @param 查通用字型編號
	 *            用來查展開式的方法
	 * @param 字體
	 *            設定時愛用的字體
	 * @param 字體渲染屬性
	 *            活字的渲染屬性
	 */
	public FontRefSettingTool(CommonFontNoSearch 查通用字型編號, CommonFont 字體, FontRenderContext 字體渲染屬性,
			MkeSeparateMovableType_Bolder 活字加粗)
	{
		super(null, null);
		this.查通用字型編號 = 查通用字型編號;
		this.字體 = 字體;
		this.字體渲染屬性 = 字體渲染屬性;

		int 標準字統一碼 = String2ControlCode.轉換成控制碼(ObjMovableTypeBasicSettingTool.tzuModelCharacter)[0];
		GlyphVector 標準字字型 = 字體.提這个字型(字體渲染屬性, 標準字統一碼);
		BasicStroke basicStroke = new BasicStroke();
		Rectangle2D 標準字區域;
		/** 　若無「意」，就用全字庫宋體的資料 */
		if (標準字字型 == null)
		{
			this.活字平均闊度 = 2.348851561664724;
			標準字區域 = new Rectangle2D.Double(0.03125, -0.78125, 0.9375, 0.921875);
		}
		else
		{
			this.活字平均闊度 = 活字加粗.computeBoldCoefficient(new PlaneGeometry(標準字字型
					.getOutline()));
			標準字區域 = 標準字字型.getOutline().getBounds2D();
		}
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(標準字區域));
		this.tzuModelTerritory = this.pieceForNoBuiltInWen.getBounds2D();
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			NonFinalCharComponent chineseCharacterWen)
	{
		SeprateMovabletype 物件活字 = 查物件活字(new CommonFontNo(chineseCharacterWen.Unicode編號()));
		return new PieceMovableTypeWen(parent, chineseCharacterWen, 物件活字);
	}

	/**
	 * 依照字型號碼，設定新的活字物件。
	 * 
	 * @param 字型號碼
	 *            愛設定的字型號碼資料
	 * @return 依照字型號碼的活字物件
	 */
	private SeprateMovabletype 查物件活字(CommonFontNo 字型號碼)
	{
		SeprateMovabletype 物件活字 = null;
		if (字體.有這个字型無(字型號碼))
		{
			GlyphVector glyphVector = 字體.提這个字型(字體渲染屬性, 字型號碼);
			物件活字 = new SeprateMovabletype(new PlaneGeometry(glyphVector.getOutline()));
			物件活字.設字範圍(tzuModelTerritory);
			物件活字.設目標範圍(物件活字.這馬字範圍());
		}
		else
		{
			物件活字 = findWenForNoBuiltIn(null);
		}
		物件活字.徙轉原點();// TODO 伊佮這个資訊攏提掉，交予排版系統去處理，但是會有一字問題。
		return 物件活字;
	}

	@Override
	public PieceMovableType setTzu(ChineseCharacterMovableTypeTzu parent,
			FinalCharComponent chineseCharacterTzu)
	{
		CharComponent[] 主要結構 = 揣主要結構元件(chineseCharacterTzu);
		int[] 頭前彼个位址 = new int[主要結構.length];// -1代表空空，遏袂處理。位址代表有彼个元件有考慮到。
											// TODO 處理解釋
		for (int i = 0; i < 頭前彼个位址.length; i++)
			頭前彼个位址[i] = 空空無物件;
		PieceMovableType[] 部件組合 = new PieceMovableType[主要結構.length];
		/** 保證開始位址前的攏有揣到 */
		for (int 開始位址 = 0; 開始位址 < 主要結構.length; 開始位址++)
		{
			/** [開始位址,結束位址) */
			for (int 結束位址 = 開始位址 + 1; 結束位址 <= 主要結構.length; 結束位址++)
			{
				if (頭前彼个位址[結束位址 - 1] == 空空無物件)
				{
					// System.out.println("開始位址="+開始位址+" 結束位址="+結束位址);
					/** 產生展開式 */
					StringBuilder 結構展開式 = new StringBuilder();
					for (int i = 開始位址; i < 結束位址; ++i)
					{
						if (i + 1 < 結束位址)
							結構展開式.append(chineseCharacterTzu.部件組字式());
						結構展開式.append(((CharComponent) 主要結構[i]).樹狀結構組字式());
					}
					CommonFontNo 字型號碼 = 查通用字型編號.查通用字型編號(結構展開式.toString());
					/** [開始位址,結束位址) 佇資料庫內底 */
					if (字型號碼 != null)
					{
						// System.out.println(字體.有這个字型無(字型號碼));
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
		SeprateMovabletype rectangularArea = new SeprateMovabletype(new PlaneGeometry());
		rectangularArea.設目標範圍(tzuModelTerritory);// TODO 有必要無？會當公家用無？
		rectangularArea.設字範圍(tzuModelTerritory);// TODO 有必要無？會當公家用無？
		while (目前位址 >= 0)
		{
			PieceMovableTypeTzu 新元件 = new PieceMovableTypeTzu(null,
					chineseCharacterTzu, rectangularArea);// TODO 愛產生新的字部件無？
			新元件.getChildren()[1] = (ChineseCharacterMovableType) 目前元件;
			新元件.getChildren()[0] = (ChineseCharacterMovableType) 部件組合[目前位址];
			目前元件 = 新元件;
			目前位址 = 頭前彼个位址[目前位址];
		}

		return 目前元件;
	}

	/**
	 * 共部件的樹狀結構上頂懸彼層有結合律的部件攏總揣出來。
	 * 
	 * @param CharComponent
	 *            愛拆的樹狀結構
	 * @return 上頂懸彼層有結合律的部件
	 */
	private CharComponent[] 揣主要結構元件(CharComponent CharComponent)
	{
		if (CompositionMethods.isCombinationType(CharComponent
				.Unicode編號()))
		{
			FinalCharComponent 這馬字部件 = (FinalCharComponent) CharComponent;
			if (這馬字部件.CompositionMethods().有結合律無() && 這馬字部件.底下元素().length == 2)
			{
				int 元件數量 = 2;
				// 倒爿部件.getCodePoint() == FinalCharComponent.getCodePoint())
				while (這馬字部件.底下元素()[1].Unicode編號() == 這馬字部件
						.Unicode編號())
				{
					元件數量++;
					這馬字部件 = (FinalCharComponent) 這馬字部件.底下元素()[1];
				}
				這馬字部件 = (FinalCharComponent) CharComponent;
				CharComponent[] 元件 = new CharComponent[元件數量];
				int 陣列位置 = 0;
				while (這馬字部件.底下元素()[1].Unicode編號() == 這馬字部件
						.Unicode編號())
				{
					元件[陣列位置++] = 這馬字部件.底下元素()[0];
					這馬字部件 = (FinalCharComponent) 這馬字部件.底下元素()[1];
				}
				元件[陣列位置++] = 這馬字部件.底下元素()[0];
				元件[陣列位置++] = 這馬字部件.底下元素()[1];
				return 元件;
			}
			else if (這馬字部件.CompositionMethods().有結合律無())
			{
				System.out.println("有三个以上的部件組合符號！！");// TODO log
				return 這馬字部件.底下元素();
			}
			else
			{
				return 這馬字部件.底下元素();
			}
		}
		else
		{
			return new CharComponent[] { CharComponent };
		}
	}

	public double 平均闊度()
	{
		return 活字平均闊度;
	}
}
