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
package cc.layouttools;

import java.util.Vector;

import cc.movabletype.SeprateMovabletype;

/**
 * 整合包圍所需平推的物件，傳入，會先用包圍模組大概調整內部活字，大概平推一次、精細平推一次，最後再用包圍模組再調整。
 * 
 * @author Ihc
 */
public class FlatSurronder
{
	/** 使用此包圍工具的調整工具，並使用其自身合併相關函式 */
	protected MergePieceAdjuster 調整工具;
	/** 主要使用的包圍模組 */
	protected Vector<ZoomAsmmod> 包圍模組列;
	/** 主要使用的包圍工具 */
	protected Vector<BisearchPasteAssembler> 包圍工具列;
	/** 平推過程中要用到的二元調整工具 */
	protected Vector<BisearchPasteAssembler> 平推工具列;
	/** 平推過程中要用到的平推黏合模組 */
	protected Vector<PasteFlatpushSticking> 平推模組列;

	/**
	 * 建立平推包圍調整工具，預設先上下再左右平推。
	 * 
	 * @param 包圍模組
	 *            主要使用的包圍模組
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 */
	public FlatSurronder(MergePieceAdjuster 調整工具, ZoomAsmmod 包圍模組)
	{
		this(調整工具, 包圍模組, true);
	}

	/**
	 * 建立平推包圍調整工具。
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 * @param 包圍模組
	 *            主要使用的包圍模組
	 * @param 先上下後左右
	 *            是否先上下再左右平推，若否則相反
	 */
	public FlatSurronder(MergePieceAdjuster 調整工具, ZoomAsmmod 包圍模組, boolean 先上下後左右)
	{
		this(調整工具, 包圍模組, 包圍模組, 先上下後左右);
	}

	/**
	 * 建立平推包圍調整工具，預設先上下再左右平推。
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 * @param 粗略包圍模組
	 *            平推前使用的包圍模組
	 * @param 貼合包圍模組
	 *            平推後使用的包圍模組
	 */
	public FlatSurronder(MergePieceAdjuster 調整工具, ZoomAsmmod 粗略包圍模組, ZoomAsmmod 貼合包圍模組)
	{
		this(調整工具, 粗略包圍模組, 貼合包圍模組, true);
	}

	/**
	 * 建立平推包圍調整工具。
	 * 
	 * @param 調整工具
	 *            使用此包圍工具的調整工具，並使用其自身合併相關函式
	 * @param 粗略包圍模組
	 *            平推前使用的包圍模組
	 * @param 貼合包圍模組
	 *            平推後使用的包圍模組
	 * @param 先上下後左右
	 *            是否先上下再左右平推，若否則相反
	 */
	public FlatSurronder(MergePieceAdjuster 調整工具, ZoomAsmmod 粗略包圍模組, ZoomAsmmod 貼合包圍模組,
			boolean 先上下後左右)
	{
		this.調整工具 = 調整工具;
		this.包圍模組列 = new Vector<ZoomAsmmod>();
		包圍模組列.add(粗略包圍模組);
		包圍模組列.add(貼合包圍模組);
		包圍工具列 = new Vector<BisearchPasteAssembler>();
		包圍工具列.add(new BisearchPasteSpacingTool(4.0)); // TODO 人工參數
		包圍工具列.add(new BisearchPasteAssembler());

		平推工具列 = new Vector<BisearchPasteAssembler>();
		平推工具列.add(new BisearchPasteSpacingTool(4.0)); // TODO 人工參數
		平推工具列.add(new BisearchPasteSpacingTool(0.5));
		平推模組列 = new Vector<PasteFlatpushSticking>();
		if (先上下後左右)
		{
			平推模組列.add(new PasteUpwardSticking(調整工具));
			平推模組列.add(new PasteDownwardSticking(調整工具));
			平推模組列.add(new PasteLeftwardSticking(調整工具));
			平推模組列.add(new PasteRightwardSticking(調整工具));
		}
		else
		{
			平推模組列.add(new PasteLeftwardSticking(調整工具));
			平推模組列.add(new PasteRightwardSticking(調整工具));
			平推模組列.add(new PasteUpwardSticking(調整工具));
			平推模組列.add(new PasteDownwardSticking(調整工具));
		}
	}

	/**
	 * 將活字物件的調整後，並回傳。
	 * 
	 * @param 活字物件
	 *            要調整的物件
	 * @return 活字物件調整後結果
	 */
	public SeprateMovabletype[] 產生調整後活字(SeprateMovabletype[] 活字物件)
	{
		包圍工具列.elementAt(0).執行(包圍模組列.elementAt(0), 活字物件);
		活字物件 = 包圍模組列.elementAt(0).取得調整後活字物件();
		for (BisearchPasteAssembler 工具 : 平推工具列)
			for (PasteFlatpushSticking 模組 : 平推模組列)
			{
				工具.執行(模組, 活字物件);
				活字物件 = 模組.取得調整後活字物件();
			}
		包圍工具列.elementAt(1).執行(包圍模組列.elementAt(1), 活字物件);
		活字物件 = 包圍模組列.elementAt(1).取得調整後活字物件();
		return 活字物件;
	}
}
