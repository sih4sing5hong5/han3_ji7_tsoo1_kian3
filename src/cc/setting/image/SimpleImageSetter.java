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
package cc.setting.image;

import java.awt.Point;

import 漢字組建.部件.字部件;
import 漢字組建.部件.文部件;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.image.ImageMoveableType;
import cc.moveable_type.image.ImageMoveableTypeTzu;
import cc.moveable_type.image.ImageMoveableTypeWen;
import cc.setting.ChineseCharacterTypeSetter;

/**
 * 圖片活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>ImageMoveableType</code>）。<code>ImageMoveableType</code>
 * 在列印時才讀取字體，處理時不考慮部件的差異，皆視為同樣大小，每個部件當做矩形，記錄預計長寬（<code>region</code>）及位置（
 * <code>position</code>），目前長寬（<code>scaler</code> ）是給合體活字儲存紀錄的。
 * 
 * @author Ihc
 */
public class SimpleImageSetter implements ChineseCharacterTypeSetter
{
	@Override
	public ImageMoveableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			文部件 chineseCharacterWen)
	{
		ImageMoveableTypeWen moveableType = new ImageMoveableTypeWen(parent,
				chineseCharacterWen);
		moveableType.setRegion(new Point(100, 100));
		moveableType.setPosition(new Point(0, 0));
		moveableType.setScaler(new Point(100, 100));
		return moveableType;
	}

	@Override
	public ImageMoveableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			字部件 chineseCharacterTzu)
	{
		ImageMoveableTypeTzu imageMoveableTypeTzu = new ImageMoveableTypeTzu(
				parent, chineseCharacterTzu);
		int childrenSize = chineseCharacterTzu.組合方式().getNumberOfChildren();
		for (int i = 0; i < childrenSize; ++i)
		{
			imageMoveableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.底下元素()[i].typeset(this, parent);
		}
		ImageMoveableType firstChild = (ImageMoveableType) imageMoveableTypeTzu
				.getChildren()[0];
		ImageMoveableType secondChild = (ImageMoveableType) imageMoveableTypeTzu
				.getChildren()[1];
		Point firstRegion = firstChild.getRegion();
		Point secondRegion = secondChild.getRegion();
		imageMoveableTypeTzu.setRegion(new Point());
		switch (chineseCharacterTzu.組合方式())
		{
		default:
			System.out.println("無支援，先用橫的組");
		case 左右合併:
			imageMoveableTypeTzu.getRegion().x = firstRegion.x + secondRegion.x;
			imageMoveableTypeTzu.getRegion().y = Math.max(firstRegion.y,
					secondRegion.y);
			firstRegion.y = secondRegion.y = imageMoveableTypeTzu.getRegion().y;
			secondChild.setPosition(new Point(firstRegion.x, 0));
			break;
		case 上下合併:
			imageMoveableTypeTzu.getRegion().x = Math.max(firstRegion.x,
					secondRegion.x);
			imageMoveableTypeTzu.getRegion().y = firstRegion.y + secondRegion.y;
			firstRegion.x = secondRegion.x = imageMoveableTypeTzu.getRegion().x;
			secondChild.setPosition(new Point(0, firstRegion.y));
			break;
		case 四面包圍:
			imageMoveableTypeTzu.getRegion().x = firstRegion.x << 1;
			imageMoveableTypeTzu.getRegion().y = firstRegion.y << 1;
			firstChild.setRegion(imageMoveableTypeTzu.getRegion());
			secondChild.setPosition(new Point(firstRegion.x >> 1,
					firstRegion.y >> 1));
			break;
		}
		imageMoveableTypeTzu.setPosition(new Point(0, 0));
		imageMoveableTypeTzu.setScaler(imageMoveableTypeTzu.getRegion());
		return imageMoveableTypeTzu;
	}
}
