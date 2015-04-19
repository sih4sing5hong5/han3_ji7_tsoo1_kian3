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
package cc.連線服務;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.slf4j.Logger;
import org.slf4j.profiler.Profiler;

import 漢字組建.解析工具.組字式序列格式例外;
import 漢字組建.解析工具.組字式序列解析工具;
import 漢字組建.部件.部件;
import 漢字組建.部件結構調整工具.組字式結構正規化工具;
import cc.adjusting.bolder.NullStroke;
import cc.adjusting.piece.MergePieceAdjuster;
import cc.core.展開式查詢工具;
import cc.core.異寫式代換工具;
import cc.core.異寫式查詢工具;
import cc.core.組字式部件組字式建立工具;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.分離活字加粗;
import cc.printing.awt.piece.AwtForSinglePiecePrinter;
import cc.setting.ChineseCharacterTypeSetter;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 共組字規的系統整理起來，紲落來若欲用，就會當自由選擇欲用的工具。
 * 
 * @author Ihc
 */
public class 組字介面
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;
	/** 看使用者提供的部件，是毋是愛先換做展開式抑是按怎 */
	protected 展開式查詢工具 查詢方式;
	/** 決定有需要正規化無佮按怎正規化的物件 */
	protected 組字式結構正規化工具 正規化工具;
	/** 設定前先建立逐的部件，伊佮下跤的部件所代表的組字式 */
	protected 組字式部件組字式建立工具 組字式建立工具;
	/** 異寫式查詢的方法 */
	protected 異寫式代換工具 異寫式代換;
	/** 依據部件佮字體的性質，共部件提來產生活字 */
	protected ChineseCharacterTypeSetter 設定工具;
	/** 佮頭拄仔產生的活字，組合閣共調整 */
	protected MergePieceAdjuster 調整工具;
	protected 分離活字加粗 活字加粗;
	/** 主要是控制字體是毋是粗體 */
	protected final int 字型屬性;
	/** 字體愛偌大 */
	protected final int 字型大細;
	/** 限制組字式長度，予儂無法度惡意攻擊 */
	protected final int 組字式上大長度;

	/**
	 * 建立一个組字介面，並決定所有屬性。
	 * 
	 * @param 查詢方式
	 *            看使用者提供的部件，是毋是愛先換做展開式抑是按怎
	 * @param 正規化工具
	 *            決定有需要正規化無佮按怎正規化的物件
	 * @param 異寫式查詢
	 *            異寫式查詢的方法
	 * @param 編號陣列
	 *            定義異寫編號數字
	 * @param 設定工具
	 *            依據部件佮字體的性質，共部件提來產生活字
	 * @param 調整工具
	 *            佮頭拄仔產生的活字，組合閣共調整
	 * @param 字型屬性
	 *            主要是控制字體是毋是粗體
	 * @param 字型大細
	 *            字體愛偌大
	 */
	public 組字介面(展開式查詢工具 查詢方式, 組字式結構正規化工具 正規化工具, 異寫式查詢工具 異寫式查詢, int[] 編號陣列,
			ChineseCharacterTypeSetter 設定工具, MergePieceAdjuster 調整工具,
			分離活字加粗 活字加粗, int 字型屬性, int 字型大細)
	{
		this(查詢方式, 正規化工具, 異寫式查詢, 編號陣列, 設定工具, 調整工具, 活字加粗, 字型屬性, 字型大細, 50);
	}

	/**
	 * 建立一个組字介面，並決定所有屬性。
	 * 
	 * @param 查詢方式
	 *            看使用者提供的部件，是毋是愛先換做展開式抑是按怎
	 * @param 正規化工具
	 *            決定有需要正規化無佮按怎正規化的物件
	 * @param 異寫式查詢
	 *            異寫式查詢的方法
	 * @param 編號陣列
	 *            定義異寫編號數字
	 * @param 設定工具
	 *            依據部件佮字體的性質，共部件提來產生活字
	 * @param 調整工具
	 *            佮頭拄仔產生的活字，組合閣共調整
	 * @param 字型屬性
	 *            主要是控制字體是毋是粗體
	 * @param 字型大細
	 *            字體愛偌大
	 * @param 組字式上大長度
	 *            限制組字式長度，予儂無法度惡意攻擊
	 */
	public 組字介面(展開式查詢工具 查詢方式, 組字式結構正規化工具 正規化工具, 異寫式查詢工具 異寫式查詢, int[] 編號陣列,
			ChineseCharacterTypeSetter 設定工具, MergePieceAdjuster 調整工具,
			分離活字加粗 活字加粗, int 字型屬性, int 字型大細, int 組字式上大長度)
	{
		this.查詢方式 = 查詢方式;
		this.正規化工具 = 正規化工具;
		this.異寫式代換 = new 異寫式代換工具(編號陣列, 異寫式查詢);
		this.設定工具 = 設定工具;
		this.調整工具 = 調整工具;
		this.活字加粗 = 活字加粗;
		this.字型屬性 = 字型屬性;
		this.字型大細 = 字型大細;
		this.組字式上大長度 = 組字式上大長度;

		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());
		組字式建立工具 = new 組字式部件組字式建立工具();
	}

	/**
	 * 組字而且畫出來。
	 * 
	 * @param 組字式
	 *            使用者要求的組字式
	 * @param 欲畫的所在
	 *            畫字體的所在
	 * @return 實際畫的組字式
	 */
	public String 組字(String 組字式, Graphics 欲畫的所在)
	{
		if (組字式.length() >= 組字式上大長度)
			組字式 = 組字式.substring(0, 組字式上大長度);
		Profiler 看時工具 = new Profiler("組字 " + 組字式);
		看時工具.setLogger(記錄工具);

		看時工具.start("初使化");
		// 記錄工具.debug(MarkerFactory.getMarker("@@"),
		// "初使化～～ 時間：" + System.currentTimeMillis());

		看時工具.start("分析中");
		// 記錄工具.debug("分析中～～ 時間：" + System.currentTimeMillis());

		組字式序列解析工具 序列分析工具 = new 組字式序列解析工具(組字式, 查詢方式);
		部件 部件;
		try
		{
			部件 = 序列分析工具.解析一個組字式();
		}
		catch (組字式序列格式例外 e)
		{
			// TODO 看欲按怎處理，硬顯示，抑是傳連結毋著？
			e.printStackTrace();
			return "";
		}

		部件 組字部件 = (部件) 部件;
		// 組字部件.建立組字式(組字式建立工具);
		// 記錄工具.debug(組字部件.提到組字式());
		部件 = (部件) 正規化工具.正規化(部件);
		組字部件.建立組字式(組字式建立工具);
		// 記錄工具.debug(組字部件.提到組字式());
		部件 = 異寫式代換.代換(部件);

		看時工具.start("設定中");
		// 記錄工具.debug("設定中～～ 時間：" + System.currentTimeMillis());

		漢字組建活字 活字 = 部件.typeset(設定工具, null);

		看時工具.start("調整中");
		// 記錄工具.debug("調整中～～ 時間：" + System.currentTimeMillis());

		活字.adjust(調整工具);

		看時工具.start("四角中");
		分離活字 上尾欲畫的圖 = 調整工具.format((PieceMovableType) 活字);

		看時工具.start("加粗中");
		活字加粗.加粗(上尾欲畫的圖);

		看時工具.start("列印中");
		// 記錄工具.debug("列印中～～ 時間：" + System.currentTimeMillis());
		Graphics2D 字型圖版 = (Graphics2D) 欲畫的所在;
		字型圖版.setColor(Color.black);
		字型圖版.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		字型圖版.translate(0, 字型大細 * 0.83);// TODO 閣愛研究按怎調整
		字型圖版.setStroke(new NullStroke());

		AwtForSinglePiecePrinter 列印工具 = new AwtForSinglePiecePrinter(字型圖版);

		列印工具.printPiece(上尾欲畫的圖);

		看時工具.stop().log();
		return 組字部件.提到組字式();
	}
}
