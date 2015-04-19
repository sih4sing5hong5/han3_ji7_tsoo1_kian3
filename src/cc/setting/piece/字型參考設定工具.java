/*******************************************************************************
 * 著作權所有 (C) 民國102年 意傳文化科技
 * 開發者：薛丞宏
 * 網址：http://意傳.台灣
 * 字型提供：
 * 	全字庫授權說明
 * 		© 2012 中華民國行政院研究發展考核委員會。本字型檔採用創用CC「姓名標示－禁止改作」3.0臺灣版授權條款釋出。您可以在不變更字型內容之條件下，重製、散布及傳輸本字型檔之著作內容。惟應保留本字型名稱及著作權聲明。
 * 		http://www.cns11643.gov.tw/AIDB/copyright.do
 * 		
 * 	「中央研究院漢字部件檢字系統」2.65版釋出聲明
 * 		……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
 * 		http://cdp.sinica.edu.tw/cdphanzi/declare.htm
 * 		
 * 	吳守禮台語注音字型：
 * 		©2012從宜工作室：吳守禮、吳昭新，以CC01.0通用(CC01.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
 * 		http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
 * 		
 * 本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主：
 * 	１．得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
 * 	２．任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼；
 * 	３．將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼
 * 
 * 此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。
 * 
 * 漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。
 * 
 * 謝謝您的使用與推廣～～
 ******************************************************************************/
package cc.setting.piece;

import java.awt.BasicStroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import cc.core.部件;
import cc.core.字部件;
import cc.core.組合方式;
import cc.core.文部件;
import cc.core.部件;
import cc.moveable_type.ChineseCharacterMovableType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.piece.PieceMovableTypeWen;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.分離活字加粗;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.tool.database.字串與控制碼轉換;

/**
 * 判斷字型有字無字，決定愛設定佗一个字型來組合。
 * 
 * @author Ihc
 */
public class 字型參考設定工具 extends 物件活字基礎設定工具
{
	/** 用來查展開式的物件 */
	展開式查通用字型編號 查通用字型編號;
	/** 設定時愛用的字體 */
	protected 通用字體 字體;
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
	public 字型參考設定工具(展開式查通用字型編號 查通用字型編號, 通用字體 字體, FontRenderContext 字體渲染屬性,
			分離活字加粗 活字加粗)
	{
		super(null, null);
		this.查通用字型編號 = 查通用字型編號;
		this.字體 = 字體;
		this.字體渲染屬性 = 字體渲染屬性;

		int 標準字統一碼 = 字串與控制碼轉換.轉換成控制碼(物件活字基礎設定工具.tzuModelCharacter)[0];
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
			this.活字平均闊度 = 活字加粗.computeBoldCoefficient(new 平面幾何(標準字字型
					.getOutline()));
			標準字區域 = 標準字字型.getOutline().getBounds2D();
		}
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(標準字區域));
		this.tzuModelTerritory = this.pieceForNoBuiltInWen.getBounds2D();
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			文部件 chineseCharacterWen)
	{
		分離活字 物件活字 = 查物件活字(new 通用字型號碼(chineseCharacterWen.getCodePoint()));
		return new PieceMovableTypeWen(parent, chineseCharacterWen, 物件活字);
	}

	/**
	 * 依照字型號碼，設定新的活字物件。
	 * 
	 * @param 字型號碼
	 *            愛設定的字型號碼資料
	 * @return 依照字型號碼的活字物件
	 */
	private 分離活字 查物件活字(通用字型號碼 字型號碼)
	{
		分離活字 物件活字 = null;
		if (字體.有這个字型無(字型號碼))
		{
			GlyphVector glyphVector = 字體.提這个字型(字體渲染屬性, 字型號碼);
			物件活字 = new 分離活字(new 平面幾何(glyphVector.getOutline()));
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
			字部件 chineseCharacterTzu)
	{
		部件[] 主要結構 = 揣主要結構元件(chineseCharacterTzu);
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
							結構展開式.append(chineseCharacterTzu.getChars());
						結構展開式.append(((部件) 主要結構[i]).提到組字式());
					}
					通用字型號碼 字型號碼 = 查通用字型編號.查通用字型編號(結構展開式.toString());
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
		分離活字 rectangularArea = new 分離活字(new 平面幾何());
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
	 * @param 部件
	 *            愛拆的樹狀結構
	 * @return 上頂懸彼層有結合律的部件
	 */
	private 部件[] 揣主要結構元件(部件 部件)
	{
		if (組合方式.isCombinationType(部件
				.getCodePoint()))
		{
			字部件 這馬字部件 = (字部件) 部件;
			if (這馬字部件.getType().有結合律無() && 這馬字部件.getChildren().length == 2)
			{
				int 元件數量 = 2;
				// 倒爿部件.getCodePoint() == 字部件.getCodePoint())
				while (這馬字部件.getChildren()[1].getCodePoint() == 這馬字部件
						.getCodePoint())
				{
					元件數量++;
					這馬字部件 = (字部件) 這馬字部件.getChildren()[1];
				}
				這馬字部件 = (字部件) 部件;
				部件[] 元件 = new 部件[元件數量];
				int 陣列位置 = 0;
				while (這馬字部件.getChildren()[1].getCodePoint() == 這馬字部件
						.getCodePoint())
				{
					元件[陣列位置++] = 這馬字部件.getChildren()[0];
					這馬字部件 = (字部件) 這馬字部件.getChildren()[1];
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
			return new 部件[] { 部件 };
		}
	}

	public double 平均闊度()
	{
		return 活字平均闊度;
	}
}
