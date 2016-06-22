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

import cc.movabletype.SeprateMovabletype;
import cc.stroketool.Point2DWithVector;

/**
 * 適用於外部部件有右上二邊並且右下有往內勾的活字接合，如「⿴勹日」為「旬」。在接合時，都固定外部活字，並將內部活字固定在左下縮放。
 * <p>
 * 注意：使用前要設定外部活字資字，否則會產生出無法預料的狀況！！
 * 
 * @author Ihc
 */

public class RightTopHookAsmmod extends ZoomAsmmod
{
	/** 內部活字縮放的參考基準點 */
	private Point2DWithVector 縮放基準點;
	/** 縮放控制點在活字最低點往上幾倍的活字粗細半徑 */
	private double 縮放點離最低點的半徑倍率;

	/**
	 * 建立右上下勾接合模組。
	 * 
	 * @param 調整工具
	 *            使用此模組的調整工具，並使用其自身合併相關函式
	 * @param 縮放點離最低點的半徑倍率
	 *            縮放控制點在活字最低點往上幾倍的活字粗細半徑
	 */
	public RightTopHookAsmmod(MergePieceAdjuster 調整工具, double 縮放點離最低點的半徑倍率)
	{
		super(調整工具);
		this.縮放點離最低點的半徑倍率 = 縮放點離最低點的半徑倍率;
	}

	/**
	 * 設定外部活字資訊
	 * 
	 * @param 外部活字
	 *            接下來變形時所要用的外部活字
	 */
	public void 設定外部活字資訊(SeprateMovabletype 外部活字)
	{
		縮放基準點 = 外部活字.揣上低的點();
		double 外部活字半徑 = 活字寬度();
		縮放基準點.subLocation(0, 外部活字半徑 * 縮放點離最低點的半徑倍率);
		return;
	}

	@Override
	public void 變形處理(double middleValue)
	{
		super.變形處理(middleValue);
		temporaryPiece.徙(縮放基準點.getX()
				- temporaryPiece.這馬字範圍().getMinX(), 縮放基準點.getY()
				- temporaryPiece.這馬字範圍().getMaxY());
		return;
	}
}
