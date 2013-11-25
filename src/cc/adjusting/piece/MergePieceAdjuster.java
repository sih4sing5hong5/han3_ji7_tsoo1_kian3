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

import cc.adjusting.bolder.ChineseCharacterTypeBolder;
import cc.core.ChineseCharacter;
import cc.core.ChineseCharacterTzu;
import cc.core.ChineseCharacterTzuCombinationType;
import cc.moveable_type.ChineseCharacterMovableTypeTzu;
import cc.moveable_type.ChineseCharacterMovableTypeWen;
import cc.moveable_type.漢字組建活字;
import cc.moveable_type.piece.PieceMovableType;
import cc.moveable_type.piece.PieceMovableTypeTzu;
import cc.moveable_type.rectangular_area.ShapeInformation;
import cc.moveable_type.rectangular_area.分離活字;
import cc.moveable_type.rectangular_area.平面幾何;
import cc.moveable_type.rectangular_area.分離活字;
import cc.tool.database.字串與控制碼轉換;

/**
 * 物件活字調整工具。把活字的資訊全部集中在同一個物件上（<code>Piece</code>， <code>RectangularArea</code>型態
 * ），方便函式傳遞與使用，而且物件上也有相對應操縱的函式。
 * <p>
 * <code>MergePiece</code>設定時只記錄字體資訊，調整時看兩兩部件大小，縮小成同高或同寬後再組合，彈性大。但現階段受加粗邊角問題影響。
 * 
 * @author Ihc
 */
public class MergePieceAdjuster extends SimplePieceAdjuster
{
	/** 將水平或垂直兩部件拼合的工具 */
	protected 二元搜尋貼合工具 貼合工具;
	/** 左右兩部件拼合時的調整工具 */
	protected 水平拼合工具 水平工具;
	/** 上下兩部件拼合時的調整工具 */
	protected 垂直拼合工具 垂直工具;
	/** 處理包圍關係的活字時所使用的工具 */
	protected 包圍整合分派工具 分派工具;
	/** 決定啥物注音會當用，愛下佇佗 */
	protected 注音符號分類工具 分類工具;
	/** 注音內底逐个符號間隔寬度 */
	final double 注音內底間隔寬度 = 0.3;// TODO
	protected final double 活字平均闊度;
	protected final double precision;

	/**
	 * 建立物件活字調整工具
	 * 
	 * @param chineseCharacterTypeBolder
	 *            物件活字加寬工具
	 * @param precisions
	 *            合併、調整的精細度
	 */
	public MergePieceAdjuster(double 精確度, double 活字平均闊度)
	{
		貼合工具 = new 二元搜尋貼合工具();
		水平工具 = new 水平拼合工具(this);
		垂直工具 = new 垂直拼合工具(this);

		分派工具 = new 包圍整合分派工具();
		分派工具.add(new 上蓋包圍工具(this));
		分派工具.add(new 左下包圍工具(this));
		分派工具.add(new 左上包圍工具(this));
		分派工具.add(new 右上包圍工具(this));
		分派工具.add(new 左右上內勾包圍工具(this));
		分派工具.add(new 左右下三邊包圍工具(this));
		分派工具.add(new 上下左三邊包圍工具(this));
		分派工具.add(new 右上內勾包圍工具(this));
		分派工具.add(new 四面包圍工具(this));
		分派工具.設定無支援暫時用包圍工具(水平工具);

		int[] 輕聲 = 字串與控制碼轉換.轉換成控制碼("˙");
		int[] 聲韻 = 字串與控制碼轉換.轉換成控制碼("ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙ" + "ㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦ"
				+ "ㄧㄨㄩ" + "ㄪㄫㄬ" + "ㄭㄮ" + "ㆠㆡㆢㆣ" + "ㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳ");
		int[] 調號 = 字串與控制碼轉換.轉換成控制碼(" ˋ˪ˊ˫ˇ㆐^ㆴㆵㆶㆷ");
		分類工具 = new 注音符號分類工具(輕聲, 聲韻, 調號);

		this.活字平均闊度 = 活字平均闊度;
		this.precision = 精確度;
	}

	@Override
	public void adjustWen(
			ChineseCharacterMovableTypeWen chineseCharacterMovableTypeWen)
	{
		return;
	}

	@Override
	public void adjustTzu(
			ChineseCharacterMovableTypeTzu chineseCharacterMovableTypeTzu)
	{
		PieceMovableTypeTzu pieceMovableTypeTzu = (PieceMovableTypeTzu) chineseCharacterMovableTypeTzu;
		switch (((ChineseCharacterTzu) pieceMovableTypeTzu
				.getChineseCharacter()).getType())
		{
		case horizontal:
			遞迴調整(pieceMovableTypeTzu);
			horizontalMerging(pieceMovableTypeTzu);
			break;
		case 異寫字編號符號:
			System.out.println("有問題！！！無事先共異寫字換掉!!!");
		case vertical:
			遞迴調整(pieceMovableTypeTzu);
			verticalMerging(pieceMovableTypeTzu);
			break;
		case wrap:
			遞迴調整(pieceMovableTypeTzu);
			wrapMerging(pieceMovableTypeTzu);
			break;
		case 注音符號:
			組合注音(pieceMovableTypeTzu);
			break;
		}
		return;
	}

	/**
	 * 遞迴調整活字下跤的活字。
	 * 
	 * @param 物件活字
	 *            愛調整的活字樹根
	 */
	private void 遞迴調整(PieceMovableTypeTzu 物件活字)
	{
		for (漢字組建活字 活字 : 物件活字.getChildren())
		{
			活字.adjust(this);
		}
		return;
	}

	/**
	 * 水平組合活字。
	 * 
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void horizontalMerging(PieceMovableTypeTzu 物件活字)
	{
		水平工具.組合(物件活字);
		return;
	}

	/**
	 * 垂直組合活字。
	 * 
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void verticalMerging(PieceMovableTypeTzu 物件活字)
	{
		垂直工具.組合(物件活字);
		return;
	}

	/**
	 * 包圍組合活字。
	 * 
	 * @param 物件活字
	 *            要調整的合體活字
	 */
	void wrapMerging(PieceMovableTypeTzu 物件活字)
	{
		if (!分派工具.組合(物件活字))
		{
			System.out.println("QQ 沒組合工具");
			ChineseCharacterTzu chineseCharacterTzu = (ChineseCharacterTzu) 物件活字
					.getChineseCharacter();
			ChineseCharacter chineseCharacter = chineseCharacterTzu
					.getChildren()[0];
			switch (chineseCharacter.getCodePoint())
			{
			case 2:
			}
			// 預設先用水平
			水平工具.組合(物件活字);
		}
		return;
	}

	/**
	 * 組合注音符號。先共注音分類，閣分兩排，兩排分別排好才閣組起來。
	 * 
	 * @param 活字物件
	 *            愛組合的注音活字樹根
	 */
	void 組合注音(PieceMovableTypeTzu 活字物件)
	{
		注音符號分開工具 分開工具 = new 注音符號分開工具(分類工具);
		注音符號分類 分類 = new 注音符號分類();
		分開工具.分開(活字物件, 分類);
		注音排放模組 主要排齊模組 = new 注音排密模組();
		// 注音排齊模組(注音內底間隔寬度) 注音排密模組()
		// double 聲韻面頂 = 主要排齊模組.對齊範圍().getMaxY();
		for (分離活字 活字 : 分類.輕聲)
			主要排齊模組.加新的活字(活字);
		double 聲韻面頂 = 主要排齊模組.上尾實際範圍().getMinY();
		boolean 第一个 = true;
		for (分離活字 活字 : 分類.聲韻)
		{
			主要排齊模組.加新的活字(活字);
			if (第一个)
			{
				聲韻面頂 = 主要排齊模組.上尾實際範圍().getMinY();
				第一个 = false;
			}
		}
		double 聲韻下跤 = 主要排齊模組.上尾實際範圍().getMaxY();
		注音排放模組 邊仔排齊模組 = new 注音排密模組();
		for (分離活字 活字 : 分類.調號)
		{
			活字.這馬字範圍照圖形範圍();
			邊仔排齊模組.加新的活字(活字);
		}
		分離活字 主要活字 = 活字物件.getPiece();
		主要活字.圖形重設();
		主要活字.合併活字(主要排齊模組.目前結果());
		分離活字 邊仔活字 = 邊仔排齊模組.目前結果();
		邊仔活字.徙(主要活字.這馬字範圍().getMaxX() - 邊仔活字.這馬字範圍().getMinX()
				+ 主要活字.這馬字範圍().getWidth() * 注音內底間隔寬度, 主要排齊模組.對齊範圍().getMinY()
				- 邊仔排齊模組.對齊範圍().getCenterY());
		// 上尾範圍() 對齊範圍()
		主要活字.合併活字(邊仔活字);
		主要活字.徙(-主要活字.這馬字範圍().getMinX(), -(聲韻面頂 + 聲韻下跤) / 2.0);
		return;
	}

	/**
	 * 給縮放值，取得相對應的縮放矩陣
	 * 
	 * @param scaler
	 *            縮放值
	 * @return 縮放矩陣
	 */
	protected AffineTransform getAffineTransform(double scaler)
	{
		return getAffineTransform(scaler, scaler);
	}

	/**
	 * 給水平、垂直縮放值，取得相對應的縮放矩陣
	 * 
	 * @param scalerX
	 *            水平縮放值
	 * @param scalerY
	 *            垂直縮放值
	 * @return 縮放矩陣
	 */
	protected AffineTransform getAffineTransform(double scalerX, double scalerY)
	{
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToScale(scalerX, scalerY);
		return affineTransform;
	}

	/**
	 * 判斷二個物件活字是否重疊。用在調整部件間的距離，<code>Area</code>內建的減去實作。
	 * 
	 * @param first
	 *            第一個活字物件
	 * @param second
	 *            第二個活字物件
	 * @return 是否重疊
	 */
	protected boolean areIntersected(分離活字 first, 分離活字 second)
	{
		平面幾何 rectangularArea = first.目前的字體();
		平面幾何 rectangularArea2 = new 平面幾何(rectangularArea);
		rectangularArea.減去活字(second.目前的字體());
		// return true;
		// return false;
		return !rectangularArea.equals(rectangularArea2);
	}

	/**
	 * 判斷二個物件活字適合接近的係數，筆劃角度太相同會黏在一起。用來調整部件間的距離，用邊長減少長度除以邊界長度來計算。
	 * 
	 * @param first
	 *            第一個活字物件
	 * @param second
	 *            第二個活字物件
	 * @param boundaryLength
	 *            用來比較消失邊長的邊界長度
	 * @return 適合接近的係數
	 */
	protected double nonsuitableToClose(分離活字 first, 分離活字 second,
			double boundaryLength)
	{
		// TODO 愛加速
		ShapeInformation firstInformation = new ShapeInformation(first.目前的字體()), secondInformation = new ShapeInformation(
				second.目前的字體());
		分離活字 rectangularArea = new 分離活字(first);
		rectangularArea.合併活字(second);
		ShapeInformation shapeInformation = new ShapeInformation(
				rectangularArea.目前的字體());
		// System.out
		// .println("s="
		// + shapeInformation.getApproximativeCircumference()
		// + " len="
		// + boundaryLength
		// + " fi="
		// + firstInformation.getApproximativeCircumference()
		// + " sec="
		// + secondInformation.getApproximativeCircumference()
		// + " fs="
		// + (firstInformation.getApproximativeCircumference() +
		// secondInformation
		// .getApproximativeCircumference()));
		// TODO 人工參數
		return (firstInformation.getApproximativeCircumference()
				+ secondInformation.getApproximativeCircumference() - shapeInformation
					.getApproximativeCircumference()) / boundaryLength;
	}

	// public 活字單元 依目標調整中心(活字單元 活字物件)
	// {
	// 活字物件.徙(活字物件.目標範圍().getCenterX()- 活字物件.字範圍().getCenterX(), 活字物件.目標範圍()
	// .getCenterY() - 活字物件.字範圍().getCenterY() );
	// return 活字物件;
	// }
	public double getPrecision()
	{// TODO
		return precision;
	}

	double 平均闊度()
	{
		return 活字平均闊度;
	}

	// ///////////////////////////

	/** 參考教育部的國字注音比例參考圖 */
	final double 教育部建議注音大細 = 0.36;// TODO
	/** 注音因為傷細，上尾愛放較橫，較清楚 */
	final double 注音譀橫 = 1.2;// TODO
	/** 注音莫傷倚倒爿，徙規的寬度的比例 */
	final double 注音徙正爿比例 = 0.05;// TODO

	/**
	 * 要給<code>AwtForSinglePiecePrinter</code>列印前必須把物件活字依預計位置及大小（
	 * <code>getTerritory()</code>）產生一個新的物件。
	 * 
	 * @param 物件活字
	 *            愛調的活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public 分離活字 format(PieceMovableType 物件活字)
	{
		if (物件活字 instanceof ChineseCharacterMovableTypeTzu)
		{
			ChineseCharacterMovableTypeTzu 活字 = (ChineseCharacterMovableTypeTzu) 物件活字;
			if (活字.getChineseCharacter() != null
					&& 活字.getChineseCharacter() instanceof ChineseCharacterTzu)
			{
				ChineseCharacterTzu 字部件 = (ChineseCharacterTzu) 活字
						.getChineseCharacter();
				if (字部件.getType() == ChineseCharacterTzuCombinationType.注音符號)
				{
					// BasicStroke basicStroke = new BasicStroke();
					// RectangularArea a = new CopyOfRectangularArea(
					// basicStroke.createStrokedShape(物件活字.getPiece()
					// .getTerritory()));
					// a.add(依目標懸度調整大細(new
					// CopyOfRectangularArea(物件活字.getPiece())));
					// return a;
					return 依目標懸度調整大細(new 分離活字(物件活字.getPiece()));
				}
			}
		}
		return 依目標區域調整大細(new 分離活字(物件活字.getPiece()));
	}

	/**
	 * 把物件活字依預計位置及大小（<code>getTerritory()</code>）產生一個新的活字物件。
	 * 
	 * @param 活字物件
	 *            愛調的活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public 分離活字 依目標區域調整大細(分離活字 活字物件)
	{
//		System.out.println(活字物件.這馬字範圍());
//		System.out.println(活字物件.目標範圍());
		// ((分離活字) 活字物件).切掉字範圍();
		double widthCoefficient = 活字物件.目標範圍().getWidth()
				/ 活字物件.這馬字範圍().getWidth(), heightCoefficient = 活字物件.目標範圍()
				.getHeight() / 活字物件.這馬字範圍().getHeight();
		AffineTransform shrinkTransform = getAffineTransform(widthCoefficient,
				heightCoefficient);
		// //TODO
		活字物件.縮放(shrinkTransform);
		活字物件.徙轉原點();
		活字物件.徙(活字物件.目標範圍().getX(), 活字物件.目標範圍().getY());
		return 活字物件;
	}

	/**
	 * 把物件活字依預計位置及懸度（<code>getTerritory()</code>）產生一個新的活字物件。
	 * 
	 * @param 活字物件
	 *            愛調的活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public 分離活字 依目標懸度調整大細(分離活字 活字物件)
	{
		double coefficient = 活字物件.目標範圍().getHeight()
				/ Math.max(-活字物件.這馬字範圍().getMinY(), 活字物件.這馬字範圍().getMaxY())
				/ 2.0;
		if (coefficient > 教育部建議注音大細)
			coefficient = 教育部建議注音大細;
		AffineTransform shrinkTransform = getAffineTransform(
				coefficient * 注音譀橫, coefficient);
		活字物件.縮放(shrinkTransform);
		活字物件.徙(活字物件.目標範圍().getX() + 活字物件.目標範圍().getWidth() * 注音徙正爿比例, 活字物件
				.目標範圍().getY() + 活字物件.目標範圍().getHeight() / 2.0);
		return 活字物件;
	}

	/**
	 * 產生一個相同圖形的正方體活字物件。
	 * 
	 * @param rectangularArea
	 *            活字物件
	 * @return 格式過後的活字物件資訊
	 */
	public 分離活字 getPieceWithSquareTerritory(分離活字 rectangularArea)
	{
		分離活字 target = new 分離活字(rectangularArea);
		target.設目標範圍(target.這馬字範圍());
		double value = Math.min(target.目標範圍().getWidth(), target.目標範圍()
				.getHeight());
		target.設目標範圍大細(value, value);
		return 依目標區域調整大細(target);
	}
}
