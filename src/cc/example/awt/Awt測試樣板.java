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
package cc.example.awt;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 測試的範例樣版。
 * 
 * @author Ihc
 */
abstract public class Awt測試樣板 extends JPanel
{
	/** 序列化編號 */
	private static final long serialVersionUID = 1L;
	/** 視窗寬度 */
	protected static final int WIDTH = 1300;
	/** 視窗高度 */
	protected static final int HEIGHT = 730;
	/** 字型大小 */
	static final int TYPE_SIZE = 60;
	/** 每行字數 */
	static final int LINE_SIZE = 11;
	/** 測試漢字 */
	static String word = /* "    ⿰禾火秋⿰⿰火牙阝"; */"一百二十三尺意"
			+ "秋漿國變務森"
			+ "⿰禾火⿱將水⿴囗或"
			+ "⿱⿰⿰糹言糹攵⿰矛⿱攵力⿱攵力⿱木⿰木木"
			+ "⿱立⿱日十⿱⿱立日十章輻⿰車⿱一⿱口田⿰⿱十⿱田十⿱一⿱口田"
			+ "⿱口⿰口口⿱夕夕，"
			+ "⿴辶⿴宀⿱珤⿰隹⿰貝招⿴辶咼辶"
			+ "⿴冖儿⿴冖几宀寶建⿴廴聿"
			+ "⿴厂猒⿴广夏⿴疒丙⿴尸古⿴戶方⿴户方⿴户 ⿱户　⿰户　"
			+ "⿴气亥⿴气乃⿴气亞"
			+ "⿴冂⿱一口⿴冂⿱儿口⿴門一⿴門日⿴門月⿴鬥月⿴鬥市⿴鬥一"
			+ "⿱⿴乃又皿盈孕⿴乃子⿱乃子"
			+ "⿴凵乂⿴凵⿰幺⿰丨幺⿴凵⿰豕⿰丨豕"
			+ "⿴勹日⿰⿴弓土畺⿴匚甲⿴⼖⿱口⿰口口⿰懸一⿱闊一⿰懸丨⿱闊丨"
			+ "⿰彳⿱羊羊⿱羊⿰羊羊⿱⿰羊羊⿰羊羊⿰⿱羊羊⿱羊羊⿰羊⿰羊羊⿰⿰羊羊羊⿰丨丨丨"
//			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚ˪⿳⿳⿳ㄅㄧㄚㆷ⿳ㆬㆷ⿳˙ㆬ"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚˋ⿳⿳⿳ㄅㄧㄚ˪⿳⿳⿳ㄅㄧㄚㆷ⿳⿳⿳ㄅㄧㄚˊ⿳⿳⿳ㄅㄧㄚ˫⿳⿳⿳⿳ㄅㄧㄚ㆐ㆷ⿳⿳⿳⿳ㄅㄧㄚㆷ㆐⿳⿳⿳˙ㄅㄧㄚ⿳⿳⿳ㄅㄧㄚ^"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳ㆠㄧ⿳⿳ㆠㄧˋ⿳⿳ㆠㄧ˪⿳⿳ㆠㄧㆷ⿳⿳ㆠㄧˊ⿳⿳ㆠㄧ˫⿳⿳⿳ㆠㄧ㆐ㆷ⿳⿳⿳ㆠㄧㆷ㆐⿳⿳˙ㆠㄧ⿳⿳ㆠㄧ^"
			+ "⿳⿳⿳˙ㄆㄨˊ⿳⿳⿳˙ㄆㄨˊㆬ⿳ㆬ ⿳ㆬˋ⿳ㆬ˪⿳ㆬㆷ⿳ㆬˊ⿳ㆬ˫⿳⿳ㆬ㆐ㆷ⿳⿳ㆬㆷ㆐⿳˙ㆬ⿳ㆬ^";// */;
	/** 全字庫正宋體 */
	static final String 全字庫正宋體 = "全字庫正宋體";
	/** 全字庫正楷體 */
	static final String 全字庫正楷體 = "全字庫正楷體";
	/** 文泉驛正黑 */
	static final String 文泉驛正黑 = "文泉驛正黑";
	/** 文鼎中圓 */
	static final String 文鼎中圓 = "文鼎中圓";
	/** 超研澤中圓 */
	static final String 超研澤中圓 = "超研澤中圓";

	/**
	 * 範例型態視窗的設定佮產生。
	 * 
	 * @param 範例型態
	 *            愛產生的範例型態
	 */
	protected static void run(Container 範例型態)
	{
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.setContentPane(範例型態);
		f.setSize(WIDTH, HEIGHT);
		f.setVisible(true);

	}

	@Override
	public String getName()
	{
		return "漢字測試";
	}

	@Override
	public int getWidth()
	{
		return WIDTH;
	}

	@Override
	public int getHeight()
	{
		return HEIGHT;
	}
}
