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
package cc.揀字工具;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import 漢字組建.部件.字部件;
import 漢字組建.部件.文部件;
import cc.活字.ChineseCharacterMovableTypeTzu;
import cc.活字.PieceMovableType;
import cc.活字.PieceMovableTypeTzu;
import cc.活字.PieceMovableTypeWen;
import cc.活字.分離活字;
import cc.活字.平面幾何;

/**
 * 物件活字設定工具。將部件結構（<code>ChineseCharacter</code>）轉換成活字結構（
 * <code>PieceMovableType</code>）。把活字的資訊全部集中在同一個物件上（<code>Piece</code>，
 * <code>RectangularArea</code>型態 ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>SimplePiece</code>是在設定時兩兩配對後定框，調整時更改部件大小，但無法物件難實作距離貼近或拉開。
 * 
 * @author Ihc
 */
public class SimplePieceSetter extends 物件活字基礎設定工具
{
	/** 活字字型的名稱 */
	private String fontName;
	/** 活字字型的選項 */
	private int fontStyle;
	/** 活字的點距 */
	private int fontResolution;
	/** 活字的渲染屬性 */
	protected FontRenderContext fontRenderContext;
	/** 活字的字體 */
	protected Font font;

	/**
	 * 建立物件活字設定工具
	 * 
	 * @param fontName
	 *            活字字型的名稱
	 * @param fontStyle
	 *            活字字型的選項
	 * @param fontResolution
	 *            活字的點距
	 * @param fontRenderContext
	 *            活字的渲染屬性
	 */
	public SimplePieceSetter(String fontName, int fontStyle,
			int fontResolution, FontRenderContext fontRenderContext)
	{
		super(null, null);
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontResolution = fontResolution;
		this.fontRenderContext = fontRenderContext;
		this.font = new Font(fontName, fontStyle, fontResolution);
		GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
				SimplePieceSetter.tzuModelCharacter);
		this.tzuModelTerritory = glyphVector.getOutline().getBounds2D();
		BasicStroke basicStroke = new BasicStroke();
		this.pieceForNoBuiltInWen = new Area(
				basicStroke.createStrokedShape(tzuModelTerritory));
	}

	@Override
	public PieceMovableTypeWen setWen(ChineseCharacterMovableTypeTzu parent,
			文部件 chineseCharacterWen)
	{
		分離活字 rectangularArea = null;
		if (font.canDisplay(chineseCharacterWen.Unicode編號()))
		{
			GlyphVector glyphVector = font.createGlyphVector(fontRenderContext,
					chineseCharacterWen.部件組字式());
			rectangularArea = new 分離活字(new 平面幾何(glyphVector.getOutline()));
			rectangularArea.設字範圍(tzuModelTerritory);
			rectangularArea.設目標範圍(rectangularArea.這馬字範圍());
		}
		else
		{
			rectangularArea = findWenForNoBuiltIn(chineseCharacterWen);
		}
		rectangularArea.徙轉原點();

		PieceMovableTypeWen pieceMovableTypeWen = new PieceMovableTypeWen(
				parent, chineseCharacterWen, rectangularArea);
		return pieceMovableTypeWen;
	}

	@Override
	public PieceMovableTypeTzu setTzu(ChineseCharacterMovableTypeTzu parent,
			字部件 chineseCharacterTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = new PieceMovableTypeTzu(
				parent, chineseCharacterTzu, new 分離活字(new 平面幾何()));

		setChildrenRecursively(pieceMovableTypeTzu, chineseCharacterTzu);

		switch (chineseCharacterTzu.組合方式())
		{
		case 左右合併:
			horizontalSetting(pieceMovableTypeTzu);
			break;
		case 上下合併:
			verticalSetting(pieceMovableTypeTzu);
			break;
		case 四面包圍:
			wrapSetting(pieceMovableTypeTzu);
			break;
		case 注音符號:// TODO 看情形才決定欲修改無，先用垂直的
			horizontalSetting(pieceMovableTypeTzu);
			break;
		}

		if (pieceMovableTypeTzu.getParent() == null)
			pieceMovableTypeTzu.getPiece().設目標範圍(tzuModelTerritory);

		return pieceMovableTypeTzu;
	}

	/**
	 * 設定底下活字部件
	 * 
	 * @param chineseCharacterMovableTypeTzu
	 *            目前設定的合體活字
	 * @param chineseCharacterTzu
	 *            目前設定的字部件
	 */
	protected void setChildrenRecursively(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu,
			字部件 chineseCharacterTzu)
	{
		for (int i = 0; i < chineseCharacterMovableTypeTzu.getChildren().length; ++i)
		{
			chineseCharacterMovableTypeTzu.getChildren()[i] = chineseCharacterTzu
					.底下元素()[i].typeset(this,
					chineseCharacterMovableTypeTzu);
		}
		return;
	}

	/**
	 * 水平組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
	protected void horizontalSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = firstChild.getPiece().目標範圍().getWidth()
				+ secondChild.getPiece().目標範圍().getWidth();
		rectDouble.height = Math.max(firstChild.getPiece().目標範圍().getHeight(),
				secondChild.getPiece().目標範圍().getHeight());
		pieceMovableTypeTzu.getPiece().設目標範圍大細(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().合併活字(
				new 分離活字(new 平面幾何(new Area(rectDouble))));
		firstChild
				.getPiece()
				.目標範圍()
				.setRect(0.0, 0.0, firstChild.getPiece().目標範圍().getWidth(),
						rectDouble.height);
		secondChild
				.getPiece()
				.目標範圍()
				.setRect(firstChild.getPiece().目標範圍().getWidth(), 0.0,
						secondChild.getPiece().目標範圍().getWidth(),
						rectDouble.height);
		return;

	}

	/**
	 * 垂直組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
	protected void verticalSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = Math.max(firstChild.getPiece().目標範圍().getWidth(),
				secondChild.getPiece().目標範圍().getWidth());
		rectDouble.height = firstChild.getPiece().目標範圍().getHeight()
				+ secondChild.getPiece().目標範圍().getHeight();
		pieceMovableTypeTzu.getPiece().設目標範圍大細(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().合併活字(
				new 分離活字(new 平面幾何(new Area(rectDouble))));
		firstChild
				.getPiece()
				.目標範圍()
				.setRect(0.0, 0.0, rectDouble.width,
						firstChild.getPiece().目標範圍().getHeight());
		secondChild
				.getPiece()
				.目標範圍()
				.setRect(0.0, firstChild.getPiece().目標範圍().getHeight(),
						rectDouble.width,
						secondChild.getPiece().目標範圍().getHeight());
		return;
	}

	/**
	 * 包圍組合活字
	 * 
	 * @param pieceMovableTypeTzu
	 *            要設定的合體活字
	 */
	protected void wrapSetting(PieceMovableTypeTzu pieceMovableTypeTzu)
	{
		// TODO 暫時替代用
		PieceMovableType firstChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[0], secondChild = (PieceMovableType) pieceMovableTypeTzu
				.getChildren()[1];
		Rectangle2D.Double rectDouble = new Rectangle2D.Double();
		rectDouble.width = firstChild.getPiece().目標範圍().getWidth() * 2;
		rectDouble.height = firstChild.getPiece().目標範圍().getHeight() * 2;
		pieceMovableTypeTzu.getPiece().設目標範圍大細(rectDouble.width,
				rectDouble.height);
		pieceMovableTypeTzu.getPiece().合併活字(
				new 分離活字(new 平面幾何(new Area(rectDouble))));
		firstChild.getPiece().目標範圍()
				.setRect(0.0, 0.0, rectDouble.width, rectDouble.height);
		secondChild
				.getPiece()
				.目標範圍()
				.setRect(
						(firstChild.getPiece().目標範圍().getWidth() - secondChild
								.getPiece().目標範圍().getWidth()) / 2,
						(firstChild.getPiece().目標範圍().getHeight() - secondChild
								.getPiece().目標範圍().getHeight()) / 2,
						secondChild.getPiece().目標範圍().getWidth(),
						secondChild.getPiece().目標範圍().getHeight());
		return;
	}

	/**
	 * 取得活字字型的名稱
	 * 
	 * @return 活字字型的名稱
	 */
	public String getFontName()
	{
		return fontName;
	}

	/**
	 * 取得活字字型的選項
	 * 
	 * @return 活字字型的選項
	 */
	public int getFontStyle()
	{
		return fontStyle;
	}

	/**
	 * 取得活字的點距
	 * 
	 * @return 活字的點距
	 */
	public int getFontResolution()
	{
		return fontResolution;
	}

	/**
	 * 取得活字的渲染屬性
	 * 
	 * @return 活字的渲染屬性
	 */
	public FontRenderContext getFontRenderContext()
	{
		return fontRenderContext;
	}
}
