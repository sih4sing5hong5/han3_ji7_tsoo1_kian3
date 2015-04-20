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
package cc.adjusting.piece;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.slf4j.Logger;

import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.moveable_type.rectangular_area.分離活字;
import cc.程式記錄.漢字組建記錄工具包;

/**
 * 區塊活字調整工具。調整<code>PieceMovableType</code>，因為把活字的資訊全部集中在同一個物件上（
 * <code>Piece</code>， <code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>SimplePiece</code>是在設定時兩兩配對後定框，調整時更改部件大小，但無法物件難實作距離貼近或拉開。
 * 
 * @author Ihc
 */
abstract public class SimplePieceAdjuster implements
		ChineseCharacterTypeAdjuster
{
	/** 記錄程式狀況 */
	protected Logger 記錄工具;

	/**
	 * 建立物件活字調整工具
	 * 
	 * @param chineseCharacterTypeBolder
	 *            物件活字加寬工具
	 * @param precision
	 *            合併、調整的精細度
	 */
	public SimplePieceAdjuster()
	{
		記錄工具 = new 漢字組建記錄工具包().記錄工具(getClass());
	}

	// @Override
	// public void adjustWen(
	// ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	// {
	// PieceMovableTypeWen pieceMovableTypeWen = (PieceMovableTypeWen)
	// chineseCharacterMovableTypeWen;
	// 分離活字 rectangularArea = pieceMovableTypeWen.getPiece();
	// AffineTransform affineTransform = getAffineTransform(rectangularArea);
	// shrinkPieceByFixingStroke(rectangularArea, affineTransform);
	// return;
	// }
	//
	// @Override
	// public void adjustTzu(ChineseCharacterMovableTypeTzu tzu)
	// {
	// PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) tzu;
	// 分離活字 rectangularArea = pieceMovableTypeTzu.getPiece();
	// AffineTransform affineTransform = getAffineTransform(rectangularArea);
	// rectangularArea.縮放(affineTransform);
	// for (int i = 0; i < pieceMovableTypeTzu.getChildren().length; ++i)
	// {
	// PieceMovableType child = (PieceMovableType) pieceMovableTypeTzu
	// .getChildren()[i];
	// Rectangle2D childTerritory = child.getPiece().目標範圍();
	// childTerritory.setRect(
	// childTerritory.getX() * affineTransform.getScaleX(),
	// childTerritory.getY() * affineTransform.getScaleY(),
	// childTerritory.getWidth() * affineTransform.getScaleX(),
	// childTerritory.getHeight() * affineTransform.getScaleY());
	// pieceMovableTypeTzu.getChildren()[i].adjust(this);
	// }
	// }

	/**
	 * 比較<code>RectangularArea</code>目前大小（<code>getBounds2D()</code>）和預期大小（
	 * <code>getTerritory()</code>），算出縮放矩陣
	 * 
	 * @param rectangularArea
	 *            計算縮放的目標
	 * @return 相對應的縮放矩陣
	 */
	protected AffineTransform getAffineTransform(分離活字 rectangularArea)
	{
		Rectangle2D territory = rectangularArea.目標範圍();
		Rectangle2D bounds = rectangularArea.這馬字範圍();
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(territory.getWidth() / bounds.getWidth(),
				territory.getHeight() / bounds.getHeight());
		return affineTransform;
	}
}
