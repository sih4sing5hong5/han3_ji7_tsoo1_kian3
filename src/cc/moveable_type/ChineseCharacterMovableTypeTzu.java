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
package cc.moveable_type;

import cc.adjusting.ChineseCharacterTypeAdjuster;
import cc.core.字部件;
import cc.printing.ChineseCharacterTypePrinter;

/**
 * 漢字活字樹狀結構的上層節點。「獨體為文，合體為字」，樹狀結構中的葉子為文，其他上層節點為字。
 * <code>ChineseCharacterMovableTypeTzu</code>記錄底下活字的排版資訊。
 * 
 * @author Ihc
 */
public class ChineseCharacterMovableTypeTzu extends ChineseCharacterMovableType
{

	/**
	 * 底下的各個活字
	 */
	protected 漢字組建活字[] children;

	/**
	 * 以<code>ChineseCharacter</code>部件結構建立字活字結構
	 * 
	 * @param parent
	 *            上一層的活字結構。若上層為樹狀的樹根，傳入null
	 * @param chineseCharacterTzu
	 *            字部件結構
	 */
	public ChineseCharacterMovableTypeTzu(
			ChineseCharacterMovableTypeTzu parent,
			字部件 chineseCharacterTzu)
	{
		super(parent, chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.getType().getNumberOfChildren();
		this.children = new ChineseCharacterMovableType[childrenSize];
	}

	@Override
	public void adjust(ChineseCharacterTypeAdjuster adjuster)
	{
		adjuster.adjustTzu(this);
		return;
	}

	@Override
	public void print(ChineseCharacterTypePrinter printer)
	{
		printer.printTzu(this);
		return;
	}

	/**
	 * 取得底下的各個部件
	 * 
	 * @return 底下的各個部件
	 */
	public 漢字組建活字[] getChildren()
	{
		return children;
	}
}
